package de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.SplashModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.AllergenModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.SettingsModule;
import de.karzek.diettracker.presentation.main.settings.SettingsFragment;
import de.karzek.diettracker.presentation.splash.SplashActivity;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {SplashModule.class})
public interface SplashComponent {

    void inject(SplashActivity activity);
}
