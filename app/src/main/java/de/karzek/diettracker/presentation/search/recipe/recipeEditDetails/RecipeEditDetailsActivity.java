package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.main.MainActivity;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationActivity;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.RecipeEditDetailsViewListAdapter;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.itemWrapper.RecipeEditDetailsViewItemWrapper;
import de.karzek.diettracker.presentation.util.Constants;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

public class RecipeEditDetailsActivity extends BaseActivity implements RecipeEditDetailsContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_view)
    FrameLayout loadingView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Menu menu;
    private int recipeId;
    private int selectedMeal;
    private String selectedDate;

    private Calendar selectedDateCalendar = Calendar.getInstance();
    private SimpleDateFormat databaseDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Inject
    RecipeEditDetailsContract.Presenter presenter;

    public static Intent newIntent(Context context, int id, int selectedMeal, String selectedDate) {
        Intent intent = new Intent(context, RecipeEditDetailsActivity.class);
        intent.putExtra("recipeId", id);
        intent.putExtra("selectedMeal", selectedMeal);
        intent.putExtra("selectedDate", selectedDate);

        return intent;
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
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
        selectedMeal = getIntent().getExtras().getInt("selectedMeal");
        selectedDate = getIntent().getExtras().getString("selectedDate");

        setupSupportActionBar();
        setupRecyclerView();

        presenter.setView(this);
        presenter.setRecipeId(recipeId);
        presenter.setSelectedMeal(selectedMeal);
        presenter.setSelectedDate(selectedDate);
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
        recyclerView.setAdapter(new RecipeEditDetailsViewListAdapter(presenter, presenter, presenter, presenter, presenter, presenter));
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void setupViewsInRecyclerView(RecipeDisplayModel displayModel, float selectedPortions, String nutritionDetails, boolean detailsExpanded, HashMap<String, Long> maxValues, HashMap<String, Float> values, ArrayList<MealDisplayModel> meals) {
        getSupportActionBar().setTitle(displayModel.getTitle());

        ArrayList<RecipeEditDetailsViewItemWrapper> views = new ArrayList<>();
        if (displayModel.getPhoto() != null) {
            views.add(new RecipeEditDetailsViewItemWrapper(RecipeEditDetailsViewItemWrapper.ItemType.PHOTO_VIEW, BitmapFactory.decodeByteArray(displayModel.getPhoto(), 0, displayModel.getPhoto().length)));
        }

        views.add(new RecipeEditDetailsViewItemWrapper(RecipeEditDetailsViewItemWrapper.ItemType.INGREDIENTS_TITLE_VIEW, selectedPortions));

        if(nutritionDetails.equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY) && detailsExpanded)
            views.add(new RecipeEditDetailsViewItemWrapper(RecipeEditDetailsViewItemWrapper.ItemType.CALORY_DETAILS_VIEW, maxValues, values));
        else if (nutritionDetails.equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS) && detailsExpanded)
            views.add(new RecipeEditDetailsViewItemWrapper(RecipeEditDetailsViewItemWrapper.ItemType.CALORIES_AND_MAKROS_DETAILS_VIEW, maxValues, values));

        for (IngredientDisplayModel ingredient : displayModel.getIngredients())
            views.add(new RecipeEditDetailsViewItemWrapper(RecipeEditDetailsViewItemWrapper.ItemType.INGREDIENT_VIEW, ingredient, selectedPortions));

        views.add(new RecipeEditDetailsViewItemWrapper(RecipeEditDetailsViewItemWrapper.ItemType.MEAL_SELECTOR_VIEW, meals, selectedMeal));
        views.add(new RecipeEditDetailsViewItemWrapper(RecipeEditDetailsViewItemWrapper.ItemType.DATE_SELECTOR_VIEW, selectedDate));

        views.add(new RecipeEditDetailsViewItemWrapper(RecipeEditDetailsViewItemWrapper.ItemType.ADD_VIEW));

        ((RecipeEditDetailsViewListAdapter) recyclerView.getAdapter()).setList(views);
    }

    @Override
    public void setPresenter(RecipeEditDetailsContract.Presenter presenter) {
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
            presenter.onFavoriteRecipeClicked(item.isChecked());
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
    public void showErrorNotEnoughIngredientsLeft() {
        Toast.makeText(this, getString(R.string.error_message_not_enough_ingredients_left), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToDiary() {
        startActivity(MainActivity.newIntentToDiary(this));
        finish();
    }

    @Override
    public void openDateSelectorDialog() {
        if (dateSetListener == null) {
            dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
                selectedDateCalendar.set(Calendar.YEAR, year);
                selectedDateCalendar.set(Calendar.MONTH, monthOfYear);
                selectedDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDate = databaseDateFormat.format(selectedDateCalendar.getTime());
                presenter.setSelectedDateFromDialog(selectedDate);
            };
        }

        DatePickerDialog dialog = new DatePickerDialog(this, dateSetListener, selectedDateCalendar.get(Calendar.YEAR), selectedDateCalendar.get(Calendar.MONTH), selectedDateCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTime().getTime() + Constants.weekInMilliS);
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        presenter.finish();
        super.onDestroy();
    }

}
