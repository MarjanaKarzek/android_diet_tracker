package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.AllergenCacheImpl;
import de.karzek.diettracker.data.cache.DiaryEntryCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.AllergenCache;
import de.karzek.diettracker.data.cache.interfaces.DiaryEntryCache;
import de.karzek.diettracker.data.mapper.AllergenDataMapper;
import de.karzek.diettracker.data.mapper.DiaryEntryDataMapper;
import de.karzek.diettracker.data.mapper.MealDataMapper;
import de.karzek.diettracker.data.repository.AllergenRepositoryImpl;
import de.karzek.diettracker.data.repository.DiaryEntryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.AllergenRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.DiaryEntryRepository;
import de.karzek.diettracker.domain.interactor.useCase.allergen.GetAllAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.GetAllergenByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.PutAllAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.AddAmountOfWaterUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.DeleteAllDiaryEntriesMatchingMealIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.DeleteDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetAllDiaryEntriesMatchingUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetDiaryEntryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetWaterStatusUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.PutDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.UpdateAmountOfWaterUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.UpdateMealOfDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllergenByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.PutAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.AddAmountOfWaterUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteAllDiaryEntriesMatchingMealIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetDiaryEntryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetWaterStatusUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.UpdateAmountOfWaterUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.UpdateMealOfDiaryEntryUseCase;
import de.karzek.diettracker.domain.mapper.AllergenDomainMapper;
import de.karzek.diettracker.domain.mapper.DiaryEntryDomainMapper;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
@Module
public class DiaryEntryModule {

    // data

    @Provides
    DiaryEntryDataMapper provideDiaryEntryDataMapper(){
        return new DiaryEntryDataMapper();
    }

    @Provides
    DiaryEntryCache provideDiaryEntryCache(){
        return new DiaryEntryCacheImpl();
    }

    @Provides
    DiaryEntryRepository provideDiaryEntryRepository(DiaryEntryCache cache, DiaryEntryDataMapper diaryEntryMapper, MealDataMapper mealMapper){
        return new DiaryEntryRepositoryImpl(cache, diaryEntryMapper, mealMapper);
    }

    // domain

    @Provides
    DiaryEntryDomainMapper provideDiaryEntryDomainMapper(){
        return new DiaryEntryDomainMapper();
    }

    @Provides
    PutDiaryEntryUseCase providePutDiaryEntryUseCase(DiaryEntryRepository repository, DiaryEntryDomainMapper mapper){
        return new PutDiaryEntryUseCaseImpl(repository, mapper);
    }

    @Provides
    GetDiaryEntryByIdUseCase provideGetDiaryEntryByIdUseCase(DiaryEntryRepository repository, DiaryEntryDomainMapper mapper){
        return new GetDiaryEntryByIdUseCaseImpl(repository, mapper);
    }

    @Provides
    DeleteDiaryEntryUseCase provideDeleteDiaryEntryUseCase(DiaryEntryRepository repository){
        return new DeleteDiaryEntryUseCaseImpl(repository);
    }

    @Provides
    GetAllDiaryEntriesMatchingUseCase providesGetAllDiaryEntriesMatchingUseCase(DiaryEntryRepository repository, DiaryEntryDomainMapper mapper){
        return new GetAllDiaryEntriesMatchingUseCaseImpl(repository, mapper);
    }

    @Provides
    GetWaterStatusUseCase providesGetWaterStatusUseCase(DiaryEntryRepository repository, DiaryEntryDomainMapper mapper){
        return new GetWaterStatusUseCaseImpl(repository, mapper);
    }

    @Provides
    AddAmountOfWaterUseCase AddAmountOfWaterUseCase(DiaryEntryRepository repository){
        return new AddAmountOfWaterUseCaseImpl(repository);
    }

    @Provides
    UpdateAmountOfWaterUseCase providesUpdateAmountOfWaterUseCase(DiaryEntryRepository repository){
        return new UpdateAmountOfWaterUseCaseImpl(repository);
    }

    @Provides
    UpdateMealOfDiaryEntryUseCase provideUpdateMealOfDiaryEntryUseCase(DiaryEntryRepository repository, MealDomainMapper mapper){
        return new UpdateMealOfDiaryEntryUseCaseImpl(repository, mapper);
    }

    @Provides
    DeleteAllDiaryEntriesMatchingMealIdUseCase provideDeleteAllDiaryEntriesMatchingMealIdUseCase(DiaryEntryRepository repository){
        return new DeleteAllDiaryEntriesMatchingMealIdUseCaseImpl(repository);
    }

    // presentation

    @Provides
    DiaryEntryUIMapper provideDiaryEntryUIMapper(){
        return new DiaryEntryUIMapper();
    }

}
