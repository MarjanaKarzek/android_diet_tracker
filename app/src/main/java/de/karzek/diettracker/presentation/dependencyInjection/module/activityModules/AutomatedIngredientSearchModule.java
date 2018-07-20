package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetAllFavoriteRecipesForMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetExactlyMatchingGroceryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipesMatchingQueryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.PutRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.UpdateRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByIdUseCase;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.AutomatedIngredientSearchContract;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.AutomatedIngredientSearchPresenter;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.search.recipe.RecipeSearchContract;
import de.karzek.diettracker.presentation.search.recipe.RecipeSearchPresenter;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class AutomatedIngredientSearchModule {

    //presentation

    @Provides
    AutomatedIngredientSearchContract.Presenter provideAutomatedIngredientSearchPresenter(GetExactlyMatchingGroceryUseCase getExactlyMatchingGroceryUseCase,
                                                                                          GetAllDefaultUnitsUseCase getAllDefaultUnitsUseCase,
                                                                                          Lazy<PutRecipeUseCase> putRecipeUseCase,
                                                                                          Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase,
                                                                                          Lazy<GetUnitByIdUseCase> getUnitByIdUseCase,
                                                                                          Lazy<UpdateRecipeUseCase> updateRecipeUseCase,
                                                                                          GroceryUIMapper groceryMapper,
                                                                                          UnitUIMapper unitMapper,
                                                                                          RecipeUIMapper recipeMapper) {
        return new AutomatedIngredientSearchPresenter(getExactlyMatchingGroceryUseCase,
                getAllDefaultUnitsUseCase,
                putRecipeUseCase,
                getGroceryByIdUseCase,
                getUnitByIdUseCase,
                updateRecipeUseCase,
                groceryMapper,
                unitMapper,
                recipeMapper);
    }
}
