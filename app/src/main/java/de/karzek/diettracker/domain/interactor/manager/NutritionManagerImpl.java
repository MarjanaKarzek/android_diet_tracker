package de.karzek.diettracker.domain.interactor.manager;

import java.util.HashMap;
import java.util.List;

import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;
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
    public HashMap<String, Float> calculateTotalNutrition(List<DiaryEntryDomainModel> diaryEntries) {
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

        values.put(Constants.calories, calories);
        values.put(Constants.proteins, proteins);
        values.put(Constants.carbs, carbs);
        values.put(Constants.fats, fats);

        return values;
    }

    @Override
    public HashMap<String, Float> calculateTotalCalories(List<DiaryEntryDomainModel> diaryEntries) {
        HashMap<String, Float> values = new HashMap<>();

        float calories = 0.0f;

        for (DiaryEntryDomainModel entry : diaryEntries) {
            calories += entry.getGrocery().getCalories_per_1U() * entry.getAmount() * entry.getUnit().getMultiplier();
        }

        values.put(Constants.calories, calories);

        return values;
    }

    @Override
    public HashMap<String, Long> getNutritionMaxValuesForMeal(long mealsTotal) {
        HashMap<String, Long> maxValues = new HashMap<>();

        long caloriesMax = sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CALORIES_DAILY, VALUE_REQUIREMENT_CALORIES_DAILY) / mealsTotal;
        long proteinsMax = sharedPreferencesUtil.getInt(KEY_REQUIREMENT_PROTEINS_DAILY, VALUE_REQUIREMENT_PROTEINS_DAILY) / mealsTotal;
        long carbsMax = sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CARBS_DAILY, VALUE_REQUIREMENT_CARBS_DAILY) / mealsTotal;
        long fatsMax = sharedPreferencesUtil.getInt(KEY_REQUIREMENT_FATS_DAILY, VALUE_REQUIREMENT_FATS_DAILY) / mealsTotal;

        maxValues.put(Constants.calories, caloriesMax);
        maxValues.put(Constants.proteins, proteinsMax);
        maxValues.put(Constants.carbs, carbsMax);
        maxValues.put(Constants.fats, fatsMax);

        return maxValues;
    }

    @Override
    public HashMap<String, Long> getCaloryMaxValueForMeal(long mealsTotal) {
        HashMap<String, Long> maxValues = new HashMap<>();

        long caloriesMax = sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CALORIES_DAILY, VALUE_REQUIREMENT_CALORIES_DAILY) / mealsTotal;
        maxValues.put(Constants.calories, caloriesMax);

        return maxValues;
    }

    public HashMap<String,Float> getDefaultValuesForTotalCalories() {
        HashMap<String, Float> defaultNutritionValues = new HashMap<>();

        defaultNutritionValues.put(Constants.calories, 0.0f);

        return defaultNutritionValues;
    }

    public HashMap<String,Float> getDefaultValuesForTotalNutrition() {
        HashMap<String, Float> defaultNutritionValues = new HashMap<>();

        defaultNutritionValues.put(Constants.calories, 0.0f);
        defaultNutritionValues.put(Constants.proteins, 0.0f);
        defaultNutritionValues.put(Constants.carbs, 0.0f);
        defaultNutritionValues.put(Constants.fats, 0.0f);

        return defaultNutritionValues;
    }
}
