package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.presentation.main.home.HomeContract;
import de.karzek.diettracker.presentation.main.home.HomePresenter;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Module
public class HomeModule {

    //presentation

    @Provides
    HomeContract.Presenter provideHomePresenter() {
        return new HomePresenter();
    }
}
