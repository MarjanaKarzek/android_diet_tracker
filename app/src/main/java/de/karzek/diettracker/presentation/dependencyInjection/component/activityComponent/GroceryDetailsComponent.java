package de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.GroceryDetailsModule;
import de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsActivity;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {GroceryDetailsModule.class})
public interface GroceryDetailsComponent {

    void inject(GroceryDetailsActivity activity);

}
