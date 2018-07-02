package de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.RecipeFilterOptionsDialogModule;
import de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog.RecipeFilterOptionsDialog;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {RecipeFilterOptionsDialogModule.class})
public interface RecipeFilterOptionsDialogComponent {

    void inject(RecipeFilterOptionsDialog dialog);

}
