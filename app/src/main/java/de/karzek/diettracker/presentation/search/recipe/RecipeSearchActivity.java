package de.karzek.diettracker.presentation.search.recipe;

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
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.search.recipe.adapter.RecipeSearchResultListAdapter;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.RecipeEditDetailsActivity;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class RecipeSearchActivity extends BaseActivity implements RecipeSearchContract.View {

    public static String EXTRA_SELECTED_DATE = "EXTRA_SELECTED_DATE";
    public static String EXTRA_SELECTED_MEAL = "EXTRA_SELECTED_MEAL";

    @Inject
    RecipeSearchContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recipe_search_placeholder)
    TextView placeholder;
    @BindView(R.id.loading_view)
    FrameLayout loadingView;

    private String noResultsPlaceholder;
    private String noFavoritesPlaceholder;
    private String selectedDate;
    private int selectedMeal;

    public static Intent newIntent(Context context, String selectedDate, int selectedMeal) {
        Intent intent = new Intent(context, RecipeSearchActivity.class);
        intent.putExtra(EXTRA_SELECTED_DATE, selectedDate);
        intent.putExtra(EXTRA_SELECTED_MEAL, selectedMeal);

        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getFavoriteRecipes(selectedMeal);
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).createRecipeSearchComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.recipe_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals(""))
                    presenter.getRecipesMatchingQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (!query.equals(""))
                    presenter.getRecipesMatchingQuery(query);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        ButterKnife.bind(this);

        selectedDate = getIntent().getExtras().getString(EXTRA_SELECTED_DATE, "");
        selectedMeal = getIntent().getExtras().getInt(EXTRA_SELECTED_MEAL, 0);

        initializePlaceholders();

        setupSupportActionBar();
        setupRecyclerView();

        presenter.setView(this);
        presenter.start();
        presenter.getFavoriteRecipes(selectedMeal);
    }

    private void initializePlaceholders() {
        noResultsPlaceholder = getString(R.string.recipe_search_query_without_result_placeholder);
        noFavoritesPlaceholder = getString(R.string.recipe_search_placeholder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(this).releaseRecipeSearchComponent();
    }

    @Override
    public void showRecipeDetails(int id) {
        startActivity(RecipeEditDetailsActivity.newIntent(this, id, selectedMeal, selectedDate));
    }

    @Override
    public void updateRecipeSearchResultList(ArrayList<RecipeDisplayModel> recipes) {
        ((RecipeSearchResultListAdapter) recyclerView.getAdapter()).setList(recipes);
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
    public int getSelectedMeal() {
        return selectedMeal;
    }

    private void setupSupportActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow_white, null));
        getSupportActionBar().setTitle(R.string.recipe_search_title);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeSearchResultListAdapter(presenter, presenter));
    }
}
