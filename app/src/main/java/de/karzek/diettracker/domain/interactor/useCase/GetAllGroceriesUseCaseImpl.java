package de.karzek.diettracker.domain.interactor.useCase;

import java.util.List;

import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.GetAllGroceriesUseCase;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetAllGroceriesUseCaseImpl implements GetAllGroceriesUseCase {

    private final GroceryRepositoryImpl repository;
    private final GroceryDomainMapper mapper;

    public GetAllGroceriesUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllGroceries().map(new Function<List<GroceryDataModel>, Output>() {
            @Override
            public Output apply(List<GroceryDataModel> groceryDataModelList) {
                return new Output(Output.SUCCESS, mapper.transformAll(groceryDataModelList));
            }
        });
    }

}