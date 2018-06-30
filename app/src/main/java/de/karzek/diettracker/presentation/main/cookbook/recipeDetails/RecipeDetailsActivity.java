package de.karzek.diettracker.presentation.main.cookbook.recipeDetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.RecipeDetailsViewListAdapter;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.itemWrapper.RecipeDetailsViewItemWrapper;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationActivity;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.PreparationStepDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.util.StringUtils;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

public class RecipeDetailsActivity extends BaseActivity implements RecipeDetailsContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_view)
    FrameLayout loadingView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Menu menu;
    private int recipeId;

    @Inject
    RecipeDetailsContract.Presenter presenter;

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra("recipeId", id);

        return intent;
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_details, menu);

        this.menu = menu;
        presenter.checkFavoriteState(recipeId);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        recipeId = getIntent().getExtras().getInt("recipeId");
        setupSupportActionBar();
        setupRecyclerView();

        presenter.setView(this);
        presenter.setRecipeId(recipeId);
        presenter.start();
    }

    private void setupSupportActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow_white, null));
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeDetailsViewListAdapter());
    }

    @Override
    public void setupViewsInRecyclerView(RecipeDisplayModel displayModel, String nutritionDetails, HashMap<String, Long> maxValues, HashMap<String, Float> values) {
        getSupportActionBar().setTitle(displayModel.getTitle());

        ArrayList<RecipeDetailsViewItemWrapper> views = new ArrayList<>();
        if (displayModel.getPhoto() != null) {
            views.add(new RecipeDetailsViewItemWrapper(RecipeDetailsViewItemWrapper.ItemType.PHOTO_VIEW, BitmapFactory.decodeByteArray(displayModel.getPhoto(), 0, displayModel.getPhoto().length)));
        }

        /*if(nutritionDetails == VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)
            views.add(new RecipeDetailsViewItemWrapper(RecipeDetailsViewItemWrapper.ItemType.CALORY_DETAILS_VIEW, maxValues, values));
        else
            views.add(new RecipeDetailsViewItemWrapper(RecipeDetailsViewItemWrapper.ItemType.CALORIES_AND_MAKROS_DETAILS_VIEW, maxValues, values));

        views.add(new RecipeDetailsViewItemWrapper(RecipeDetailsViewItemWrapper.ItemType.INGREDIENTS_TITLE_VIEW, getString(R.string.recipe_details_ingredients_title, StringUtils.formatFloat(displayModel.getPortions()))));

        for (IngredientDisplayModel ingredient : displayModel.getIngredients()) {
            views.add(new RecipeDetailsViewItemWrapper(RecipeDetailsViewItemWrapper.ItemType.INGREDIENT_VIEW, ingredient));
        }

        views.add(new RecipeDetailsViewItemWrapper(RecipeDetailsViewItemWrapper.ItemType.TITLE_VIEW,getString(R.string.recipe_preparation_steps_title)));
        for (PreparationStepDisplayModel step : displayModel.getSteps())
            views.add(new RecipeDetailsViewItemWrapper(RecipeDetailsViewItemWrapper.ItemType.PREPARATION_STEP_VIEW, step));

        views.add(new RecipeDetailsViewItemWrapper(RecipeDetailsViewItemWrapper.ItemType.TITLE_VIEW, getString(R.string.recipe_meals_title)));
        views.add(new RecipeDetailsViewItemWrapper(RecipeDetailsViewItemWrapper.ItemType.MEALS_VIEW, displayModel.getMeals()));*/

        ((RecipeDetailsViewListAdapter) recyclerView.getAdapter()).setList(views);
    }

    @Override
    public void setPresenter(RecipeDetailsContract.Presenter presenter) {
        this.presenter = presenter;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        else if (item.getItemId() == R.id.recipe_details_favorite) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                item.setIcon(getDrawable(R.drawable.ic_star_filled_white));
            } else {
                item.setIcon(getDrawable(R.drawable.ic_star_white));
            }
            presenter.onFavoriteGroceryClicked(item.isChecked());
        } else if (item.getItemId() == R.id.recipe_details_edit){
            startActivity(RecipeManipulationActivity.newEditRecipeIntent(this, recipeId));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setFavoriteIconCheckedState(boolean checked) {
        MenuItem item = menu.findItem(R.id.recipe_details_favorite).setChecked(checked);
        if (item.isChecked()) {
            item.setIcon(getDrawable(R.drawable.ic_star_filled_white));
        } else {
            item.setIcon(getDrawable(R.drawable.ic_star_white));
        }
    }

    @Override
    protected void onDestroy() {
        presenter.finish();
        super.onDestroy();
    }

}
