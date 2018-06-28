package de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllAllergensUseCase;
import de.karzek.diettracker.presentation.main.home.HomeContract;
import de.karzek.diettracker.presentation.main.home.HomePresenter;
import de.karzek.diettracker.presentation.main.settings.dialog.editAllergen.EditAllergensDialogContract;
import de.karzek.diettracker.presentation.main.settings.dialog.editAllergen.EditAllergensDialogPresenter;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Module
public class EditAllergensDialogModule {

    //presentation

    @Provides
    EditAllergensDialogContract.Presenter provideEditAllergensDialogPresenter(SharedPreferencesManager sharedPreferencesManager,
                                                                              GetAllAllergensUseCase getAllAllergensUseCase,
                                                                              AllergenUIMapper allergenMapper) {
        return new EditAllergensDialogPresenter(sharedPreferencesManager,
                getAllAllergensUseCase,
                allergenMapper);
    }
}
