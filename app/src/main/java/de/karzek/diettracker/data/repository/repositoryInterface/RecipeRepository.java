package de.karzek.diettracker.data.repository.repositoryInterface;

import java.util.List;

import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.model.RecipeDataModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface RecipeRepository {

    Observable<Boolean> putRecipe(RecipeDataModel recipeEntity);

    Observable<List<RecipeDataModel>> getAllRecipes();

    Observable<RecipeDataModel> getRecipeById(int id);

}
