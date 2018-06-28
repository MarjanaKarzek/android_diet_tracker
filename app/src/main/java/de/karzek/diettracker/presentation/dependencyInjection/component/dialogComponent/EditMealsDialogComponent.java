package de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.EditAllergensDialogModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.EditMealsDialogModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.AllergenModule;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals.EditMealsDialog;
import de.karzek.diettracker.presentation.main.settings.dialog.editAllergen.EditAllergensDialog;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {EditMealsDialogModule.class})
public interface EditMealsDialogComponent {

    void inject(EditMealsDialog dialog);

}
