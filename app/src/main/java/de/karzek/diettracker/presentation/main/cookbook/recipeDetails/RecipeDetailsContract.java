package de.karzek.diettracker.presentation.main.cookbook.recipeDetails;

import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.viewHolder.RecipeDetailsIngredientsAndPortionsTitleViewHolder;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 26.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 26.04.2018
 */

public interface RecipeDetailsContract {

    interface View extends BaseView<Presenter> {

        void setupViewsInRecyclerView(RecipeDisplayModel displayModel, String nutritionDetails, boolean detailsExpanded, HashMap<String, Long> maxValues, HashMap<String, Float> values);

        void showLoading();

        void hideLoading();

        void setFavoriteIconCheckedState(boolean checked);

    }

    interface Presenter extends BasePresenter<View>,
            RecipeDetailsIngredientsAndPortionsTitleViewHolder.OnExpandNutritionDetailsViewClickListener {

        void checkFavoriteState(int recipeId);

        void setRecipeId(int id);

        void onFavoriteRecipeClicked(boolean checked);

    }

}
