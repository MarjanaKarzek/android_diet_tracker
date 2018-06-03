package de.karzek.diettracker.presentation.main.diary.meal;

import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_SETTING_NUTRITION_DETAILS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class GenericMealPresenter implements  GenericMealContract.Presenter {

    GenericMealContract.View view;

    private SharedPreferencesUtil sharedPreferencesUtil;

    public GenericMealPresenter(SharedPreferencesUtil sharedPreferencesUtil){
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void start() {
        view.hideRecyclerView();
        view.showGroceryListPlaceholder();

        if(sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
        } else {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        }
    }

    @Override
    public void setView(GenericMealContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {

    }
}
