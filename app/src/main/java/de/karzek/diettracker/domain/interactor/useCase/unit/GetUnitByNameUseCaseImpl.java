package de.karzek.diettracker.domain.interactor.useCase.unit;

import java.util.List;

import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByNameUseCase;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetUnitByNameUseCaseImpl implements GetUnitByNameUseCase {

    private final UnitRepository repository;
    private final UnitDomainMapper mapper;

    public GetUnitByNameUseCaseImpl(UnitRepository repository, UnitDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getUnitByName(input.getName()).map(new Function<UnitDataModel, Output>() {
            @Override
            public Output apply(UnitDataModel unitDataModels) {
                return new Output( mapper.transform(unitDataModels), Output.SUCCESS);
            }
        });
    }

}