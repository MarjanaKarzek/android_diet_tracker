package de.karzek.diettracker.presentation.splash;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public interface SplashContract {

    interface View extends BaseView<Presenter> {
        void startMainActivity();
    }

    interface Presenter extends BasePresenter<View> {

        void init();
    }
}
