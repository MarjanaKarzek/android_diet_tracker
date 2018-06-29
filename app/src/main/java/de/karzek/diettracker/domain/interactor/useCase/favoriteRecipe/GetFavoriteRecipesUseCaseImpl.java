package de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe;

import java.util.List;

import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import de.karzek.diettracker.data.model.FavoriteRecipeDataModel;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteGroceryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteRecipeRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetFavoriteRecipesUseCase;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.FavoriteRecipeDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class GetFavoriteRecipesUseCaseImpl implements GetFavoriteRecipesUseCase {

    private final FavoriteRecipeRepository repository;
    private final FavoriteRecipeDomainMapper mapper;

    public GetFavoriteRecipesUseCaseImpl(FavoriteRecipeRepository repository, FavoriteRecipeDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllFavoriteRecipes()
                .map(new Function<List<FavoriteRecipeDataModel>, Output>() {
                    @Override
                    public Output apply(List<FavoriteRecipeDataModel> dataModels) {
                        return new Output(Output.SUCCESS, mapper.transformAll(dataModels));
                    }
                });
    }
}
