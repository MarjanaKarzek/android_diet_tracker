package de.karzek.diettracker.domain.interactor.manager.managerInterface;

import java.util.HashMap;
import java.util.List;

import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;
import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;

/**
 * Created by MarjanaKarzek on 06.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 06.06.2018
 */
public interface NutritionManager {
    HashMap<String, Float> calculateTotalNutrition(List<DiaryEntryDomainModel> diaryEntries);

    HashMap<String, Float> calculateTotalNutritionForGrocery(GroceryDomainModel grocery, float amount);

    HashMap<String, Float> calculateTotalCalories(List<DiaryEntryDomainModel> diaryEntries);

    HashMap<String, Float> calculateTotalCaloriesForGrocery(GroceryDomainModel grocery, float amount);

    HashMap<String, Long> getNutritionMaxValuesForMeal(long mealsTotal);

    HashMap<String, Long> getCaloryMaxValueForMeal(long mealsTotal);

    HashMap<String,Float> getDefaultValuesForTotalCalories();

    HashMap<String,Float> getDefaultValuesForTotalNutrition();
}
