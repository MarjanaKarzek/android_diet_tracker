package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.repository.DiaryEntryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.DiaryEntryRepository;
import de.karzek.diettracker.domain.interactor.manager.NutritionManagerImpl;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.DeleteDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetAllDiaryEntriesMatchingUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.UpdateMealOfDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealCountUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.UpdateMealOfDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealCountUseCase;
import de.karzek.diettracker.domain.mapper.DiaryEntryDomainMapper;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealContract;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealPresenter;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
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
    GetAllDiaryEntriesMatchingUseCase provideGetAllDiaryEntriesMatchingUseCaseImpl(DiaryEntryRepository repository, DiaryEntryDomainMapper mapper){
        return new GetAllDiaryEntriesMatchingUseCaseImpl(repository, mapper);
    }

    @Provides
    UpdateMealOfDiaryEntryUseCase provideUpdateMealOfDiaryEntryUseCaseImpl(DiaryEntryRepository repository, MealDomainMapper mapper){
        return new UpdateMealOfDiaryEntryUseCaseImpl(repository, mapper);
    }

    //presentation

    @Provides
    GenericMealContract.Presenter provideGenericMealPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                              GetAllDiaryEntriesMatchingUseCase getAllDiaryEntriesMatchingUseCase,
                                                              Lazy<GetAllMealsUseCase> getAllMealsUseCase,
                                                              GetMealCountUseCase getMealCountUseCase,
                                                              Lazy<DeleteDiaryEntryUseCase> deleteDiaryEntryUseCase,
                                                              Lazy<UpdateMealOfDiaryEntryUseCase> updateMealOfDiaryEntryUseCase,
                                                              NutritionManager nutritionManager,
                                                              MealUIMapper mealMapper,
                                                              DiaryEntryUIMapper diaryEntryMapper) {
        return new GenericMealPresenter(sharedPreferencesUtil,
                getAllDiaryEntriesMatchingUseCase,
                getAllMealsUseCase,
                getMealCountUseCase,
                deleteDiaryEntryUseCase,
                updateMealOfDiaryEntryUseCase,
                nutritionManager,
                mealMapper,
                diaryEntryMapper);
    }
}
