package de.karzek.diettracker.presentation.onboarding;

import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class OnboardingPresenter implements OnboardingContract.Presenter {

    private OnboardingContract.View view;

    private SharedPreferencesManager sharedPreferencesManager;

    public OnboardingPresenter(SharedPreferencesManager sharedPreferencesManager){
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public void start() {

    }

    @Override
    public void setView(OnboardingContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {

    }

    @Override
    public void onCloseButtonClicked() {
        view.finishSelf();
    }

    @Override
    public void onBackgroundViewClicked() {
        view.finishSelf();
    }

    @Override
    public void setOnboardingScreenViewed(int onboardingTag) {
        sharedPreferencesManager.setOnboardingToViewed(onboardingTag);
    }

}
