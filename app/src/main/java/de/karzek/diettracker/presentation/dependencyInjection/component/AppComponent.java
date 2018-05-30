package de.karzek.diettracker.presentation.dependencyInjection.component;

import javax.inject.Singleton;

import dagger.Component;
import de.karzek.diettracker.presentation.dependencyInjection.module.AndroidModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.AppModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.CookbookModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.DiaryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.GenericDrinkModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.GenericMealModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.HomeModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.FoodSearchModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.MainModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.SettingsModule;
import de.karzek.diettracker.presentation.main.MainActivity;
import de.karzek.diettracker.presentation.search.food.FoodSearchActivity;

/**
 * Created by MarjanaKarzek on 28.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.04.2018
 */
@Singleton
@Component(modules = {AppModule.class, AndroidModule.class, MainModule.class, FoodSearchModule.class})
public interface AppComponent {

    HomeComponent plus(HomeModule module);

    DiaryComponent plus(DiaryModule module);

    CookbookComponent plus(CookbookModule module);

    SettingsComponent plus(SettingsModule module);

    GenericMealComponent plus(GenericMealModule module);

    GenericDrinkComponent plus(GenericDrinkModule module);

    void inject(MainActivity activity);

    void inject(FoodSearchActivity activity);

}
