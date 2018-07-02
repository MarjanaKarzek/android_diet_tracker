package de.karzek.diettracker.data.repository.datasource.local;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.RecipeCache;
import de.karzek.diettracker.data.cache.model.RecipeEntity;
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
    public Observable<List<RecipeEntity>> getAllRecipes(ArrayList<String> filterOptions, String sortOption, boolean asc) {
        return recipeCache.getAllRecipes(filterOptions, sortOption, asc);
    }

    @Override
    public Observable<RecipeEntity> getRecipeById(int id) {
        return recipeCache.getRecipeById(id);
    }

    @Override
    public Observable<Boolean> updateRecipe(RecipeEntity recipeEntity) {
        return recipeCache.updateRecipe(recipeEntity);
    }

    @Override
    public Observable<Boolean> deleteRecipe(int id) {
        return recipeCache.deleteRecipe(id);
    }

    @Override
    public Observable<List<RecipeEntity>> getAllRecipesMatching(String query, ArrayList<String> filterOptions, String sortOption, boolean asc) {
        return recipeCache.getAllRecipesMatching(query, filterOptions, sortOption, asc);
    }

}
