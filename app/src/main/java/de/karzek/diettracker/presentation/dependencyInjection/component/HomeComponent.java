package de.karzek.diettracker.presentation.dependencyInjection.component;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.HomeModule;
import de.karzek.diettracker.presentation.main.home.HomeFragment;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {HomeModule.class})
public interface HomeComponent {

    void inject(HomeFragment fragment);
}
