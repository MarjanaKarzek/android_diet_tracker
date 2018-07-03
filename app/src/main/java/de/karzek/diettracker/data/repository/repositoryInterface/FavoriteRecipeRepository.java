package de.karzek.diettracker.data.repository.repositoryInterface;

import java.util.List;

import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import de.karzek.diettracker.data.model.FavoriteRecipeDataModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface FavoriteRecipeRepository {

    Observable<List<FavoriteRecipeDataModel>> getAllFavoriteRecipes();

    Observable<Boolean> putFavoriteRecipe(FavoriteRecipeDataModel dataModel);

    Observable<Boolean> removeFavoriteRecipeByTitle(String title);

    Observable<Boolean> getFavoriteStateForRecipeById(int id);

    Observable<List<FavoriteRecipeDataModel>> getAllFavoriteRecipesForMeal(String meal);

}
