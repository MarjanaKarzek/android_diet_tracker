package de.karzek.diettracker.data.cache.interfaces;

import java.util.List;

import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.cache.model.FavoriteRecipeEntity;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface FavoriteRecipeCache {

    boolean isExpired();
    boolean isCached();

    Observable<List<FavoriteRecipeEntity>> getAllFavoriteRecipes();
    Observable<Boolean> putFavoriteRecipe(FavoriteRecipeEntity entity);
    Observable<Boolean> removeFavoriteRecipeByTitle(String title);
    Observable<Boolean> getFavoriteStateForRecipeById(int id);
    Observable<List<FavoriteRecipeEntity>> getAllFavoriteRecipesForMeal(String meal);

}
