package de.karzek.diettracker.domain.interactor.useCase.grocery;

import java.util.List;

import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetMatchingGroceriesUseCase;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class GetMatchingGroceriesUseCaseImpl implements GetMatchingGroceriesUseCase {

    private final GroceryRepository repository;
    private final GroceryDomainMapper mapper;

    public GetMatchingGroceriesUseCaseImpl(GroceryRepository repository, GroceryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllGroceriesMatching(input.getType(), input.getQuery())
                .map(new Function<List<GroceryDataModel>, Output>() {
                    @Override
                    public Output apply(List<GroceryDataModel> groceryDataModels) {
                        return new Output(Output.SUCCESS, mapper.transformAll(groceryDataModels));
                    }
                });
    }
}
