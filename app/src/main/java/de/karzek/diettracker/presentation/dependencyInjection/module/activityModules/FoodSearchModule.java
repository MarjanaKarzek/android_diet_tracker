package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.presentation.search.food.FoodSearchContract;
import de.karzek.diettracker.presentation.search.food.FoodSearchPresenter;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class FoodSearchModule {

    //presentation

    @Provides
    FoodSearchContract.Presenter provideFoodSearchPresenter() {
        return new FoodSearchPresenter();
    }
}
