package de.karzek.diettracker.domain.interactor.useCase.grocery;

import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.PutAllGroceriesUseCase;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class PutAllGroceriesUseCaseImpl implements PutAllGroceriesUseCase {

    private final GroceryRepository repository;
    private final GroceryDomainMapper mapper;

    public PutAllGroceriesUseCaseImpl(GroceryRepository repository, GroceryDomainMapper mapper) {
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