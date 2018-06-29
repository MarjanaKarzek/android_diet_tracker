package de.karzek.diettracker.presentation.main.cookbook.recipeDetails;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
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

        void showLoading();

        void hideLoading();

        void setFavoriteIconCheckedState(boolean checked);

        void fillRecipeDetails(RecipeDisplayModel transform);
    }

    interface Presenter extends BasePresenter<View> {

        void checkFavoriteState(int recipeId);

        void setRecipeId(int id);

        void onFavoriteGroceryClicked(boolean checked, RecipeDisplayModel recipe);
    }

}
