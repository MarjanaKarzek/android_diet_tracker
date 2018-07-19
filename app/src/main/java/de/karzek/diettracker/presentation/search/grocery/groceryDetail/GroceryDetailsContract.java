package de.karzek.diettracker.presentation.search.grocery.groceryDetail;

import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public interface GroceryDetailsContract {

    interface View extends BaseView<Presenter> {

        void refreshNutritionDetails();

        void showLoading();

        void hideLoading();

        void showNutritionDetails(String value);

        void setupAllergenWarning(ArrayList<AllergenDisplayModel> allergenDisplayModels);

        void setNutritionMaxValues(HashMap<String, Long> values);

        void updateNutritionDetails(HashMap<String, Float> values);

        void fillGroceryDetails(GroceryDisplayModel grocery);

        void initializeServingsSpinner(ArrayList<UnitDisplayModel> defaultUnits, ArrayList<ServingDisplayModel> servings);

        void initializeMealSpinner(ArrayList<MealDisplayModel> mealDisplayModels);

        void navigateToDiaryFragment();

        void openDatePicker();

        void setFavoriteIconCheckedState(boolean checked);

        void prepareEditMode(DiaryEntryDisplayModel diaryEntry);

        void prepareAddIngredientMode();

        void finishView();

        void prepareEditIngredientMode(float amount);
    }

    interface Presenter extends BasePresenter<View> {

        void updateNutritionDetails(GroceryDisplayModel grocery, float amount);

        void setGroceryId(int groceryId);

        void addFood(DiaryEntryDisplayModel diaryEntry);

        void onDateLabelClicked();

        void addDrink(DiaryEntryDisplayModel diaryEntryDisplayModel);

        void onFavoriteGroceryClicked(boolean checked, GroceryDisplayModel grocery);

        void checkFavoriteState(int groceryId);

        void startEditMode(int diaryEntryId);

        void startAddIngredientMode(int groceryId);

        void onDeleteDiaryEntryClicked(int diaryEntryId);

        void startEditIngredientMode(float amount);

    }

    @DetailsMode
    int MODE_SEARCH_RESULT = 0;
    int MODE_EDIT_DIARY_ENTRY = 1;
    int MODE_EDIT_INGREDIENT = 2;
    int MODE_ADD_INGREDIENT = 3;

    @IntDef({MODE_SEARCH_RESULT, MODE_EDIT_DIARY_ENTRY, MODE_EDIT_INGREDIENT, MODE_ADD_INGREDIENT})
    @interface DetailsMode {
    }
}
