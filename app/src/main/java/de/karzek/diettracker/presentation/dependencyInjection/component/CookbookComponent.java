package de.karzek.diettracker.presentation.dependencyInjection.component;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.cookbook.CookbookFragment;
import de.karzek.diettracker.presentation.dependencyInjection.module.CookbookModule;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {CookbookModule.class})
public interface CookbookComponent {

    void inject(CookbookFragment fragment);
}
