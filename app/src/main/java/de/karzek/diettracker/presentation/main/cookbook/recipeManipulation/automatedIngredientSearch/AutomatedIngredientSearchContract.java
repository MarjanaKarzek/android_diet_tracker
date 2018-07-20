package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch;

import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.viewHolder.IngredientSearchFailedViewHolder;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 26.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 26.04.2018
 */

public interface AutomatedIngredientSearchContract {

    //todo implement intdefs correctly
    @IntDef({FailReasons.FAIL_REASON_WRONG_UNIT,
            FailReasons.FAIL_REASON_GROCERY_NOT_FOUND})
    @interface FailReasons {
        int FAIL_REASON_WRONG_UNIT = 0;
        int FAIL_REASON_GROCERY_NOT_FOUND = 1;
    }

    interface View extends BaseView<Presenter> {

        void enableSaveButton();

        void setupViewsInRecyclerView(ArrayList<IngredientDisplayModel> ingredients);

        void showLoading();

        void hideLoading();

        void startGrocerySearch(int index);

        void startBarcodeScan(int index);

        void showSuccessfulSearchDialog();

        void setupViewsInRecyclerViewForAdaption(ArrayList<IngredientDisplayModel> ingredients, HashMap<Integer, Integer> failReasons);

        void showErrorWhileSavingRecipe();

        void finishActivity();

        void showSaveButton();

        void showUnsuccessfulSearchDiaog();
    }

    interface Presenter extends BasePresenter<View>,
            IngredientSearchFailedViewHolder.OnStartGrocerySearchClickListener,
            IngredientSearchFailedViewHolder.OnStartBarcodeScanClickListener,
            IngredientSearchFailedViewHolder.OnDeleteIngredientClickListener {

        void startEdit();

        void setRecipe(RecipeDisplayModel recipe);

        void onSuccessfulSearchDialogOKClicked();

        void replaceIngredient(int index, int groceryId, float amount, int unitId);

        void onSaveButtonClicked();
    }

}
