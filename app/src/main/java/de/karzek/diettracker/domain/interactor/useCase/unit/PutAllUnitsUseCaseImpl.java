package de.karzek.diettracker.domain.interactor.useCase.unit;

import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.PutAllGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.PutAllUnitsUseCase;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class PutAllUnitsUseCaseImpl implements PutAllUnitsUseCase {

    private final UnitRepository repository;
    private final UnitDomainMapper mapper;

    public PutAllUnitsUseCaseImpl(UnitRepository repository, UnitDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.putAllUnits(mapper.transformAllToData(input.getUnitDomainModels())).map(output -> {
            if (output) {
                return new Output(Output.SUCCESS);
            } else {
                return new Output(Output.ERROR_UNKNOWN_PROBLEM);
            }
        }).onErrorReturn(throwable -> new Output(Output.ERROR_UNKNOWN_PROBLEM));
    }

}