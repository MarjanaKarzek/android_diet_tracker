package de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.RecipeModule;
import de.karzek.diettracker.presentation.main.cookbook.CookbookFragment;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.CookbookModule;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {CookbookModule.class, RecipeModule.class})
public interface CookbookComponent {

    void inject(CookbookFragment fragment);
}
