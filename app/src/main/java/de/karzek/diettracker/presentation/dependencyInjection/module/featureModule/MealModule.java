package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.MealCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.MealCache;
import de.karzek.diettracker.data.mapper.MealDataMapper;
import de.karzek.diettracker.data.repository.MealRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealByNameUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealCountUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.PutAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.UpdateMealTimeUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByNameUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealCountUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.PutAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.UpdateMealTimeUseCase;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;

/**
 * Created by MarjanaKarzek on 03.07.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 03.07.2018
 */
@Module
public class MealModule {

    //data

    @Provides
    MealDataMapper provideMealDataMapper(){
        return new MealDataMapper();
    }

    @Provides
    MealCache provideMealCacheImpl(){
        return new MealCacheImpl();
    }

    @Provides
    MealRepository provideMealRepositoryImpl(MealCache mealCache, MealDataMapper mapper){
        return new MealRepositoryImpl(mealCache, mapper);
    }

    //domain

    @Provides
    MealUIMapper provideMealUIMapper(){
        return new MealUIMapper();
    }

    @Provides
    MealDomainMapper provideMealDomainMapper(){
        return new MealDomainMapper();
    }

    @Provides
    PutAllMealsUseCase providePutAllMealsUseCaseImpl(MealRepository repository, MealDomainMapper mapper){
        return new PutAllMealsUseCaseImpl(repository, mapper);
    }

    @Provides
    UpdateMealTimeUseCase providesUpdateMealTimeUseCase(MealRepository repository){
        return new UpdateMealTimeUseCaseImpl(repository);
    }

    @Provides
    GetMealByIdUseCase providesGetMealByIdUseCase(MealRepository repository, MealDomainMapper mapper){
        return new GetMealByIdUseCaseImpl(repository, mapper);
    }

    @Provides
    GetAllMealsUseCase provideGetAllMealsUseCaseImpl(MealRepository repository, MealDomainMapper mapper){
        return new GetAllMealsUseCaseImpl(repository, mapper);
    }

    @Provides
    GetMealCountUseCase provideGetMealCountUseCaseImpl(MealRepository repository){
        return new GetMealCountUseCaseImpl(repository);
    }

    //presentation

    @Provides
    GetMealByNameUseCase provideGetMealByNameUseCase(MealRepository repository, MealDomainMapper mapper){
        return new GetMealByNameUseCaseImpl(repository, mapper);
    }

}
