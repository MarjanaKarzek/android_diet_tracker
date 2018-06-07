package de.karzek.diettracker.data.repository.repositoryInterface;

import java.util.List;

import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface MealRepository {

    Observable<Boolean> putAllMeals(List<MealDataModel> mealDataModels);

    Observable<List<MealDataModel>> getAllMeals();

    Observable<Long> getMealCount();
}
