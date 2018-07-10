package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.repository.DiaryEntryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.DiaryEntryRepository;
import de.karzek.diettracker.domain.interactor.manager.NutritionManagerImpl;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.AddAmountOfWaterUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.DeleteDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetAllDiaryEntriesMatchingUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetWaterStatusUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.UpdateAmountOfWaterUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.AddAmountOfWaterUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetWaterStatusUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.UpdateAmountOfWaterUseCase;
import de.karzek.diettracker.domain.mapper.DiaryEntryDomainMapper;
import de.karzek.diettracker.presentation.main.diary.drink.GenericDrinkContract;
import de.karzek.diettracker.presentation.main.diary.drink.GenericDrinkPresenter;
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
public class GenericDrinkModule {

    //presentation

    @Provides
    GenericDrinkContract.Presenter provideGenericDrinkPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                                Lazy<DeleteDiaryEntryUseCase> deleteDiaryEntryUseCase,
                                                                Lazy<AddAmountOfWaterUseCase> addAmountOfWaterUseCase,
                                                                Lazy<UpdateAmountOfWaterUseCase> updateAmountOfWaterUseCase,
                                                                GetAllDiaryEntriesMatchingUseCase getAllDiaryEntriesMatchingUseCase,
                                                                GetWaterStatusUseCase getWaterStatusUseCase,
                                                                DiaryEntryUIMapper diaryEntryMapper,
                                                                NutritionManager nutritionManager) {
        return new GenericDrinkPresenter(sharedPreferencesUtil,
                deleteDiaryEntryUseCase,
                addAmountOfWaterUseCase,
                updateAmountOfWaterUseCase,
                getAllDiaryEntriesMatchingUseCase,
                getWaterStatusUseCase,
                diaryEntryMapper,
                nutritionManager);
    }
}
