package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.AllergenCacheImpl;
import de.karzek.diettracker.data.cache.GroceryCacheImpl;
import de.karzek.diettracker.data.cache.MealCacheImpl;
import de.karzek.diettracker.data.cache.ServingCacheImpl;
import de.karzek.diettracker.data.cache.UnitCacheImpl;
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
import de.karzek.diettracker.data.repository.repositoryInterface.ServingRepository;
import de.karzek.diettracker.domain.interactor.manager.InitializeSharedPreferencesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.PutAllAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.PutAllGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.PutAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.serving.PutAllServingsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.PutAllUnitsUseCaseImpl;
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
    AllergenDataMapper provideAllergenDataMapper(){
        return new AllergenDataMapper();
    }

    @Provides
    GroceryDataMapper provideGroceryDataMapper(){
        return new GroceryDataMapper();
    }

    @Provides
    MealDataMapper provideMealDataMapper(){
        return new MealDataMapper();
    }

    //cache

    @Provides
    UnitCacheImpl provideUnitCacheImpl(){
        return new UnitCacheImpl();
    }

    @Provides
    ServingCacheImpl provideServingCacheImpl(){
        return new ServingCacheImpl();
    }

    @Provides
    AllergenCacheImpl provideAllergenCacheImpl(){
        return new AllergenCacheImpl();
    }

    @Provides
    GroceryCacheImpl provideGroceryCacheImpl(){
        return new GroceryCacheImpl();
    }

    @Provides
    MealCacheImpl provideMealCacheImpl(){
        return new MealCacheImpl();
    }

    //repository

    @Provides
    UnitRepositoryImpl provideUnitRepositoryImpl(UnitCacheImpl unitCache, UnitDataMapper mapper){
        return new UnitRepositoryImpl(unitCache, mapper);
    }

    @Provides
    ServingRepositoryImpl provideServingRepositoryImpl(ServingCacheImpl servingCache, ServingDataMapper mapper){
        return new ServingRepositoryImpl(servingCache, mapper);
    }

    @Provides
    GroceryRepositoryImpl provideGroceryRepositoryImpl(GroceryCacheImpl groceryCache, GroceryDataMapper mapper){
        return new GroceryRepositoryImpl(groceryCache, mapper);
    }

    @Provides
    MealRepositoryImpl provideMealRepositoryImpl(MealCacheImpl mealCache, MealDataMapper mapper){
        return new MealRepositoryImpl(mealCache, mapper);
    }

    @Provides
    AllergenRepositoryImpl provideAllergenRepositoryImpl(AllergenCacheImpl mealCache, AllergenDataMapper mapper){
        return new AllergenRepositoryImpl(mealCache, mapper);
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

    @Provides
    MealDomainMapper provideMealDomainMapper(){
        return new MealDomainMapper();
    }

    @Provides
    AllergenDomainMapper provideAllergenDomainMapper(){
        return new AllergenDomainMapper();
    }

    //use case

    @Provides
    PutAllUnitsUseCaseImpl providePutAllUnitsUseCaseImpl(UnitRepositoryImpl repository, UnitDomainMapper mapper){
        return new PutAllUnitsUseCaseImpl(repository, mapper);
    }

    @Provides
    PutAllServingsUseCaseImpl providePutAllServingsUseCaseImpl(ServingRepositoryImpl repository, ServingDomainMapper mapper){
        return new PutAllServingsUseCaseImpl(repository, mapper);
    }

    @Provides
    PutAllAllergensUseCaseImpl providePutAllAllergensUseCaseImpl(AllergenRepositoryImpl repository, AllergenDomainMapper mapper){
        return new PutAllAllergensUseCaseImpl(repository, mapper);
    }

    @Provides
    PutAllGroceriesUseCaseImpl providePutAllGroceriesUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper){
        return new PutAllGroceriesUseCaseImpl(repository, mapper);
    }

    @Provides
    PutAllMealsUseCaseImpl providePutAllMealsUseCaseImpl(MealRepositoryImpl repository, MealDomainMapper mapper){
        return new PutAllMealsUseCaseImpl(repository, mapper);
    }

    @Provides
    InitializeSharedPreferencesUseCaseImpl provideInitializeSharedPreferencesUseCaseImpl(SharedPreferencesUtil sharedPreferencesUtil){
        return new InitializeSharedPreferencesUseCaseImpl(sharedPreferencesUtil);
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
    MealUIMapper provideMealUIMapper(){
        return new MealUIMapper();
    }

    @Provides
    AllergenUIMapper providAllergenUIMapper(){
        return new AllergenUIMapper();
    }

    @Provides
    SplashContract.Presenter provideSplashPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                    PutAllUnitsUseCaseImpl putAllUnitsUseCase,
                                                    PutAllServingsUseCaseImpl putAllServingsUseCase,
                                                    PutAllAllergensUseCaseImpl putAllAllergensUseCase,
                                                    PutAllGroceriesUseCaseImpl putAllGroceriesUseCase,
                                                    PutAllMealsUseCaseImpl putAllMealsUseCase,
                                                    InitializeSharedPreferencesUseCaseImpl initializeSharedPreferencesUseCase,
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
                initializeSharedPreferencesUseCase,
                unitMapper,
                servingMapper,
                allergenMapper,
                groceryMapper,
                mealMapper);
    }
}
