package de.karzek.diettracker.data.repository.datasource.interfaces;

import java.util.List;

import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.MealDataModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface MealDataSource {

    Observable<Boolean> putAllMeals(List<MealEntity> mealEntities);

    Observable<List<MealEntity>> getAllMeals();

    Observable<Long> getMealCount();

    Observable<MealEntity> getMealById(int id);

    Observable<Boolean> updateMealTime(int id, String startTime, String endTime);

    Observable<MealEntity> getMealByName(String meal);

}
