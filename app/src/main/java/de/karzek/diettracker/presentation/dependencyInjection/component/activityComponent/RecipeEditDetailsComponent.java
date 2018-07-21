package de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeDetailsModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeEditDetailsModule;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.RecipeDetailsActivity;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.RecipeEditDetailsActivity;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {RecipeEditDetailsModule.class})
public interface RecipeEditDetailsComponent {

    void inject(RecipeEditDetailsActivity activity);

}
