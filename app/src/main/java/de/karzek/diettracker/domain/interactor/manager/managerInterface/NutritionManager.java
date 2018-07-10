package de.karzek.diettracker.domain.interactor.manager.managerInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;
import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;

/**
 * Created by MarjanaKarzek on 06.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 06.06.2018
 */
public interface NutritionManager {

    HashMap<String, Float> calculateTotalNutritionForDiaryEntry(List<DiaryEntryDomainModel> diaryEntries);

    HashMap<String, Float> calculateTotalNutritionForGrocery(GroceryDomainModel grocery, float amount);

    HashMap<String, Float> calculateTotalCaloriesForDiaryEntry(List<DiaryEntryDomainModel> diaryEntries);

    HashMap<String, Float> calculateTotalCaloriesForGrocery(GroceryDomainModel grocery, float amount);

    HashMap<String, Long> getNutritionMaxValuesForMeal(long mealsTotal);

    HashMap<String, Long> getNutritionMaxValuesForDay();

    HashMap<String, Long> getCaloryMaxValueForMeal(long mealsTotal);

    HashMap<String, Long> getCaloryMaxValueForDay();

    HashMap<String, Float> getDefaultValuesForTotalCalories();

    HashMap<String, Float> getDefaultValuesForTotalNutrition();

    HashMap<String, Float> calculateTotalCaloriesForRecipe(ArrayList<IngredientDisplayModel> ingredients, float portions, float selectedPortions);

    HashMap<String, Float> calculateTotalNutritionsForRecipe(ArrayList<IngredientDisplayModel> ingredients, float portions, float selectedPortions);

}
