package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.UnitCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.UnitCache;
import de.karzek.diettracker.data.mapper.UnitDataMapper;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetAllDefaultUnitsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetUnitByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetUnitByNameUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.PutAllUnitsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByNameUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.PutAllUnitsUseCase;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
@Module
public class UnitModule {

    // data

    @Provides
    UnitDataMapper provideUnitDataMapper(){
        return new UnitDataMapper();
    }

    @Provides
    UnitCache provideUnitCacheImpl(){
        return new UnitCacheImpl();
    }

    @Provides
    UnitRepository provideUnitRepositoryImpl(UnitCache unitCache, UnitDataMapper mapper){
        return new UnitRepositoryImpl(unitCache, mapper);
    }

    // domain

    @Provides
    UnitDomainMapper provideUnitDomainMapper(){
        return new UnitDomainMapper();
    }

    @Provides
    PutAllUnitsUseCase providePutAllUnitsUseCase(UnitRepository repository, UnitDomainMapper mapper){
        return new PutAllUnitsUseCaseImpl(repository, mapper);
    }

    @Provides
    GetAllDefaultUnitsUseCase provideGetAllDefaultUnitsUseCaseImpl(UnitRepository repository, UnitDomainMapper mapper){
        return new GetAllDefaultUnitsUseCaseImpl(repository, mapper);
    }

    @Provides
    GetUnitByNameUseCase provideGetUnitByNameUseCaseImpl(UnitRepository repository, UnitDomainMapper mapper){
        return new GetUnitByNameUseCaseImpl(repository, mapper);
    }

    @Provides
    GetUnitByIdUseCase provideGetUnitByIdUseCase(UnitRepository repository, UnitDomainMapper mapper){
        return new GetUnitByIdUseCaseImpl(repository, mapper);
    }

    // presentation

    @Provides
    UnitUIMapper provideUnitUIMapper(){
        return new UnitUIMapper();
    }

}
