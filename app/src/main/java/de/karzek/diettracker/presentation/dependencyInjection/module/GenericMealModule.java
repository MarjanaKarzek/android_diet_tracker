package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.presentation.diary.meal.GenericMealContract;
import de.karzek.diettracker.presentation.diary.meal.GenericMealPresenter;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class GenericMealModule {

    //presentation

    @Provides
    GenericMealContract.Presenter provideGenericMealPresenter() {
        return new GenericMealPresenter();
    }
}
