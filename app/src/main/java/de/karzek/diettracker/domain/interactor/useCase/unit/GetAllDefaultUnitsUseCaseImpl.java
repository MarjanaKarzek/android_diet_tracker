package de.karzek.diettracker.domain.interactor.useCase.unit;

import java.util.List;

import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
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
public class GetAllDefaultUnitsUseCaseImpl implements GetAllDefaultUnitsUseCase {

    private final UnitRepositoryImpl repository;
    private final UnitDomainMapper mapper;

    public GetAllDefaultUnitsUseCaseImpl(UnitRepositoryImpl repository, UnitDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllDefaultUnits(input.getType()).map(new Function<List<UnitDataModel>, Output>() {
            @Override
            public Output apply(List<UnitDataModel> unitDataModels) {
                return new Output( mapper.transformAll(unitDataModels), Output.SUCCESS);
            }
        });
    }

}