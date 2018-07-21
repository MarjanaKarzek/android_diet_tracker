package de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeManipulationModule;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationActivity;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {RecipeManipulationModule.class})
public interface RecipeManipulationComponent {

    void inject(RecipeManipulationActivity activity);

}
