package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.presentation.main.diary.DiaryContract;
import de.karzek.diettracker.presentation.main.diary.DiaryPresenter;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Module
public class DiaryModule {

    //presentation

    @Provides
    DiaryContract.Presenter provideDiaryPresenter() {
        return new DiaryPresenter();
    }
}
