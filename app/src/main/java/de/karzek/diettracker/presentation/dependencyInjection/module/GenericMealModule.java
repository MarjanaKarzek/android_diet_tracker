package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealContract;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealPresenter;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

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
    GenericMealContract.Presenter provideGenericMealPresenter(SharedPreferencesUtil sharedPreferencesUtil) {
        return new GenericMealPresenter(sharedPreferencesUtil);
    }
}
