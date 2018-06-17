package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationContract;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationPresenter;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
@Module
public class RecipeManipulationModule {

    //presentation

    @Provides
    RecipeManipulationContract.Presenter provideRecipeManipulationPresenter(Lazy<GetAllDefaultUnitsUseCase> getAllDefaultUnitsUseCase,
                                                                            UnitUIMapper unitMapper) {
        return new RecipeManipulationPresenter(getAllDefaultUnitsUseCase, unitMapper);
    }
}
