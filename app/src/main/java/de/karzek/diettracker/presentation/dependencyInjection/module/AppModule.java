package de.karzek.diettracker.presentation.dependencyInjection.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.manager.NutritionManagerImpl;
import de.karzek.diettracker.domain.interactor.manager.SharedPreferencesManagerImpl;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.presentation.main.MainContract;
import de.karzek.diettracker.presentation.main.MainPresenter;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

/**
 * Created by MarjanaKarzek on 28.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.04.2018
 */

@Module
public class AppModule {

    @Provides
    SharedPreferences providesSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    SharedPreferencesUtil provideSharedPreferenceUtil(SharedPreferences sharedPreferences) {
        return new SharedPreferencesUtil(sharedPreferences);
    }

    @Provides
    SharedPreferencesManager provideSharedPreferencesManager(SharedPreferencesUtil sharedPreferencesUtil){
        return new SharedPreferencesManagerImpl(sharedPreferencesUtil);
    }

    @Provides
    NutritionManager provideNutritionManagerImpl(SharedPreferencesUtil sharedPreferencesUtil){
        return new NutritionManagerImpl(sharedPreferencesUtil);
    }
}
