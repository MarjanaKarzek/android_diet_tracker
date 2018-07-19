package de.karzek.diettracker.presentation.main;

import android.support.annotation.IntDef;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;

/**
 * Created by MarjanaKarzek on 26.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 26.04.2018
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        int FRAGMENT_HOME = 0;
        int FRAGMENT_DIARY = 1;
        int FRAGMENT_COOKBOOK = 2;
        int FRAGMENT_SETTINGS = 3;

        void showOnboardingScreen(int onboardingTag);

        @IntDef({FRAGMENT_HOME, FRAGMENT_DIARY, FRAGMENT_COOKBOOK, FRAGMENT_SETTINGS})
        @interface FragmentIndex {
        }
    }

    interface Presenter extends BasePresenter<View> {

    }

}
