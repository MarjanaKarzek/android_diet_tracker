package de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog.RecipeFilterOptionsDialogContract;
import de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog.RecipeFilterOptionsDialogPresenter;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Module
public class RecipeFilterOptionsDialogModule {

    //presentation

    @Provides
    RecipeFilterOptionsDialogContract.Presenter provideRecipeFilterOptionsDialogPresenter(GetAllMealsUseCase getAllMealsUseCase,
                                                                                          MealUIMapper mapper) {
        return new RecipeFilterOptionsDialogPresenter(getAllMealsUseCase,
                mapper);
    }
}
