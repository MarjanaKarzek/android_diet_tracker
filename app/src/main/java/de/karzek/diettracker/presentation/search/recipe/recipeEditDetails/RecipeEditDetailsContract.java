package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder.RecipeEditDetailsAddViewHolder;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder.RecipeEditDetailsDateSelectorViewHolder;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder.RecipeEditDetailsIngredientViewHolder;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder.RecipeEditDetailsIngredientsAndPortionsTitleViewHolder;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder.RecipeEditDetailsMealSelectorViewHolder;

/**
 * Created by MarjanaKarzek on 26.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 26.04.2018
 */

public interface RecipeEditDetailsContract {

    interface View extends BaseView<Presenter> {

        void setupViewsInRecyclerView(RecipeDisplayModel displayModel, float selectedPortions, String nutritionDetails, boolean detailsExpanded, HashMap<String, Long> maxValues, HashMap<String, Float> values, ArrayList<MealDisplayModel> meals);

        void showLoading();

        void hideLoading();

        void setFavoriteIconCheckedState(boolean checked);

        void showErrorNotEnoughIngredientsLeft();

        void navigateToDiary();

        void openDateSelectorDialog();

    }

    interface Presenter extends BasePresenter<View>,
            RecipeEditDetailsIngredientsAndPortionsTitleViewHolder.OnPortionChangedListener,
            RecipeEditDetailsIngredientsAndPortionsTitleViewHolder.OnExpandNutritionDetailsViewClickListener,
            RecipeEditDetailsIngredientViewHolder.OnDeleteIngredientClickListener,
            RecipeEditDetailsMealSelectorViewHolder.OnMealItemSelectedListener,
            RecipeEditDetailsDateSelectorViewHolder.OnDateClickListener,
            RecipeEditDetailsAddViewHolder.OnSaveRecipeClickedListener {

        void checkFavoriteState(int recipeId);

        void setRecipeId(int id);

        void onFavoriteRecipeClicked(boolean checked);

        void setSelectedMeal(int selectedMeal);

        void setSelectedDate(String selectedDate);

        void setSelectedDateFromDialog(String selectedDate);

    }

}
