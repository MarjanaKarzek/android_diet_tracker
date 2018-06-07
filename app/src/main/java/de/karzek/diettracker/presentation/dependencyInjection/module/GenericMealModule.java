package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.DiaryEntryCacheImpl;
import de.karzek.diettracker.data.mapper.DiaryEntryDataMapper;
import de.karzek.diettracker.data.repository.DiaryEntryRepositoryImpl;
import de.karzek.diettracker.data.repository.MealRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.DiaryEntryRepository;
import de.karzek.diettracker.domain.interactor.manager.NutritionManagerImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetAllDiaryEntriesMatchingUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealCountUseCaseImpl;
import de.karzek.diettracker.domain.mapper.DiaryEntryDomainMapper;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealContract;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealPresenter;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class GenericMealModule {

    //domain

    @Provides
    GetMealCountUseCaseImpl provideGetMealCountUseCaseImpl(MealRepositoryImpl repository){
        return new GetMealCountUseCaseImpl(repository);
    }

    @Provides
    NutritionManagerImpl provideNutritionManagerImpl(SharedPreferencesUtil sharedPreferencesUtil){
        return new NutritionManagerImpl(sharedPreferencesUtil);
    }

    @Provides
    GetAllDiaryEntriesMatchingUseCaseImpl provideGetAllDiaryEntriesMatchingUseCaseImpl(DiaryEntryRepositoryImpl repository, DiaryEntryDomainMapper mapper){
        return new GetAllDiaryEntriesMatchingUseCaseImpl(repository, mapper);
    }

    //presentation

    @Provides
    GenericMealContract.Presenter provideGenericMealPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                              GetAllDiaryEntriesMatchingUseCaseImpl getAllDiaryEntriesMatchingUseCase,
                                                              GetMealCountUseCaseImpl getMealCountUseCase,
                                                              NutritionManagerImpl nutritionManager,
                                                              DiaryEntryUIMapper mapper) {
        return new GenericMealPresenter(sharedPreferencesUtil,
                getAllDiaryEntriesMatchingUseCase,
                getMealCountUseCase,
                nutritionManager,
                mapper);
    }
}
