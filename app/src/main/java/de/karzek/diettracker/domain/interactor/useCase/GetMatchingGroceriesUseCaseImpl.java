package de.karzek.diettracker.domain.interactor.useCase;

import java.util.List;

import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.GetMatchingGroceriesUseCase;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
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

    private final GroceryRepositoryImpl repository;
    private final GroceryDomainMapper mapper;

    public GetMatchingGroceriesUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper) {
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
