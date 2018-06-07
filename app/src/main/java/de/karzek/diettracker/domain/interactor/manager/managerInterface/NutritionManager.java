package de.karzek.diettracker.domain.interactor.manager.managerInterface;

import java.util.HashMap;
import java.util.List;

import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;

/**
 * Created by MarjanaKarzek on 06.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 06.06.2018
 */
public interface NutritionManager {
    HashMap<String, Float> calculateTotalNutrition(List<DiaryEntryDomainModel> diaryEntries);

    HashMap<String, Float> calculateTotalCalories(List<DiaryEntryDomainModel> diaryEntries);

    HashMap<String, Long> getNutritionMaxValuesForMeal(long mealsTotal);

    HashMap<String, Long> getCaloryMaxValueForMeal(long mealsTotal);
}
