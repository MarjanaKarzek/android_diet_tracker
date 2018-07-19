package de.karzek.diettracker.presentation.main;

import android.content.Context;
import android.content.SharedPreferences;

import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_WELCOME;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    private SharedPreferencesManager sharedPreferencesManager;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainPresenter(SharedPreferencesManager sharedPreferencesManager){
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public void start() {
        if(!sharedPreferencesManager.getOnboardingViewed(ONBOARDING_WELCOME))
            view.showOnboardingScreen(ONBOARDING_WELCOME);
    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

}
