package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.repository.repositoryInterface.DiaryEntryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetAllDiaryEntriesMatchingUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.UpdateMealOfDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.UpdateMealOfDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetAllFavoriteRecipesForMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByNameUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealCountUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.mapper.DiaryEntryDomainMapper;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealContract;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealPresenter;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class GenericMealModule {

    //presentation

    @Provides
    GenericMealContract.Presenter provideGenericMealPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                              GetAllDiaryEntriesMatchingUseCase getAllDiaryEntriesMatchingUseCase,
                                                              GetAllFavoriteRecipesForMealUseCase getAllFavoriteRecipesForMealUseCase,
                                                              GetMealCountUseCase getMealCountUseCase,
                                                              Lazy<GetAllMealsUseCase> getAllMealsUseCase,
                                                              Lazy<GetMealByNameUseCase> getMealByNameUseCase,
                                                              Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase,
                                                              Lazy<DeleteDiaryEntryUseCase> deleteDiaryEntryUseCase,
                                                              Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                                                              Lazy<UpdateMealOfDiaryEntryUseCase> updateMealOfDiaryEntryUseCase,
                                                              NutritionManager nutritionManager,
                                                              MealUIMapper mealMapper,
                                                              DiaryEntryUIMapper diaryEntryMapper,
                                                              RecipeUIMapper recipeMapper) {
        return new GenericMealPresenter(sharedPreferencesUtil,
                getAllDiaryEntriesMatchingUseCase,
                getAllFavoriteRecipesForMealUseCase,
                getMealCountUseCase,
                getAllMealsUseCase,
                getMealByNameUseCase,
                getRecipeByIdUseCase,
                deleteDiaryEntryUseCase,
                putDiaryEntryUseCase,
                updateMealOfDiaryEntryUseCase,
                nutritionManager,
                mealMapper,
                diaryEntryMapper,
                recipeMapper);
    }
}
