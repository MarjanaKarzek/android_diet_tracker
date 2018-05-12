package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.presentation.settings.SettingsContract;
import de.karzek.diettracker.presentation.settings.SettingsPresenter;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Module
public class SettingsModule {

    //presentation

    @Provides
    SettingsContract.Presenter provideSettingsPresenter() {
        return new SettingsPresenter();
    }
}
