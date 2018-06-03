package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.MealCacheImpl;
import de.karzek.diettracker.data.cache.UnitCacheImpl;
import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.mapper.MealDataMapper;
import de.karzek.diettracker.data.mapper.UnitDataMapper;
import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.datasource.local.MealLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.datasource.local.UnitLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class MealRepositoryImpl implements MealRepository {

    private final MealDataMapper mapper;
    private final MealCacheImpl mealCache;

    public MealRepositoryImpl(MealCacheImpl mealCache, MealDataMapper mapper) {
        this.mealCache = mealCache;
        this.mapper = mapper;
    }

    @Override
    public Observable<Boolean> putAllMeals(List<MealDataModel> mealDataModels) {
        return new MealLocalDataSourceImpl(mealCache).putAllMeals(mapper.transformAllToEntity(mealDataModels));
    }

    @Override
    public Observable<List<MealDataModel>> getAllMeals() {
        return new MealLocalDataSourceImpl(mealCache).getAllMeals().map(new Function<List<MealEntity>, List<MealDataModel>>() {
            @Override
            public List<MealDataModel> apply(List<MealEntity> mealEntities) {
                return mapper.transformAll(mealEntities);
            }
        });
    }
}