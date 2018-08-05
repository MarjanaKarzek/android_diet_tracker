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

        void showOnboardingScreen(int onboardingTag);

    }

    interface Presenter extends BasePresenter<View> {

    }

    @IntDef({FragmentIndex.FRAGMENT_HOME,
            FragmentIndex.FRAGMENT_DIARY,
            FragmentIndex.FRAGMENT_COOKBOOK,
            FragmentIndex.FRAGMENT_SETTINGS})
    @interface FragmentIndex {
        int FRAGMENT_HOME = 0;
        int FRAGMENT_DIARY = 1;
        int FRAGMENT_COOKBOOK = 2;
        int FRAGMENT_SETTINGS = 3;
    }

}
