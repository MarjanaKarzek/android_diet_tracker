package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetAllRecipesUseCase;
import de.karzek.diettracker.presentation.main.cookbook.CookbookContract;
import de.karzek.diettracker.presentation.main.cookbook.CookbookPresenter;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;

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
    CookbookContract.Presenter provideCookbookPresenter(GetAllRecipesUseCase getAllRecipesUseCase,
                                                        RecipeUIMapper mapper) {
        return new CookbookPresenter(getAllRecipesUseCase,
                mapper);
    }
}
