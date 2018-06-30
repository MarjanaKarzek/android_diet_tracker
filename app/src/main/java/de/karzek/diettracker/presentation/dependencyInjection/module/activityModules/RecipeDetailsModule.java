package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe.RemoveFavoriteRecipeByTitleUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetFavoriteStateForRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.PutFavoriteRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.RemoveFavoriteRecipeByTitleUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.RecipeDetailsContract;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.RecipeDetailsPresenter;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class RecipeDetailsModule {

    //presentation

    @Provides
    RecipeDetailsContract.Presenter provideRecipeDetailsPresenter(GetRecipeByIdUseCase getRecipeByIdUseCase,
                                                                  SharedPreferencesManager sharedPreferencesManager,
                                                                  Lazy<PutFavoriteRecipeUseCase> putFavoriteRecipeUseCase,
                                                                  Lazy<RemoveFavoriteRecipeByTitleUseCase> removeFavoriteRecipeByTitleUseCase,
                                                                  Lazy<GetFavoriteStateForRecipeByIdUseCase> getFavoriteStateForRecipeByIdUseCase,
                                                                  RecipeUIMapper recipeUIMapper) {
        return new RecipeDetailsPresenter(getRecipeByIdUseCase,
                sharedPreferencesManager,
                putFavoriteRecipeUseCase,
                removeFavoriteRecipeByTitleUseCase,
                getFavoriteStateForRecipeByIdUseCase,
                recipeUIMapper);
    }

}
