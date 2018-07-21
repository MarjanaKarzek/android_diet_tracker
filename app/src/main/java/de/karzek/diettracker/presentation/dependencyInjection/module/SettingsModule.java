package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllergenByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteAllDiaryEntriesMatchingMealIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.DeleteMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.PutMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.UpdateMealUseCase;
import de.karzek.diettracker.presentation.main.settings.SettingsContract;
import de.karzek.diettracker.presentation.main.settings.SettingsPresenter;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Module
public class SettingsModule {

    //presentation

    @Provides
    SettingsContract.Presenter provideSettingsPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                                        GetAllMealsUseCase getAllMealsUseCase,
                                                        GetAllergenByIdUseCase getAllergenByIdUseCase,
                                                        SharedPreferencesManager sharedPreferencesManager,
                                                        Lazy<GetMealByIdUseCase> getMealByIdUseCase,
                                                        Lazy<UpdateMealUseCase> updateMealTimeUseCase,
                                                        Lazy<PutMealUseCase> putMealUseCase,
                                                        Lazy<DeleteAllDiaryEntriesMatchingMealIdUseCase> deleteAllDiaryEntriesMatchingMealIdUseCase,
                                                        Lazy<DeleteMealByIdUseCase> deleteMealByIdUseCase,
                                                        MealUIMapper mealMapper,
                                                        AllergenUIMapper allergenMapper) {
        return new SettingsPresenter(sharedPreferencesUtil,
                getAllMealsUseCase,
                getAllergenByIdUseCase,
                sharedPreferencesManager,
                getMealByIdUseCase,
                updateMealTimeUseCase,
                putMealUseCase,
                deleteAllDiaryEntriesMatchingMealIdUseCase,
                deleteMealByIdUseCase,
                mealMapper,
                allergenMapper);
    }
}
