package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.MealCache;
import de.karzek.diettracker.data.cache.interfaces.RecipeCache;
import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.RecipeEntity;
import de.karzek.diettracker.data.repository.datasource.interfaces.MealDataSource;
import de.karzek.diettracker.data.repository.datasource.interfaces.RecipeDataSource;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class RecipeLocalDataSourceImpl implements RecipeDataSource {

    private final RecipeCache recipeCache;

    public RecipeLocalDataSourceImpl(RecipeCache recipeCache){
        this.recipeCache = recipeCache;
    }

    @Override
    public Observable<Boolean> putRecipe(RecipeEntity recipeEntity) {
        return recipeCache.putRecipe(recipeEntity);
    }

    @Override
    public Observable<List<RecipeEntity>> getAllRecipes() {
        return recipeCache.getAllRecipes();
    }

    @Override
    public Observable<RecipeEntity> getRecipeById(int id) {
        return recipeCache.getRecipeById(id);
    }
}