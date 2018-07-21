package de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.AutomatedIngredientSearchModule;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.AutomatedIngredientSearchActivity;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {AutomatedIngredientSearchModule.class})
public interface AutomatedIngredientSearchComponent {

    void inject(AutomatedIngredientSearchActivity activity);

}
