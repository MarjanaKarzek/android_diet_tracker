package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.DiaryEntryCacheImpl;
import de.karzek.diettracker.data.mapper.DiaryEntryDataMapper;
import de.karzek.diettracker.data.mapper.MealDataMapper;
import de.karzek.diettracker.data.repository.DiaryEntryRepositoryImpl;
import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.MealRepositoryImpl;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.domain.interactor.manager.NutritionManagerImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.PutDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.GetFavoriteStateForGroceryIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.PutFavoriteGroceryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.RemoveFavoriteGroceryByNameUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealCountUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetAllDefaultUnitsUseCaseImpl;
import de.karzek.diettracker.domain.mapper.DiaryEntryDomainMapper;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsContract;
import de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsPresenter;
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

    //data

    @Provides
    DiaryEntryDataMapper provideDiaryEntryDataMapper(){
        return new DiaryEntryDataMapper();
    }

    @Provides
    DiaryEntryCacheImpl provideDiaryEntryCacheImpl(){
        return new DiaryEntryCacheImpl();
    }

    @Provides
    DiaryEntryRepositoryImpl provideDiaryEntryRepositoryImpl(DiaryEntryCacheImpl cache, DiaryEntryDataMapper diaryEntryMapper, MealDataMapper mealMapper){
        return new DiaryEntryRepositoryImpl(cache, diaryEntryMapper, mealMapper);
    }

    //domain

    @Provides
    DiaryEntryDomainMapper provideDiaryEntryDomainMapper(){
        return new DiaryEntryDomainMapper();
    }


    @Provides
    GetGroceryByIdUseCaseImpl provideGetGroceryByIdUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper){
        return new GetGroceryByIdUseCaseImpl(repository, mapper);
    }

    @Provides
    GetAllDefaultUnitsUseCaseImpl provideGetAllDefaultUnitsUseCaseImpl(UnitRepositoryImpl repository, UnitDomainMapper mapper){
        return new GetAllDefaultUnitsUseCaseImpl(repository, mapper);
    }

    @Provides
    GetAllMealsUseCaseImpl provideGetAllMealsUseCaseImpl(MealRepositoryImpl repository, MealDomainMapper mapper){
        return new GetAllMealsUseCaseImpl(repository, mapper);
    }

    @Provides
    PutDiaryEntryUseCaseImpl providePutDiaryEntryUseCaseImpl(DiaryEntryRepositoryImpl repository, DiaryEntryDomainMapper mapper){
        return new PutDiaryEntryUseCaseImpl(repository, mapper);
    }

    @Provides
    GetMealCountUseCaseImpl provideGetMealCountUseCaseImpl(MealRepositoryImpl repository){
        return new GetMealCountUseCaseImpl(repository);
    }

    @Provides
    PutFavoriteGroceryUseCaseImpl providePutFavoriteGroceryUseCaseImpl(FavoriteGroceryRepositoryImpl repository, FavoriteGroceryDomainMapper mapper){
        return new PutFavoriteGroceryUseCaseImpl(repository, mapper);
    }

    @Provides
    RemoveFavoriteGroceryByNameUseCaseImpl provideRemoveFavoriteGroceryByNameUseCaseImpl(FavoriteGroceryRepositoryImpl repository, FavoriteGroceryDomainMapper mapper){
        return new RemoveFavoriteGroceryByNameUseCaseImpl(repository, mapper);
    }

    @Provides
    GetFavoriteStateForGroceryIdUseCaseImpl provideGetFavoriteStateForGroceryIdUseCaseImpl(FavoriteGroceryRepositoryImpl repository, FavoriteGroceryDomainMapper mapper){
        return new GetFavoriteStateForGroceryIdUseCaseImpl(repository, mapper);
    }

    //presentation

    @Provides
    DiaryEntryUIMapper provideDiaryEntryUIMapper(){
        return new DiaryEntryUIMapper();
    }

    @Provides
    GroceryDetailsContract.Presenter provideFoodDetailsPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                                 GetGroceryByIdUseCaseImpl getGroceryByIdUseCase,
                                                                 GetAllDefaultUnitsUseCaseImpl getAllDefaultUnitsUseCase,
                                                                 GetAllMealsUseCaseImpl getAllMealsUseCase,
                                                                 GetMealCountUseCaseImpl getMealCountUseCase,
                                                                 GetFavoriteStateForGroceryIdUseCaseImpl getFavoriteStateForGroceryIdUseCase,
                                                                 Lazy<PutDiaryEntryUseCaseImpl> putDiaryEntryUseCase,
                                                                 Lazy<PutFavoriteGroceryUseCaseImpl> putFavoriteGroceryUseCase,
                                                                 Lazy<RemoveFavoriteGroceryByNameUseCaseImpl> removeFavoriteGroceryByNameUseCase,
                                                                 GroceryUIMapper groceryMapper,
                                                                 UnitUIMapper unitMapper,
                                                                 MealUIMapper mealMapper,
                                                                 DiaryEntryUIMapper diaryEntryMapper,
                                                                 NutritionManagerImpl nutritionManager) {
        return new GroceryDetailsPresenter(sharedPreferencesUtil,
                getGroceryByIdUseCase,
                getAllDefaultUnitsUseCase,
                getAllMealsUseCase,
                getMealCountUseCase,
                getFavoriteStateForGroceryIdUseCase,
                putDiaryEntryUseCase,
                putFavoriteGroceryUseCase,
                removeFavoriteGroceryByNameUseCase,
                groceryMapper,
                unitMapper,
                mealMapper,
                diaryEntryMapper,
                nutritionManager);
    }
}
