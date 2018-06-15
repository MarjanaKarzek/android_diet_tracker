package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.presentation.main.settings.SettingsContract;
import de.karzek.diettracker.presentation.main.settings.SettingsPresenter;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Module
public class SettingsModule {

    //domain

    @Provides
    GetMealByIdUseCase providesGetMealByIdUseCase(MealRepository repository, MealDomainMapper mapper){
        return new GetMealByIdUseCaseImpl(repository, mapper);
    }

    //presentation

    @Provides
    SettingsContract.Presenter provideSettingsPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                        GetAllMealsUseCase getAllMealsUseCase,
                                                        Lazy<GetMealByIdUseCase> getMealByIdUseCase,
                                                        MealUIMapper mealMapper) {
        return new SettingsPresenter(sharedPreferencesUtil,
                getAllMealsUseCase,
                getMealByIdUseCase,
                mealMapper);
    }
}
