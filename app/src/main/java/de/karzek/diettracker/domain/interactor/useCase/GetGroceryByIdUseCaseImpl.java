package de.karzek.diettracker.domain.interactor.useCase;

import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.GetGroceryByIdUseCase;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetGroceryByIdUseCaseImpl implements GetGroceryByIdUseCase {

    private final GroceryRepositoryImpl repository;
    private final GroceryDomainMapper mapper;

    public GetGroceryByIdUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getGroceryByID(input.id).map(new Function<GroceryDataModel, Output>() {
            @Override
            public Output apply(GroceryDataModel grocery){
                return new Output(Output.SUCCESS, mapper.transform(grocery));
            }
        });
    }

}