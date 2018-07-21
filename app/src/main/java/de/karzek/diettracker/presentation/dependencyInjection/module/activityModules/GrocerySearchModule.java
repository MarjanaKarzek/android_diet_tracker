package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetMatchingGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByNameUseCase;
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
public class GrocerySearchModule {

    //presentation

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
