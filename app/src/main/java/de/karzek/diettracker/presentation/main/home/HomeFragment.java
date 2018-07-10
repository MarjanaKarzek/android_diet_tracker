package de.karzek.diettracker.presentation.main.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchActivity;
import de.karzek.diettracker.presentation.search.recipe.RecipeSearchActivity;
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

    @BindView(R.id.favorite_recipe_title)
    TextView favoriteText;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

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
    private int currentMealId = 0;

    @Override
    public void onResume() {
        super.onResume();
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


        presenter.setView(this);
        presenter.setCurrentMealId(currentMealId);
        presenter.setCurrentDate(databaseDateFormat.format(date.getTime()));
        presenter.start();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FavoriteRecipeListAdapter(presenter));
    }

    @Override
    protected void setupFragmentComponent() {
        TrackerApplication.get(getContext()).createHomeComponent().inject(this);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
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
    public void startFoodSearchActivity() {
        startActivity(GrocerySearchActivity.newIntent(getContext(), TYPE_FOOD, databaseDateFormat.format(date.getTime()), currentMealId, false));
    }

    @Override
    public void startDrinkSearchActivity() {
        startActivity(GrocerySearchActivity.newIntent(getContext(), TYPE_DRINK, databaseDateFormat.format(date.getTime()), currentMealId, false));
    }

    @Override
    public void startRecipeSearchActivity() {
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
        caloriesProgressBar.setProgress(100.0f / caloriesGoal * sums.get("calories"));
        caloriesProgressBarValue.setText(StringUtils.formatFloat(caloriesGoal - sums.get("calories")));

        proteinsProgressBar.setProgress(100.0f / proteinsGoal * sums.get("proteins"));
        proteinsProgressBarValue.setText(StringUtils.formatFloat(proteinsGoal - sums.get("proteins")));

        carbsProgressBar.setProgress(100.0f / carbsGoal * sums.get("carbs"));
        carbsProgressBarValue.setText(StringUtils.formatFloat(carbsGoal - sums.get("carbs")));

        fatsProgressBar.setProgress(100.0f / fatsGoal * sums.get("fats"));
        fatsProgressBarValue.setText(StringUtils.formatFloat(fatsGoal - sums.get("fats")));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(getContext()).releaseHomeComponent();
    }
}
