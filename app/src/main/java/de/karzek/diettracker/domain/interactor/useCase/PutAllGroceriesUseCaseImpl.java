package de.karzek.diettracker.domain.interactor.useCase;

import java.util.List;

import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.GetAllGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.PutAllGroceriesUseCase;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class PutAllGroceriesUseCaseImpl implements PutAllGroceriesUseCase {

    private final GroceryRepositoryImpl repository;
    private final GroceryDomainMapper mapper;

    public PutAllGroceriesUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.putAllGroceries(mapper.transformAllToData(input.getGroceryDomainModelList())).map(output -> {
            if (output) {
                return new Output(Output.SUCCESS);
            } else {
                return new Output(Output.ERROR_UNKNOWN_PROBLEM);
            }
        }).onErrorReturn(throwable -> new Output(Output.ERROR_UNKNOWN_PROBLEM));
    }

}