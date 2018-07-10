package de.karzek.diettracker.presentation.main.home;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.diary.meal.adapter.favoriteRecipeList.viewHolder.FavoriteRecipeViewHolder;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void startFoodSearchActivity();

        void startDrinkSearchActivity();

        void startRecipeSearchActivity();

        void closeFabMenu();

        void showLoading();

        void hideLoading();

        void showRecipeAddedInfo();

        void updateRecyclerView(ArrayList<RecipeDisplayModel> recipeDisplayModels);

        void showRecyclerView();

        void hideRecyclerView();

        void setCaloryState(float sum, int maxValue);

        void showFavoriteText(String name);

        void hideFavoriteText();

        void setNutritionState(HashMap<String, Float> nutritionSumsForDiaryEntries, int caloriesGoal, int proteinsGoal, int carbsGoal, int fatsGoal);

        void hideNutritionState();

        void hideDrinksSection();
    }

    interface Presenter extends BasePresenter<View>,
            FavoriteRecipeViewHolder.OnFavoriteRecipeItemClickedListener {

        void setCurrentMealId(int currentMealId);

        void setCurrentDate(String currentDate);

        void onAddFoodClicked();

        void onAddDrinkClicked();

        void onAddRecipeClicked();

        void onFabOverlayClicked();
    }
}
