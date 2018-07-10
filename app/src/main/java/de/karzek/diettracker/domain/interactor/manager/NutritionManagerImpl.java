package de.karzek.diettracker.domain.interactor.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;
import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_CALORIES_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_CARBS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_FATS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_PROTEINS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_CALORIES_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_CARBS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_FATS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_PROTEINS_DAILY;

/**
 * Created by MarjanaKarzek on 06.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 06.06.2018
 */
public class NutritionManagerImpl implements NutritionManager {

    private SharedPreferencesUtil sharedPreferencesUtil;

    public NutritionManagerImpl(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public HashMap<String, Float> calculateTotalNutritionForDiaryEntry(List<DiaryEntryDomainModel> diaryEntries) {
        HashMap<String, Float> values = new HashMap<>();

        float calories = 0.0f;
        float proteins = 0.0f;
        float carbs = 0.0f;
        float fats = 0.0f;

        for (DiaryEntryDomainModel entry : diaryEntries) {
            calories += entry.getGrocery().getCalories_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
            proteins += entry.getGrocery().getProteins_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
            carbs += entry.getGrocery().getCarbohydrates_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
            fats += entry.getGrocery().getFats_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
        }

        values.put(Constants.CALORIES, calories);
        values.put(Constants.PROTEINS, proteins);
        values.put(Constants.CARBS, carbs);
        values.put(Constants.FATS, fats);

        return values;
    }

    @Override
    public HashMap<String, Float> calculateTotalNutritionForGrocery(GroceryDomainModel grocery, float amount) {
        HashMap<String, Float> values = new HashMap<>();

        values.put(Constants.CALORIES, grocery.getCalories_per_1U() * amount);
        values.put(Constants.PROTEINS, grocery.getProteins_per_1U() * amount);
        values.put(Constants.CARBS, grocery.getCarbohydrates_per_1U() * amount);
        values.put(Constants.FATS, grocery.getFats_per_1U() * amount);

        return values;
    }

    @Override
    public HashMap<String, Float> calculateTotalCaloriesForDiaryEntry(List<DiaryEntryDomainModel> diaryEntries) {
        HashMap<String, Float> values = new HashMap<>();

        float calories = 0.0f;

        for (DiaryEntryDomainModel entry : diaryEntries) {
            calories += entry.getGrocery().getCalories_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
        }

        values.put(Constants.CALORIES, calories);

        return values;
    }

    @Override
    public HashMap<String, Float> calculateTotalCaloriesForGrocery(GroceryDomainModel grocery, float amount) {
        HashMap<String, Float> values = new HashMap<>();

        values.put(Constants.CALORIES, grocery.getCalories_per_1U() * amount);

        return values;
    }

    @Override
    public HashMap<String, Long> getNutritionMaxValuesForMeal(long mealsTotal) {
        HashMap<String, Long> maxValues = new HashMap<>();

        long caloriesMax = sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CALORIES_DAILY, VALUE_REQUIREMENT_CALORIES_DAILY) / mealsTotal;
        long proteinsMax = sharedPreferencesUtil.getInt(KEY_REQUIREMENT_PROTEINS_DAILY, VALUE_REQUIREMENT_PROTEINS_DAILY) / mealsTotal;
        long carbsMax = sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CARBS_DAILY, VALUE_REQUIREMENT_CARBS_DAILY) / mealsTotal;
        long fatsMax = sharedPreferencesUtil.getInt(KEY_REQUIREMENT_FATS_DAILY, VALUE_REQUIREMENT_FATS_DAILY) / mealsTotal;

        maxValues.put(Constants.CALORIES, caloriesMax);
        maxValues.put(Constants.PROTEINS, proteinsMax);
        maxValues.put(Constants.CARBS, carbsMax);
        maxValues.put(Constants.FATS, fatsMax);

        return maxValues;
    }

    @Override
    public HashMap<String, Long> getNutritionMaxValuesForDay() {
        HashMap<String, Long> maxValues = new HashMap<>();

        maxValues.put(Constants.CALORIES, (long)sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CALORIES_DAILY, VALUE_REQUIREMENT_CALORIES_DAILY));
        maxValues.put(Constants.PROTEINS, (long)sharedPreferencesUtil.getInt(KEY_REQUIREMENT_PROTEINS_DAILY, VALUE_REQUIREMENT_PROTEINS_DAILY));
        maxValues.put(Constants.CARBS, (long)sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CARBS_DAILY, VALUE_REQUIREMENT_CARBS_DAILY));
        maxValues.put(Constants.FATS, (long)sharedPreferencesUtil.getInt(KEY_REQUIREMENT_FATS_DAILY, VALUE_REQUIREMENT_FATS_DAILY));

        return maxValues;
    }

    @Override
    public HashMap<String, Long> getCaloryMaxValueForMeal(long mealsTotal) {
        HashMap<String, Long> maxValues = new HashMap<>();

        long caloriesMax = sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CALORIES_DAILY, VALUE_REQUIREMENT_CALORIES_DAILY) / mealsTotal;
        maxValues.put(Constants.CALORIES, caloriesMax);

        return maxValues;
    }

    @Override
    public HashMap<String, Long> getCaloryMaxValueForDay() {
        HashMap<String, Long> maxValues = new HashMap<>();

        maxValues.put(Constants.CALORIES, (long)sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CALORIES_DAILY, VALUE_REQUIREMENT_CALORIES_DAILY));

        return maxValues;
    }

    @Override
    public HashMap<String,Float> getDefaultValuesForTotalCalories() {
        HashMap<String, Float> defaultNutritionValues = new HashMap<>();

        defaultNutritionValues.put(Constants.CALORIES, 0.0f);

        return defaultNutritionValues;
    }

    @Override
    public HashMap<String,Float> getDefaultValuesForTotalNutrition() {
        HashMap<String, Float> defaultNutritionValues = new HashMap<>();

        defaultNutritionValues.put(Constants.CALORIES, 0.0f);
        defaultNutritionValues.put(Constants.PROTEINS, 0.0f);
        defaultNutritionValues.put(Constants.CARBS, 0.0f);
        defaultNutritionValues.put(Constants.FATS, 0.0f);

        return defaultNutritionValues;
    }

    @Override
    public HashMap<String, Float> calculateTotalCaloriesForRecipe(ArrayList<IngredientDisplayModel> ingredients, float portions, float selectedPortions) {
        HashMap<String, Float> values = new HashMap<>();

        float calories = 0.0f;

        for (IngredientDisplayModel entry : ingredients) {
            calories += entry.getGrocery().getCalories_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
        }

        calories = calories / portions * selectedPortions;

        values.put(Constants.CALORIES, calories);

        return values;
    }

    @Override
    public HashMap<String, Float> calculateTotalNutritionsForRecipe(ArrayList<IngredientDisplayModel> ingredients, float portions, float selectedPortions) {
        HashMap<String, Float> values = new HashMap<>();

        float calories = 0.0f;
        float proteins = 0.0f;
        float carbs = 0.0f;
        float fats = 0.0f;

        for (IngredientDisplayModel entry : ingredients) {
            calories += entry.getGrocery().getCalories_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
            proteins += entry.getGrocery().getProteins_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
            carbs += entry.getGrocery().getCarbohydrates_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
            fats += entry.getGrocery().getFats_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
        }

        calories = calories / portions * selectedPortions;
        proteins = proteins / portions * selectedPortions;
        carbs = carbs / portions * selectedPortions;
        fats = fats / portions * selectedPortions;

        values.put(Constants.CALORIES, calories);
        values.put(Constants.PROTEINS, proteins);
        values.put(Constants.CARBS, carbs);
        values.put(Constants.FATS, fats);

        return values;
    }

    @Override
    public float getCaloriesSumForDiaryEntries(List<DiaryEntryDomainModel> displayModels) {
        float caloriesSum = 0.0f;

        for (DiaryEntryDomainModel entry: displayModels)
            caloriesSum += entry.getAmount() * entry.getGrocery().getCalories_per_1U() * entry.getUnit().getMultiplier();

        return caloriesSum;
    }

    @Override
    public HashMap<String, Float> getNutritionSumsForDiaryEntries(List<DiaryEntryDomainModel> displayModels) {
        HashMap<String, Float> values = new HashMap<>();

        float calories = 0.0f;
        float proteins = 0.0f;
        float carbs = 0.0f;
        float fats = 0.0f;

        for (DiaryEntryDomainModel entry : displayModels) {
            calories += entry.getGrocery().getCalories_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
            proteins += entry.getGrocery().getProteins_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
            carbs += entry.getGrocery().getCarbohydrates_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
            fats += entry.getGrocery().getFats_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
        }

        values.put(Constants.CALORIES, calories);
        values.put(Constants.PROTEINS, proteins);
        values.put(Constants.CARBS, carbs);
        values.put(Constants.FATS, fats);

        return values;
    }
}
