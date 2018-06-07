package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.MealCache;
import de.karzek.diettracker.data.cache.interfaces.UnitCache;
import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.repository.datasource.interfaces.MealDataSource;
import de.karzek.diettracker.data.repository.datasource.interfaces.UnitDataSource;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class MealLocalDataSourceImpl implements MealDataSource {

    private final MealCache mealCache;

    public MealLocalDataSourceImpl(MealCache mealCache){
        this.mealCache = mealCache;
    }

    @Override
    public Observable<Boolean> putAllMeals(List<MealEntity> mealEntities) {
        return mealCache.putAllMeals(mealEntities);
    }

    @Override
    public Observable<List<MealEntity>> getAllMeals() {
        return mealCache.getAllMeals();
    }

    @Override
    public Observable<Long> getMealCount() {
        return mealCache.getMealCount();
    }
}
