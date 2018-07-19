package de.karzek.diettracker.presentation.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseFragment;
import de.karzek.diettracker.presentation.main.diary.meal.adapter.favoriteRecipeList.FavoriteRecipeListAdapter;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchActivity;
import de.karzek.diettracker.presentation.search.recipe.RecipeSearchActivity;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.StringUtils;
import de.karzek.diettracker.presentation.util.ViewUtils;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_DRINK;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class HomeFragment extends BaseFragment implements HomeContract.View {

    @BindView(R.id.circle_progress_bar_calories)
    CircularProgressBar caloriesProgressBar;
    @BindView(R.id.circle_progress_bar_calories_value)
    TextView caloriesProgressBarValue;
    @BindView(R.id.circle_progress_bar_protein)
    CircularProgressBar proteinsProgressBar;
    @BindView(R.id.circle_progress_bar_protein_value)
    TextView proteinsProgressBarValue;
    @BindView(R.id.circle_progress_bar_carbs)
    CircularProgressBar carbsProgressBar;
    @BindView(R.id.circle_progress_bar_carbs_value)
    TextView carbsProgressBarValue;
    @BindView(R.id.circle_progress_bar_fats)
    CircularProgressBar fatsProgressBar;
    @BindView(R.id.circle_progress_bar_fats_value)
    TextView fatsProgressBarValue;
    @BindView(R.id.nutrition_state)
    CardView nutritionState;

    @BindView(R.id.favorite_recipe_title)
    TextView favoriteText;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.drinks_section)
    CardView drinksSection;
    @BindView(R.id.circle_progress_bar_dinks_progress)
    CircularProgressBar drinksProgressBar;
    @BindView(R.id.circle_progress_bar_drinks_value)
    EditText drinksProgressBarValue;
    @BindView(R.id.drinks_max_value)
    TextView drinksMaxValue;

    @BindView(R.id.floating_action_button_menu)
    FloatingActionsMenu floatingActionsMenu;
    @BindView(R.id.fab_overlay)
    FrameLayout overlay;
    @BindView(R.id.loading_view)
    FrameLayout loadingView;

    @Inject
    HomeContract.Presenter presenter;

    private SimpleDateFormat databaseDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
    private Calendar date = Calendar.getInstance();

    @Override
    public void onResume() {
        super.onResume();
        showLoading();
        presenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        showLoading();

        setupRecyclerView();
        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                overlay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                overlay.setVisibility(View.GONE);
            }
        });
        ViewUtils.addElevationToFABMenuLabels(getContext(), floatingActionsMenu);

        drinksProgressBarValue.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    drinksProgressBarValue.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(drinksProgressBarValue.getWindowToken(), 0);
                    presenter.updateAmountOfWater(Float.valueOf(drinksProgressBarValue.getText().toString()));
                }
                return true;
            }
        });


        presenter.setView(this);
        presenter.setCurrentDate(databaseDateFormat.format(date.getTime()));
        presenter.start();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new FavoriteRecipeListAdapter(presenter));
    }

    @Override
    protected void setupFragmentComponent() {
        TrackerApplication.get(getContext()).createHomeComponent().inject(this);
    }

    @OnClick(R.id.add_food)
    public void onAddFoodClicked() {
        presenter.onAddFoodClicked();
    }

    @OnClick(R.id.add_drink)
    public void onAddDrinkClicked() {
        presenter.onAddDrinkClicked();
    }

    @OnClick(R.id.add_recipe)
    public void onAddRecipeClicked() {
        presenter.onAddRecipeClicked();
    }

    @OnClick(R.id.fab_overlay)
    public void onFabOverlayClicked() {
        presenter.onFabOverlayClicked();
    }

    @Override
    public void startFoodSearchActivity(int currentMealId) {
        startActivity(GrocerySearchActivity.newIntent(getContext(), TYPE_FOOD, databaseDateFormat.format(date.getTime()), currentMealId, false));
    }

    @Override
    public void startDrinkSearchActivity(int currentMealId) {
        startActivity(GrocerySearchActivity.newIntent(getContext(), TYPE_DRINK, databaseDateFormat.format(date.getTime()), currentMealId, false));
    }

    @Override
    public void startRecipeSearchActivity(int currentMealId) {
        startActivity(RecipeSearchActivity.newIntent(getContext(), databaseDateFormat.format(date.getTime()), currentMealId));
    }

    @Override
    public void closeFabMenu() {
        floatingActionsMenu.collapse();
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
    public void showRecipeAddedInfo() {
        Toast.makeText(getContext(), getString(R.string.success_message_portion_added), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateRecyclerView(ArrayList<RecipeDisplayModel> recipeDisplayModels) {
        ((FavoriteRecipeListAdapter) recyclerView.getAdapter()).setList(recipeDisplayModels);
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void setCaloryState(float sum, int maxValue) {
        caloriesProgressBar.setProgress(100.0f / maxValue * sum);
        caloriesProgressBarValue.setText(StringUtils.formatFloat(maxValue - sum));
    }

    @Override
    public void showFavoriteText(String name) {
        favoriteText.setVisibility(View.VISIBLE);
        favoriteText.setText(getString(R.string.favorite_recipes_home_title, name));
    }

    @Override
    public void hideFavoriteText() {
        favoriteText.setVisibility(View.GONE);
    }

    @Override
    public void setNutritionState(HashMap<String, Float> sums, int caloriesGoal, int proteinsGoal, int carbsGoal, int fatsGoal) {
        caloriesProgressBar.setProgress(100.0f / caloriesGoal * sums.get(Constants.CALORIES));
        caloriesProgressBarValue.setText(StringUtils.formatFloat(caloriesGoal - sums.get(Constants.CALORIES)));

        proteinsProgressBar.setProgress(100.0f / proteinsGoal * sums.get(Constants.PROTEINS));
        proteinsProgressBarValue.setText(StringUtils.formatFloat(proteinsGoal - sums.get(Constants.PROTEINS)));

        carbsProgressBar.setProgress(100.0f / carbsGoal * sums.get(Constants.CARBS));
        carbsProgressBarValue.setText(StringUtils.formatFloat(carbsGoal - sums.get(Constants.CARBS)));

        fatsProgressBar.setProgress(100.0f / fatsGoal * sums.get(Constants.FATS));
        fatsProgressBarValue.setText(StringUtils.formatFloat(fatsGoal - sums.get(Constants.FATS)));
    }

    @Override
    public void hideNutritionState() {
        nutritionState.setVisibility(View.GONE);
    }

    @Override
    public void hideDrinksSection() {
        drinksSection.setVisibility(View.GONE);
    }

    @Override
    public void setLiquidStatus(float sum, float liquidGoal) {
        drinksProgressBar.setProgress(100.0f / liquidGoal * sum);
        drinksProgressBarValue.setText(StringUtils.formatFloat(sum));
        drinksMaxValue.setText(getString(R.string.generic_drinks_max_value, StringUtils.formatFloat(liquidGoal)));
    }

    @OnClick(R.id.add_bottle)
    public void onAddBottleWaterClicked() {
        presenter.addBottleWaterClicked();
    }

    @OnClick(R.id.add_glass)
    public void onAddGlassWaterClicked() {
        presenter.addGlassWaterClicked();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(getContext()).releaseHomeComponent();
    }
}
