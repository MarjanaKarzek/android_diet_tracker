package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.AllergenCacheImpl;
import de.karzek.diettracker.data.cache.ServingCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.AllergenCache;
import de.karzek.diettracker.data.cache.interfaces.ServingCache;
import de.karzek.diettracker.data.mapper.AllergenDataMapper;
import de.karzek.diettracker.data.mapper.ServingDataMapper;
import de.karzek.diettracker.data.repository.AllergenRepositoryImpl;
import de.karzek.diettracker.data.repository.ServingRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.AllergenRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.ServingRepository;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.allergen.GetAllAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.GetAllergenByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.GetMatchingAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.PutAllAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.serving.PutAllServingsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllergenByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetMatchingAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.PutAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.serving.PutAllServingsUseCase;
import de.karzek.diettracker.domain.mapper.AllergenDomainMapper;
import de.karzek.diettracker.domain.mapper.ServingDomainMapper;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.ServingUIMapper;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
@Module
public class ServingModule {

    // data

    @Provides
    ServingDataMapper provideServingDataMapper(){
        return new ServingDataMapper();
    }

    @Provides
    ServingCache provideServingCacheImpl(){
        return new ServingCacheImpl();
    }

    @Provides
    ServingRepository provideServingRepositoryImpl(ServingCache servingCache, ServingDataMapper mapper){
        return new ServingRepositoryImpl(servingCache, mapper);
    }

    // domain

    @Provides
    ServingDomainMapper provideServingDomainMapper(){
        return new ServingDomainMapper();
    }

    @Provides
    PutAllServingsUseCase providePutAllServingsUseCase(ServingRepository repository, ServingDomainMapper mapper){
        return new PutAllServingsUseCaseImpl(repository, mapper);
    }

    // presentation

    @Provides
    ServingUIMapper provideServingUIMapper(){
        return new ServingUIMapper();
    }

}
