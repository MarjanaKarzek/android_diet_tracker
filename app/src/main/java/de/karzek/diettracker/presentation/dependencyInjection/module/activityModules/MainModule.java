package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.presentation.main.MainContract;
import de.karzek.diettracker.presentation.main.MainPresenter;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class MainModule {

    //presentation

    @Provides
    MainContract.Presenter provideMainPresenter(SharedPreferencesManager sharedPreferencesManager) {
        return new MainPresenter(sharedPreferencesManager);
    }
}
