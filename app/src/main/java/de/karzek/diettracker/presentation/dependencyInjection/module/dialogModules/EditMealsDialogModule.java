package de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals.EditMealsDialogContract;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals.EditMealsDialogPresenter;
import de.karzek.diettracker.presentation.main.settings.dialog.editAllergen.EditAllergensDialogContract;
import de.karzek.diettracker.presentation.main.settings.dialog.editAllergen.EditAllergensDialogPresenter;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Module
public class EditMealsDialogModule {

    //presentation

    @Provides
    EditMealsDialogContract.Presenter provideEditMealsDialogPresenter(GetAllMealsUseCase getAllMealsUseCase,
                                                                      MealUIMapper mealMapper) {
        return new EditMealsDialogPresenter(getAllMealsUseCase,
                mealMapper);
    }
}
