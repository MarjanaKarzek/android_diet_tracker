package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.repository.DiaryEntryRepositoryImpl;
import de.karzek.diettracker.domain.interactor.manager.NutritionManagerImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.AddAmountOfWaterUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.DeleteDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetAllDiaryEntriesMatchingUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetWaterStatusUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.UpdateAmountOfWaterUseCaseImpl;
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

    //domain

    @Provides
    DeleteDiaryEntryUseCaseImpl providesDeleteDiaryEntryUseCaseImpl(DiaryEntryRepositoryImpl repository){
        return new DeleteDiaryEntryUseCaseImpl(repository);
    }

    @Provides
    GetAllDiaryEntriesMatchingUseCaseImpl providesGetAllDiaryEntriesMatchingUseCaseImpl(DiaryEntryRepositoryImpl repository, DiaryEntryDomainMapper mapper){
        return new GetAllDiaryEntriesMatchingUseCaseImpl(repository, mapper);
    }

    @Provides
    GetWaterStatusUseCaseImpl providesGetWaterStatusUseCaseImpl(DiaryEntryRepositoryImpl repository, DiaryEntryDomainMapper mapper){
        return new GetWaterStatusUseCaseImpl(repository, mapper);
    }

    @Provides
    AddAmountOfWaterUseCaseImpl AddAmountOfWaterUseCaseImpl(DiaryEntryRepositoryImpl repository){
        return new AddAmountOfWaterUseCaseImpl(repository);
    }

    @Provides
    UpdateAmountOfWaterUseCaseImpl providesUpdateAmountOfWaterUseCaseImpl(DiaryEntryRepositoryImpl repository){
        return new UpdateAmountOfWaterUseCaseImpl(repository);
    }

    //presentation

    @Provides
    GenericDrinkContract.Presenter provideGenericDrinkPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                                Lazy<DeleteDiaryEntryUseCaseImpl> deleteDiaryEntryUseCase,
                                                                Lazy<AddAmountOfWaterUseCaseImpl> addAmountOfWaterUseCase,
                                                                Lazy<UpdateAmountOfWaterUseCaseImpl> updateAmountOfWaterUseCase,
                                                                GetAllDiaryEntriesMatchingUseCaseImpl getAllDiaryEntriesMatchingUseCase,
                                                                GetWaterStatusUseCaseImpl getWaterStatusUseCase,
                                                                DiaryEntryUIMapper diaryEntryMapper,
                                                                NutritionManagerImpl nutritionManager) {
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
