package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.IngredientSearchListAdapter;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.itemWrapper.IngredientSearchItemWrapper;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.ManualIngredientDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchActivity;
import de.karzek.diettracker.presentation.search.grocery.barcodeScanner.BarcodeScannerActivity;
import de.karzek.diettracker.presentation.util.Constants;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_COMBINED;
import static de.karzek.diettracker.presentation.util.Constants.INVALID_ENTITY_ID;

public class AutomatedIngredientSearchActivity extends BaseActivity implements AutomatedIngredientSearchContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_view)
    FrameLayout loadingView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.save_button)
    Button saveButton;

    @Inject
    AutomatedIngredientSearchContract.Presenter presenter;

    public static Intent newIntent(Context context, RecipeDisplayModel recipe) {
        Intent intent = new Intent(context, AutomatedIngredientSearchActivity.class);
        intent.putExtra("recipe", recipe);

        return intent;
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).createAutomatedIngredientSearchComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automated_ingredient_search);
        ButterKnife.bind(this);

        setupSupportActionBar();
        setupRecyclerView();
        disableSaveButton();

        presenter.setView(this);
        RecipeDisplayModel recipeDisplayModel = getIntent().getExtras().getParcelable("recipe");
        presenter.setRecipe(recipeDisplayModel);
        if(recipeDisplayModel.getId() != INVALID_ENTITY_ID)
            presenter.startEdit();
        else
            presenter.start();
    }

    private void disableSaveButton() {
        saveButton.setAlpha(.5f);
        saveButton.setClickable(false);
    }

    @Override
    public void enableSaveButton() {
        saveButton.setAlpha(1.f);
        saveButton.setClickable(true);
    }

    private void setupSupportActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow_white, null));
        getSupportActionBar().setTitle(getString(R.string.automated_ingredient_search_title));
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new IngredientSearchListAdapter(presenter, presenter, presenter));
    }

    @Override
    public void setupViewsInRecyclerView(ArrayList<IngredientDisplayModel> ingredients) {
        ArrayList<IngredientSearchItemWrapper> views = new ArrayList<>();

        for (IngredientDisplayModel ingredient : ingredients) {
            if (ingredient instanceof ManualIngredientDisplayModel) {
                views.add(new IngredientSearchItemWrapper(IngredientSearchItemWrapper.ItemType.LOADING_INGREDIENT, ingredient));
            } else {
                views.add(new IngredientSearchItemWrapper(IngredientSearchItemWrapper.ItemType.FOUND_INGREDIENT, ingredient));
            }
        }

        ((IngredientSearchListAdapter) recyclerView.getAdapter()).setList(views);
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
    public void startGrocerySearch(int index) {
        startActivityForResult(GrocerySearchActivity.newIngredientReplaceIntent(this, TYPE_COMBINED, index), Constants.ADD_REPLACE_INGREDIENT_INTENT_RESULT);
    }

    @Override
    public void startBarcodeScan(int index) {
        startActivityForResult(BarcodeScannerActivity.newReplaceIngredientSearchIntent(this, index), Constants.ADD_REPLACE_INGREDIENT_INTENT_RESULT);
    }

    @Override
    public void showSuccessfulSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.dialog_message_automated_ingredient_search_success));
        builder.setPositiveButton(getString(R.string.dialog_action_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onSuccessfulSearchDialogOKClicked();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override
    public void setupViewsInRecyclerViewForAdaption(ArrayList<IngredientDisplayModel> ingredients, HashMap<Integer, Integer> failReasons) {
        ArrayList<IngredientSearchItemWrapper> views = new ArrayList<>();

        for(int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i) instanceof ManualIngredientDisplayModel) {
                views.add(new IngredientSearchItemWrapper(IngredientSearchItemWrapper.ItemType.FAILED_INGREDIENT,
                        ingredients.get(i),
                        failReasons.get(i)));
            } else {
                views.add(new IngredientSearchItemWrapper(IngredientSearchItemWrapper.ItemType.FOUND_INGREDIENT, ingredients.get(i)));
            }
        }

        ((IngredientSearchListAdapter) recyclerView.getAdapter()).setList(views);
    }

    @Override
    public void showErrorWhileSavingRecipe() {
        Toast.makeText(this, getString(R.string.error_message_unknown_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        finish();
        setResult(Constants.CLOSE_SELF_RESULT, getIntent());
    }

    @Override
    public void showSaveButton() {
        saveButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUnsuccessfulSearchDiaog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.dialog_message_automated_ingredient_search_unsuccess));
        builder.setPositiveButton(getString(R.string.dialog_action_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.save_button) public void onSaveButtonClicked(){
        presenter.onSaveButtonClicked();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {
                case Constants.ADD_REPLACE_INGREDIENT_INTENT_RESULT:
                    presenter.replaceIngredient(data.getIntExtra("index", 0),
                            data.getIntExtra("groceryId", 0),
                            data.getFloatExtra("amount", 0.0f),
                            data.getIntExtra("unitId", 0));
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(this).releaseAutomatedIngredientSearchComponent();
    }

}
