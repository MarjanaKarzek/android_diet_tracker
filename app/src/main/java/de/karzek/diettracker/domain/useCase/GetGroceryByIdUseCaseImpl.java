package de.karzek.diettracker.domain.useCase;

import java.util.List;

import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.domain.mapper.GroceryUIMapper;
import de.karzek.diettracker.domain.model.GroceryData;
import de.karzek.diettracker.domain.useCase.useCaseInterface.GetGroceryByIdUseCase;
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
    private final GroceryUIMapper mapper;

    public GetGroceryByIdUseCaseImpl(GroceryRepositoryImpl repository, GroceryUIMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getGroceryByID(input.id).map(new Function<GroceryData, Output>() {
            @Override
            public Output apply(GroceryData grocery){
                return new Output(Output.SUCCESS, grocery);
            }
        });
    }

}