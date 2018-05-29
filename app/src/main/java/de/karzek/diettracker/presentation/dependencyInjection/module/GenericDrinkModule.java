package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.presentation.diary.drink.GenericDrinkContract;
import de.karzek.diettracker.presentation.diary.drink.GenericDrinkPresenter;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class GenericDrinkModule {

    //presentation

    @Provides
    GenericDrinkContract.Presenter provideGenericDrinkPresenter() {
        return new GenericDrinkPresenter();
    }
}
