package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.presentation.main.diary.DiaryContract;
import de.karzek.diettracker.presentation.main.diary.DiaryPresenter;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;

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
    DiaryContract.Presenter provideDiaryPresenter(GetAllMealsUseCaseImpl getAllMealsUseCase, MealUIMapper mapper) {
        return new DiaryPresenter(getAllMealsUseCase, mapper);
    }
}
