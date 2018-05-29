package de.karzek.diettracker.presentation.main.diary;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;

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
    }

    interface Presenter extends BasePresenter<View> {
        void onAddFoodClicked();

        void onAddDrinkClicked();

        void onAddRecipeClicked();
    }
}
