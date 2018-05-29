package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.presentation.main.cookbook.CookbookContract;
import de.karzek.diettracker.presentation.main.cookbook.CookbookPresenter;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Module
public class CookbookModule {

    //presentation

    @Provides
    CookbookContract.Presenter provideCookbookPresenter() {
        return new CookbookPresenter();
    }
}
