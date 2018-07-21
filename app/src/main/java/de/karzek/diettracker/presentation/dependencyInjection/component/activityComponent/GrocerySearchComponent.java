package de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.GrocerySearchModule;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchActivity;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {GrocerySearchModule.class})
public interface GrocerySearchComponent {

    void inject(GrocerySearchActivity activity);

}
