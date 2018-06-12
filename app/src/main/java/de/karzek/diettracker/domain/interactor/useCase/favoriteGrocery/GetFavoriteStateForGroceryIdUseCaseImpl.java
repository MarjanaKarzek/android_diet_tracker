package de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery;

import java.util.List;

import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteGroceryRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteStateForGroceryIdUseCase;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class GetFavoriteStateForGroceryIdUseCaseImpl implements GetFavoriteStateForGroceryIdUseCase {

    private final FavoriteGroceryRepository repository;

    public GetFavoriteStateForGroceryIdUseCaseImpl(FavoriteGroceryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getFavoriteStateForGroceryById(input.getId())
                .map(new Function<Boolean, Output>() {
                    @Override
                    public Output apply(Boolean state) {
                        return new Output(state, Output.SUCCESS);
                    }
                });
    }
}
