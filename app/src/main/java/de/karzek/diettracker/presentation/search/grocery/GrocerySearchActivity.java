package de.karzek.diettracker.presentation.search.grocery;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.search.grocery.adapter.GrocerySearchResultListAdapter;
import de.karzek.diettracker.presentation.search.grocery.adapter.itemWrapper.GrocerySearchResultItemWrapper;
import de.karzek.diettracker.presentation.search.grocery.barcodeScanner.BarcodeScannerActivity;
import de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsActivity;
import de.karzek.diettracker.presentation.util.Constants;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_COMBINED;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_DRINK;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;
import static de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsContract.MODE_ADD_INGREDIENT;
import static de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsContract.MODE_SEARCH_RESULT;
import static de.karzek.diettracker.presentation.util.Constants.INVALID_ENTITY_ID;
import static de.karzek.diettracker.presentation.util.Constants.ZXING_CAMERA_PERMISSION;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class GrocerySearchActivity extends BaseActivity implements GrocerySearchContract.View {

    @Inject
    GrocerySearchContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.food_search_placeholder)
    TextView placeholder;
    @BindView(R.id.loading_view)
    FrameLayout loadingView;

    private int groceryType;
    private String noResultsPlaceholder;
    private String noFavoritesPlaceholder;
    private String selectedDate;
    private int selectedMeal;
    private boolean ingredientSearch;
    private int index;

    public static Intent newIntent(Context context, int groceryType, String selectedDate, int selectedMeal, boolean ingredientSearch, int index) {
        Intent intent = new Intent(context, GrocerySearchActivity.class);
        if (!ingredientSearch) {
            intent.putExtra("selectedDate", selectedDate); // ToDo implemnt constant values for keys
            intent.putExtra("selectedMeal", selectedMeal);
        }
        intent.putExtra("groceryType", groceryType);
        intent.putExtra("ingredientSearch", ingredientSearch);
        intent.putExtra("index", index);

        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getFavoriteGroceries(groceryType);
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.grocery_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals(""))
                    presenter.getGroceriesMatchingQuery(query, groceryType);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (!query.equals(""))
                    presenter.getGroceriesMatchingQuery(query, groceryType);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.grocery_search_barcode) {
            presenter.onBarcodeScannerClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_search);
        ButterKnife.bind(this);

        groceryType = getIntent().getExtras().getInt("groceryType", 0);
        if (groceryType != TYPE_COMBINED) {
            groceryType = getIntent().getExtras().getInt("groceryType", 0);
            selectedDate = getIntent().getExtras().getString("selectedDate", "");
            selectedMeal = getIntent().getExtras().getInt("selectedMeal", 0);
        } else {
            ingredientSearch = true;
            if(getIntent().getExtras().getInt("index", 0) != Constants.INVALID_ENTITY_ID)
                index = getIntent().getExtras().getInt("index", 0);
        }

        initializePlaceholders(groceryType);

        setupSupportActionBar();
        setupRecyclerView();

        presenter.setView(this);
        presenter.start();
        presenter.getFavoriteGroceries(groceryType);
    }

    private void initializePlaceholders(int type) {
        switch (type) {
            case TYPE_FOOD:
                noResultsPlaceholder = getString(R.string.food_search_query_without_result_placeholder);
                noFavoritesPlaceholder = getString(R.string.food_search_placeholder);
                break;
            case TYPE_DRINK:
                noResultsPlaceholder = getString(R.string.drink_search_query_without_result_placeholder);
                noFavoritesPlaceholder = getString(R.string.drink_search_placeholder);
                break;
            default:
                noResultsPlaceholder = getString(R.string.product_search_query_without_result_placeholder);
                noFavoritesPlaceholder = getString(R.string.product_search_placeholder);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.finish();
        super.onDestroy();
    }

    @Override
    public void showGroceryDetails(int id) {
        if (ingredientSearch)
            startActivityForResult(GroceryDetailsActivity.newIntent(this, id, null, null, null, MODE_ADD_INGREDIENT, index), Constants.ADD_INGREDIENT_INTENT_RESULT);
        else
            startActivity(GroceryDetailsActivity.newIntent(this, id, selectedDate, selectedMeal, null, MODE_SEARCH_RESULT, INVALID_ENTITY_ID));
    }

    @Override
    public void updateFoodSearchResultList(ArrayList<GroceryDisplayModel> foods) {
        ArrayList<GrocerySearchResultItemWrapper> wrappedFoods = new ArrayList<>();

        for (GroceryDisplayModel food : foods) {
            wrappedFoods.add(new GrocerySearchResultItemWrapper(food.getType(), food));
        }

        ((GrocerySearchResultListAdapter) recyclerView.getAdapter()).setList(wrappedFoods);
    }

    @Override
    public void showPlaceholder() {
        placeholder.setText(noFavoritesPlaceholder);
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlaceholder() {
        placeholder.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showQueryWithoutResultPlaceholder() {
        placeholder.setText(noResultsPlaceholder);
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideQueryWithoutResultPlaceholder() {
        placeholder.setVisibility(View.GONE);
    }

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public String getSelectedDate() {
        return selectedDate;
    }

    @Override
    public void startBarcodeScannerActivity() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            startActivity(BarcodeScannerActivity.newIntent(this, selectedDate, selectedMeal, MODE_SEARCH_RESULT, INVALID_ENTITY_ID));
        }
    }

    private void setupSupportActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow_white, null));
        if (groceryType == TYPE_FOOD) {
            getSupportActionBar().setTitle(getString(R.string.food_search_title));
        } else if (groceryType == TYPE_DRINK) {
            getSupportActionBar().setTitle(getString(R.string.drink_search_title));
        } else {
            getSupportActionBar().setTitle(R.string.product_search_title);
        }
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GrocerySearchResultListAdapter(presenter, presenter, presenter));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startActivity(BarcodeScannerActivity.newIntent(this, selectedDate, selectedMeal, MODE_SEARCH_RESULT, INVALID_ENTITY_ID));
                else
                    Toast.makeText(this, getString(R.string.permission_grand_camera_for_barcode_scanner), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.ADD_INGREDIENT_INTENT_RESULT) {
            Intent intent = new Intent();
            intent.putExtra("index", data.getIntExtra("index", Constants.INVALID_ENTITY_ID));
            intent.putExtra("groceryId", data.getIntExtra("groceryId", 0));
            intent.putExtra("amount", data.getFloatExtra("amount", 0.0f));
            intent.putExtra("unitId", data.getIntExtra("unitId", 0));
            setResult(Constants.ADD_INGREDIENT_INTENT_RESULT, intent);
            finish();
        }
    }
}
