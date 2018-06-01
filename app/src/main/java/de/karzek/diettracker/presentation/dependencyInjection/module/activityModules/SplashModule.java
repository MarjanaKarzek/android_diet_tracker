package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.GroceryCacheImpl;
import de.karzek.diettracker.data.mapper.GroceryDataMapper;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.domain.interactor.manager.InitializeSharedPreferencesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.PutAllGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.presentation.main.MainContract;
import de.karzek.diettracker.presentation.main.MainPresenter;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
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
    @Provides
    GroceryDataMapper provideGroceryDataMapper(){
        return new GroceryDataMapper();
    }

    @Provides
    GroceryCacheImpl provideGroceryCacheImpl(){
        return new GroceryCacheImpl();
    }

    @Provides
    GroceryRepositoryImpl provideGroceryRepositoryImpl(GroceryCacheImpl groceryCache, GroceryDataMapper mapper){
        return new GroceryRepositoryImpl(groceryCache, mapper);
    }

    //domain
    @Provides
    GroceryDomainMapper provideGroceryDomainMapper(){
        return new GroceryDomainMapper();
    }

    @Provides
    InitializeSharedPreferencesUseCaseImpl provideInitializeSharedPreferencesUseCaseImpl(SharedPreferencesUtil sharedPreferencesUtil){
        return new InitializeSharedPreferencesUseCaseImpl(sharedPreferencesUtil);
    }

    @Provides
    PutAllGroceriesUseCaseImpl providePutAllGroceriesUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper){
        return new PutAllGroceriesUseCaseImpl(repository, mapper);
    }

    //presentation
    @Provides
    GroceryUIMapper provideGroceryUIMapper(){
        return new GroceryUIMapper();
    }

    @Provides
    SplashContract.Presenter provideSplashPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                    PutAllGroceriesUseCaseImpl putAllGroceriesUseCase,
                                                    InitializeSharedPreferencesUseCaseImpl initializeSharedPreferencesUseCase,
                                                    GroceryUIMapper mapper) {
        return new SplashPresenter(sharedPreferencesUtil, putAllGroceriesUseCase, initializeSharedPreferencesUseCase, mapper);
    }
}
