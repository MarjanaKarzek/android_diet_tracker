package de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeDetailsModule;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.RecipeDetailsActivity;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {RecipeDetailsModule.class})
public interface RecipeDetailsComponent {

    void inject(RecipeDetailsActivity activity);

}
