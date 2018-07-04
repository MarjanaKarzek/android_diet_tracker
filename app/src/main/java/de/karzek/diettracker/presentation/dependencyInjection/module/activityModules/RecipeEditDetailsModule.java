package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetFavoriteStateForRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.PutFavoriteRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.RemoveFavoriteRecipeByTitleUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.RecipeEditDetailsContract;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.RecipeEditDetailsPresenter;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class RecipeEditDetailsModule {

    //presentation

    @Provides
    RecipeEditDetailsContract.Presenter provideRecipeEditDetailsPresenter(GetRecipeByIdUseCase getRecipeByIdUseCase,
                                                                          SharedPreferencesManager sharedPreferencesManager,
                                                                          NutritionManager nutritionManager,
                                                                          Lazy<PutFavoriteRecipeUseCase> putFavoriteRecipeUseCase,
                                                                          Lazy<RemoveFavoriteRecipeByTitleUseCase> removeFavoriteRecipeByTitleUseCase,
                                                                          Lazy<GetFavoriteStateForRecipeByIdUseCase> getFavoriteStateForRecipeByIdUseCase,
                                                                          GetAllMealsUseCase getAllMealsUseCase,
                                                                          Lazy<GetMealByIdUseCase> getMealByIdUseCase,
                                                                          Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                                                                          RecipeUIMapper recipeMapper,
                                                                          MealUIMapper mealMapper,
                                                                          DiaryEntryUIMapper diaryEntryMapper) {
        return new RecipeEditDetailsPresenter(getRecipeByIdUseCase,
                sharedPreferencesManager,
                nutritionManager,
                putFavoriteRecipeUseCase,
                removeFavoriteRecipeByTitleUseCase,
                getFavoriteStateForRecipeByIdUseCase,
                getAllMealsUseCase,
                getMealByIdUseCase,
                putDiaryEntryUseCase,
                recipeMapper,
                mealMapper,
                diaryEntryMapper);
    }
}
