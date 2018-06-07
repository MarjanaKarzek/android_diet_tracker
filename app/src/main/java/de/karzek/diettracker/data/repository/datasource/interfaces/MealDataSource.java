package de.karzek.diettracker.data.repository.datasource.interfaces;

import java.util.List;

import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
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
}
