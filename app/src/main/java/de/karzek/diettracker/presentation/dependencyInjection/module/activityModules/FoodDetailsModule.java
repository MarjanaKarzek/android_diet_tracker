package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetAllDefaultUnitsUseCaseImpl;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.search.food.foodDetail.FoodDetailsContract;
import de.karzek.diettracker.presentation.search.food.foodDetail.FoodDetailsPresenter;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class FoodDetailsModule {

    //domain

    @Provides
    GetGroceryByIdUseCaseImpl provideGetGroceryByIdUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper){
        return new GetGroceryByIdUseCaseImpl(repository, mapper);
    }

    @Provides
    GetAllDefaultUnitsUseCaseImpl provideGetAllDefaultUnitsUseCaseImpl(UnitRepositoryImpl repository, UnitDomainMapper mapper){
        return new GetAllDefaultUnitsUseCaseImpl(repository, mapper);
    }

    //presentation

    @Provides
    FoodDetailsContract.Presenter provideFoodDetailsPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                              GetGroceryByIdUseCaseImpl getGroceryByIdUseCase,
                                                              GetAllDefaultUnitsUseCaseImpl getAllDefaultUnitsUseCase,
                                                              GroceryUIMapper groceryMapper,
                                                              UnitUIMapper unitMapper) {
        return new FoodDetailsPresenter(sharedPreferencesUtil, getGroceryByIdUseCase, getAllDefaultUnitsUseCase, groceryMapper, unitMapper);
    }
}
