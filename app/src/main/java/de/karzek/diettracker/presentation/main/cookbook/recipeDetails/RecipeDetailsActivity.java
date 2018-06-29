package de.karzek.diettracker.presentation.main.cookbook.recipeDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.custom.CustomBottomNavigationView;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationActivity;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.util.ViewUtils;

public class RecipeDetailsActivity extends BaseActivity implements RecipeDetailsContract.View {

    @BindView(R.id.loading_view)
    FrameLayout loadingView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Menu menu;
    private int recipeId;
    private RecipeDisplayModel recipe;

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
            presenter.onFavoriteGroceryClicked(item.isChecked(), recipe);
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
    public void fillRecipeDetails(RecipeDisplayModel recipe) {
        this.recipe = recipe;
        getSupportActionBar().setTitle(recipe.getTitle());
    }

    @Override
    protected void onDestroy() {
        presenter.finish();
        super.onDestroy();
    }

}
