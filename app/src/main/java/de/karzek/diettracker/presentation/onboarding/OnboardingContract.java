package de.karzek.diettracker.presentation.onboarding;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public interface OnboardingContract {

    interface View extends BaseView<Presenter> {

        void finishSelf();
    }

    interface Presenter extends BasePresenter<View> {

        void onCloseButtonClicked();

        void onBackgroundViewClicked();

        void setOnboardingScreenViewed(int onboardingTag);
    }
}
