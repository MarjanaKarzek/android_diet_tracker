package de.karzek.diettracker.domain.interactor.useCase.grocery;

import java.util.List;

import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetExactlyMatchingGroceryUseCase;
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
public class GetExactlyMatchingGroceryUseCaseImpl implements GetExactlyMatchingGroceryUseCase {

    private final GroceryRepository repository;
    private final GroceryDomainMapper mapper;

    public GetExactlyMatchingGroceryUseCaseImpl(GroceryRepository repository, GroceryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllGroceriesExactlyMatching(input.getQuery())
                .map(new Function<List<GroceryDataModel>, Output>() {
                    @Override
                    public Output apply(List<GroceryDataModel> groceryDataModels) {
                        if (groceryDataModels.size() > 0)
                            return new Output(Output.SUCCESS, mapper.transform(groceryDataModels.get(0)));
                        else
                            return new Output(Output.SUCCESS, null);
                    }
                });
    }
}
