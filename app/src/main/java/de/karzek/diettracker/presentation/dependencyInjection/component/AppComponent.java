package de.karzek.diettracker.presentation.dependencyInjection.component;

import javax.inject.Singleton;

import dagger.Component;
import de.karzek.diettracker.presentation.dependencyInjection.module.AndroidModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.AppModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.CookbookModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.DiaryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.HomeModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.SettingsModule;
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

    HomeComponent plus(HomeModule module);

    DiaryComponent plus(DiaryModule module);

    CookbookComponent plus(CookbookModule module);

    SettingsComponent plus(SettingsModule module);

    void inject(MainActivity activity);

}
