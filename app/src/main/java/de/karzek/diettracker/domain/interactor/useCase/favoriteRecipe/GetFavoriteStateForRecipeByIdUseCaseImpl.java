package de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe;

import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteGroceryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteRecipeRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteStateForGroceryIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetFavoriteStateForRecipeByIdUseCase;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class GetFavoriteStateForRecipeByIdUseCaseImpl implements GetFavoriteStateForRecipeByIdUseCase {

    private final FavoriteRecipeRepository repository;

    public GetFavoriteStateForRecipeByIdUseCaseImpl(FavoriteRecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getFavoriteStateForRecipeById(input.getId())
                .map(new Function<Boolean, Output>() {
                    @Override
                    public Output apply(Boolean state) {
                        return new Output(state, Output.SUCCESS);
                    }
                });
    }
}
