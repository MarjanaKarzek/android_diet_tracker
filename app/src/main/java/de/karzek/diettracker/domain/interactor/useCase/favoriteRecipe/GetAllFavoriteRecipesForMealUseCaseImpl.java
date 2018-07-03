package de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.FavoriteRecipeDataModel;
import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.data.model.RecipeDataModel;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteRecipeRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetAllFavoriteRecipesForMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetAllGroceriesUseCase;
import de.karzek.diettracker.domain.mapper.FavoriteRecipeDomainMapper;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.RecipeDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetAllFavoriteRecipesForMealUseCaseImpl implements GetAllFavoriteRecipesForMealUseCase {

    private final FavoriteRecipeRepository repository;
    private final RecipeDomainMapper mapper;

    public GetAllFavoriteRecipesForMealUseCaseImpl(FavoriteRecipeRepository repository, RecipeDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllFavoriteRecipesForMeal(input.getMeal()).map(new Function<List<FavoriteRecipeDataModel>, Output>() {
            @Override
            public Output apply(List<FavoriteRecipeDataModel> dataModels) {
                ArrayList<RecipeDataModel> recipes = new ArrayList<>();

                for(FavoriteRecipeDataModel dataModel : dataModels)
                    recipes.add(dataModel.getRecipe());

                return new Output(Output.SUCCESS, mapper.transformAll(recipes));
            }
        });
    }

}