package de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery;

import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.RemoveFavoriteGroceryByNameUseCase;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class RemoveFavoriteGroceryByNameUseCaseImpl implements RemoveFavoriteGroceryByNameUseCase {

    private final FavoriteGroceryRepositoryImpl repository;
    private final FavoriteGroceryDomainMapper mapper;

    public RemoveFavoriteGroceryByNameUseCaseImpl(FavoriteGroceryRepositoryImpl repository, FavoriteGroceryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.removeFavoriteGroceryByName(input.getName()).map(output -> {
            if (output) {
                return new Output(Output.SUCCESS);
            } else {
                return new Output(Output.ERROR_UNKNOWN_PROBLEM);
            }
        }).onErrorReturn(throwable -> new Output(Output.ERROR_UNKNOWN_PROBLEM));
    }

}