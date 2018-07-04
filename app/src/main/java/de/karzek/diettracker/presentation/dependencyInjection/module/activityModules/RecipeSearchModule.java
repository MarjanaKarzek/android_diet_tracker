package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.FavoriteGroceryCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.mapper.FavoriteGroceryDataMapper;
import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteGroceryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.GetFavoriteGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetMatchingGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetUnitByNameUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetAllFavoriteRecipesForMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetMatchingGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipesMatchingQueryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByNameUseCase;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.FavoriteGroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchContract;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchPresenter;
import de.karzek.diettracker.presentation.search.recipe.RecipeSearchContract;
import de.karzek.diettracker.presentation.search.recipe.RecipeSearchPresenter;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class RecipeSearchModule {

    //presentation

    @Provides
    RecipeSearchContract.Presenter provideRecipeSearchPresenter(GetMealByIdUseCase getMealByIdUseCase,
                                                                GetAllFavoriteRecipesForMealUseCase getAllFavoriteRecipesForMealUseCase,
                                                                Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase,
                                                                Lazy<GetRecipesMatchingQueryUseCase> getRecipesMatchingQueryUseCase,
                                                                Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                                                                RecipeUIMapper recipeMapper,
                                                                MealUIMapper mealMapper,
                                                                DiaryEntryUIMapper diaryEntryMapper) {
        return new RecipeSearchPresenter(getMealByIdUseCase,
                getAllFavoriteRecipesForMealUseCase,
                getRecipeByIdUseCase,
                getRecipesMatchingQueryUseCase,
                putDiaryEntryUseCase,
                recipeMapper,
                mealMapper,
                diaryEntryMapper);
    }
}
