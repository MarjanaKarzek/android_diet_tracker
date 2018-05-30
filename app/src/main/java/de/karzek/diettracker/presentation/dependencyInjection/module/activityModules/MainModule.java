package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.presentation.main.MainContract;
import de.karzek.diettracker.presentation.main.MainPresenter;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class MainModule {

    //presentation

    @Provides
    MainContract.Presenter provideMainPresenter() {
        return new MainPresenter();
    }
}
