package de.karzek.diettracker.presentation.dependencyInjection.component;

import javax.inject.Singleton;

import dagger.Component;
import de.karzek.diettracker.presentation.dependencyInjection.module.AndroidModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.AppModule;
import de.karzek.diettracker.presentation.main.MainActivity;

/**
 * Created by MarjanaKarzek on 28.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.04.2018
 */
@Singleton
@Component(modules = {AppModule.class, AndroidModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

}
