package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealByNameUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByNameUseCase;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;

/**
 * Created by MarjanaKarzek on 03.07.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 03.07.2018
 */
@Module
public class MealModule {

    @Provides
    GetMealByNameUseCase provideGetMealByNameUseCase(MealRepository repository, MealDomainMapper mapper){
        return new GetMealByNameUseCaseImpl(repository, mapper);
    }

}
