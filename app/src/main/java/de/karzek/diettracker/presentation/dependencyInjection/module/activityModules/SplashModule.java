package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.AllergenCacheImpl;
import de.karzek.diettracker.data.cache.GroceryCacheImpl;
import de.karzek.diettracker.data.cache.MealCacheImpl;
import de.karzek.diettracker.data.cache.ServingCacheImpl;
import de.karzek.diettracker.data.cache.UnitCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.AllergenCache;
import de.karzek.diettracker.data.cache.interfaces.GroceryCache;
import de.karzek.diettracker.data.cache.interfaces.MealCache;
import de.karzek.diettracker.data.cache.interfaces.ServingCache;
import de.karzek.diettracker.data.cache.interfaces.UnitCache;
import de.karzek.diettracker.data.mapper.AllergenDataMapper;
import de.karzek.diettracker.data.mapper.GroceryDataMapper;
import de.karzek.diettracker.data.mapper.MealDataMapper;
import de.karzek.diettracker.data.mapper.ServingDataMapper;
import de.karzek.diettracker.data.mapper.UnitDataMapper;
import de.karzek.diettracker.data.repository.AllergenRepositoryImpl;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.MealRepositoryImpl;
import de.karzek.diettracker.data.repository.ServingRepositoryImpl;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.AllergenRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.ServingRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import de.karzek.diettracker.domain.interactor.manager.SharedPreferencesManagerImpl;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.allergen.PutAllAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.PutAllGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.PutAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.serving.PutAllServingsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.PutAllUnitsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.PutAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.PutAllGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.PutAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.serving.PutAllServingsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.PutAllUnitsUseCase;
import de.karzek.diettracker.domain.mapper.AllergenDomainMapper;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.domain.mapper.ServingDomainMapper;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.ServingUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.splash.SplashContract;
import de.karzek.diettracker.presentation.splash.SplashPresenter;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class SplashModule {

    //data

    //mapper

    @Provides
    UnitDataMapper provideUnitDataMapper(){
        return new UnitDataMapper();
    }

    @Provides
    ServingDataMapper provideServingDataMapper(){
        return new ServingDataMapper();
    }

    @Provides
    GroceryDataMapper provideGroceryDataMapper(){
        return new GroceryDataMapper();
    }

    //cache

    @Provides
    UnitCache provideUnitCacheImpl(){
        return new UnitCacheImpl();
    }

    @Provides
    ServingCache provideServingCacheImpl(){
        return new ServingCacheImpl();
    }

    @Provides
    GroceryCache provideGroceryCacheImpl(){
        return new GroceryCacheImpl();
    }

    //repository

    @Provides
    UnitRepository provideUnitRepositoryImpl(UnitCache unitCache, UnitDataMapper mapper){
        return new UnitRepositoryImpl(unitCache, mapper);
    }

    @Provides
    ServingRepository provideServingRepositoryImpl(ServingCache servingCache, ServingDataMapper mapper){
        return new ServingRepositoryImpl(servingCache, mapper);
    }

    @Provides
    GroceryRepository provideGroceryRepositoryImpl(GroceryCache groceryCache, GroceryDataMapper mapper){
        return new GroceryRepositoryImpl(groceryCache, mapper);
    }

    //domain

    //mapper

    @Provides
    UnitDomainMapper provideUnitDomainMapper(){
        return new UnitDomainMapper();
    }

    @Provides
    ServingDomainMapper provideServingDomainMapper(){
        return new ServingDomainMapper();
    }

    @Provides
    GroceryDomainMapper provideGroceryDomainMapper(){
        return new GroceryDomainMapper();
    }

    //use case

    @Provides
    PutAllUnitsUseCase providePutAllUnitsUseCaseImpl(UnitRepository repository, UnitDomainMapper mapper){
        return new PutAllUnitsUseCaseImpl(repository, mapper);
    }

    @Provides
    PutAllServingsUseCase providePutAllServingsUseCaseImpl(ServingRepository repository, ServingDomainMapper mapper){
        return new PutAllServingsUseCaseImpl(repository, mapper);
    }

    @Provides
    PutAllGroceriesUseCase providePutAllGroceriesUseCaseImpl(GroceryRepository repository, GroceryDomainMapper mapper){
        return new PutAllGroceriesUseCaseImpl(repository, mapper);
    }

    @Provides
    SharedPreferencesManager provideInitializeSharedPreferencesUseCaseImpl(SharedPreferencesUtil sharedPreferencesUtil){
        return new SharedPreferencesManagerImpl(sharedPreferencesUtil);
    }

    //presentation

    //mapper

    @Provides
    UnitUIMapper provideUnitUIMapper(){
        return new UnitUIMapper();
    }

    @Provides
    ServingUIMapper provideServingUIMapper(){
        return new ServingUIMapper();
    }

    @Provides
    GroceryUIMapper provideGroceryUIMapper(){
        return new GroceryUIMapper();
    }

    @Provides
    SplashContract.Presenter provideSplashPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                    PutAllUnitsUseCase putAllUnitsUseCase,
                                                    PutAllServingsUseCase putAllServingsUseCase,
                                                    PutAllAllergensUseCase putAllAllergensUseCase,
                                                    PutAllGroceriesUseCase putAllGroceriesUseCase,
                                                    PutAllMealsUseCase putAllMealsUseCase,
                                                    SharedPreferencesManager sharedPreferencesManager,
                                                    UnitUIMapper unitMapper,
                                                    ServingUIMapper servingMapper,
                                                    AllergenUIMapper allergenMapper,
                                                    GroceryUIMapper groceryMapper,
                                                    MealUIMapper mealMapper) {
        return new SplashPresenter(sharedPreferencesUtil,
                putAllUnitsUseCase,
                putAllServingsUseCase,
                putAllAllergensUseCase,
                putAllGroceriesUseCase,
                putAllMealsUseCase,
                sharedPreferencesManager,
                unitMapper,
                servingMapper,
                allergenMapper,
                groceryMapper,
                mealMapper);
    }
}
