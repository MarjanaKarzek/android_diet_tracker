package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.cache.interfaces.FavoriteRecipeCache;
import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.cache.model.FavoriteRecipeEntity;
import de.karzek.diettracker.data.repository.datasource.interfaces.FavoriteGroceryDataSource;
import de.karzek.diettracker.data.repository.datasource.interfaces.FavoriteRecipeDataSource;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteRecipeLocalDataSourceImpl implements FavoriteRecipeDataSource {

    private final FavoriteRecipeCache cache;

    public FavoriteRecipeLocalDataSourceImpl(FavoriteRecipeCache cache){
        this.cache = cache;
    }

    @Override
    public Observable<List<FavoriteRecipeEntity>> getAllFavorites() {
        return cache.getAllFavoriteRecipes();
    }

    @Override
    public Observable<Boolean> putFavoriteRecipe(FavoriteRecipeEntity entity) {
        return cache.putFavoriteRecipe(entity);
    }

    @Override
    public Observable<Boolean> removeFavoriteRecipeByTitle(String title) {
        return cache.removeFavoriteRecipeByTitle(title);
    }

    @Override
    public Observable<Boolean> getFavoriteStateForRecipeById(int id) {
        return cache.getFavoriteStateForRecipeById(id);
    }

    @Override
    public Observable<List<FavoriteRecipeEntity>> getAllFavoriteRecipesForMeal(String meal) {
        return cache.getAllFavoriteRecipesForMeal(meal);
    }

}
