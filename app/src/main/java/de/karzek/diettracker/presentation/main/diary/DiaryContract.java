package de.karzek.diettracker.presentation.main.diary;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.model.MealDisplayModel;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public interface DiaryContract {

    interface View extends BaseView<Presenter> {
        void startFoodSearchActivity();

        void startDrinkSearchActivity();

        void startRecipeSearchActivity();

        void closeFabMenu();

        void openDatePicker();

        void showLoading();

        void hideLoading();

        void setupViewPager(ArrayList<MealDisplayModel> meals);

        void onDateSelected(String selectedDate);

        void showPreviousDate();

        void showNextDate();
    }

    interface Presenter extends BasePresenter<View> {
        void onAddFoodClicked();

        void onAddDrinkClicked();

        void onAddRecipeClicked();

        void onFabOverlayClicked();

        void onDateLabelClicked();

        void onDateSelected(String selectedDate);

        void onPreviousDateClicked();

        void onNextDateClicked();
    }
}
