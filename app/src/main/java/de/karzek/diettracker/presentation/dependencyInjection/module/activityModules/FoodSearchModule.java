package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.FavoriteGroceryCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.mapper.FavoriteGroceryDataMapper;
import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteGroceryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.PutDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.GetFavoriteGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetMatchingGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetUnitByNameUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetMatchingGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByNameUseCase;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
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
    FavoriteGroceryCache provideFavoriteGroceryCacheImpl(){
        return new FavoriteGroceryCacheImpl();
    }

    @Provides
    FavoriteGroceryDataMapper provideFavoriteGroceryDataMapper(){
        return new FavoriteGroceryDataMapper();
    }

    @Provides
    FavoriteGroceryRepository provideFavoriteGroceryRepositoryImpl(FavoriteGroceryCache cache, FavoriteGroceryDataMapper mapper){
        return new FavoriteGroceryRepositoryImpl(cache, mapper);
    }

    //domain

    @Provides
    FavoriteGroceryDomainMapper provideFavoriteGroceryDomainMapper(){
        return new FavoriteGroceryDomainMapper();
    }

    @Provides
    GetFavoriteGroceriesUseCase provideGetFavoriteFoodsUseCase(FavoriteGroceryRepository repository, FavoriteGroceryDomainMapper mapper){
        return new GetFavoriteGroceriesUseCaseImpl(repository, mapper);
    }

    @Provides
    GetMatchingGroceriesUseCase provideGetMatchingGroceriesUseCaseImpl(GroceryRepository repository, GroceryDomainMapper mapper){
        return new GetMatchingGroceriesUseCaseImpl(repository, mapper);
    }

    @Provides
    GetUnitByNameUseCase provideGetUnitByNameUseCaseImpl(UnitRepository repository, UnitDomainMapper mapper){
        return new GetUnitByNameUseCaseImpl(repository, mapper);
    }

    //presentation

    @Provides
    FavoriteGroceryUIMapper provideFavoriteGroceryUIMapper(){
        return new FavoriteGroceryUIMapper();
    }

    @Provides
    GrocerySearchContract.Presenter provideFoodSearchPresenter(GetFavoriteGroceriesUseCase getFavoriteFoodsUseCase,
                                                               Lazy<GetMatchingGroceriesUseCase> getMatchingGroceriesUseCase,
                                                               Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase,
                                                               Lazy<GetUnitByNameUseCase> getUnitByNameUseCase,
                                                               Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                                                               FavoriteGroceryUIMapper favoriteGroceryMapper,
                                                               GroceryUIMapper groceryMapper,
                                                               UnitUIMapper unitMapper,
                                                               DiaryEntryUIMapper diaryEntryMapper,
                                                               SharedPreferencesUtil sharedPreferencesUtil) {
        return new GrocerySearchPresenter(getFavoriteFoodsUseCase,
                getMatchingGroceriesUseCase,
                getGroceryByIdUseCase,
                getUnitByNameUseCase,
                putDiaryEntryUseCase,
                favoriteGroceryMapper,
                groceryMapper,
                unitMapper,
                diaryEntryMapper,
                sharedPreferencesUtil);
    }
}
