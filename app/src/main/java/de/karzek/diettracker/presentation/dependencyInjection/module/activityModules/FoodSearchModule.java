package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.FavoriteGroceryCacheImpl;
import de.karzek.diettracker.data.mapper.FavoriteGroceryDataMapper;
import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.GetFavoriteGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetMatchingGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetUnitByNameUseCaseImpl;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import de.karzek.diettracker.presentation.mapper.FavoriteGroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchContract;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchPresenter;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class FoodSearchModule {

    //data

    @Provides
    FavoriteGroceryCacheImpl provideFavoriteGroceryCacheImpl(){
        return new FavoriteGroceryCacheImpl();
    }

    @Provides
    FavoriteGroceryDataMapper provideFavoriteGroceryDataMapper(){
        return new FavoriteGroceryDataMapper();
    }

    @Provides
    FavoriteGroceryRepositoryImpl provideFavoriteGroceryRepositoryImpl(FavoriteGroceryCacheImpl cache, FavoriteGroceryDataMapper mapper){
        return new FavoriteGroceryRepositoryImpl(cache, mapper);
    }

    //domain

    @Provides
    FavoriteGroceryDomainMapper provideFavoriteGroceryDomainMapper(){
        return new FavoriteGroceryDomainMapper();
    }

    @Provides
    GetFavoriteGroceriesUseCaseImpl provideGetFavoriteFoodsUseCase(FavoriteGroceryRepositoryImpl repository, FavoriteGroceryDomainMapper mapper){
        return new GetFavoriteGroceriesUseCaseImpl(repository, mapper);
    }

    @Provides
    GetMatchingGroceriesUseCaseImpl provideGetMatchingGroceriesUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper){
        return new GetMatchingGroceriesUseCaseImpl(repository, mapper);
    }

    @Provides
    GetUnitByNameUseCaseImpl provideGetUnitByNameUseCaseImpl(UnitRepositoryImpl repository, UnitDomainMapper mapper){
        return new GetUnitByNameUseCaseImpl(repository, mapper);
    }

    //presentation

    @Provides
    FavoriteGroceryUIMapper provideFavoriteGroceryUIMapper(){
        return new FavoriteGroceryUIMapper();
    }

    @Provides
    GrocerySearchContract.Presenter provideFoodSearchPresenter(GetFavoriteGroceriesUseCaseImpl getFavoriteFoodsUseCase,
                                                               Lazy<GetMatchingGroceriesUseCaseImpl> getMatchingGroceriesUseCase,
                                                               Lazy<GetGroceryByIdUseCaseImpl> getGroceryByIdUseCase,
                                                               Lazy<GetUnitByNameUseCaseImpl> getUnitByNameUseCase,
                                                               FavoriteGroceryUIMapper favoriteGroceryMapper,
                                                               GroceryUIMapper groceryMapper,
                                                               UnitUIMapper unitMapper,
                                                               SharedPreferencesUtil sharedPreferencesUtil) {
        return new GrocerySearchPresenter(getFavoriteFoodsUseCase,
                getMatchingGroceriesUseCase,
                getGroceryByIdUseCase,
                getUnitByNameUseCase,
                favoriteGroceryMapper,
                groceryMapper,
                unitMapper,
                sharedPreferencesUtil);
    }
}
