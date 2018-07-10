package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetAllFavoriteRecipesForMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.presentation.main.home.HomeContract;
import de.karzek.diettracker.presentation.main.home.HomePresenter;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Module
public class HomeModule {

    //presentation

    @Provides
    HomeContract.Presenter provideHomePresenter(GetMealByIdUseCase getMealByIdUseCase,
                                                GetAllFavoriteRecipesForMealUseCase getAllFavoriteRecipesForMealUseCase,
                                                Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase,
                                                GetAllDiaryEntriesMatchingUseCase getAllDiaryEntriesMatchingUseCase,
                                                Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                                                NutritionManager nutritionManager,
                                                SharedPreferencesManager sharedPreferencesManager,
                                                MealUIMapper mealMapper,
                                                RecipeUIMapper recipeMapper,
                                                DiaryEntryUIMapper diaryEntryMapper) {
        return new HomePresenter(getMealByIdUseCase,
                getAllFavoriteRecipesForMealUseCase,
                getRecipeByIdUseCase,
                getAllDiaryEntriesMatchingUseCase,
                putDiaryEntryUseCase,
                nutritionManager,
                sharedPreferencesManager,
                mealMapper,
                recipeMapper,
                diaryEntryMapper);
    }
}
