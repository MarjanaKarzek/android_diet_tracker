package de.karzek.diettracker.data.repository.datasource.interfaces;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.RecipeEntity;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface RecipeDataSource {

    Observable<Boolean> putRecipe(RecipeEntity recipeEntity);

    Observable<List<RecipeEntity>> getAllRecipes(ArrayList<String> filterOptions, String sortOption, boolean asc);

    Observable<RecipeEntity> getRecipeById(int id);

    Observable<Boolean> updateRecipe(RecipeEntity recipeEntity);

    Observable<Boolean> deleteRecipe(int id);

    Observable<List<RecipeEntity>> getAllRecipesMatching(String query, ArrayList<String> filterOptions, String sortOption, boolean asc);

    Observable<List<RecipeEntity>> getAllRecipesMatching(String query);
}
