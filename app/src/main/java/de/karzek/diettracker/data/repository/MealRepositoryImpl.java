package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.MealCacheImpl;
import de.karzek.diettracker.data.cache.UnitCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.MealCache;
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
    private final MealCache mealCache;

    public MealRepositoryImpl(MealCache mealCache, MealDataMapper mapper) {
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

    @Override
    public Observable<Long> getMealCount() {
        return new MealLocalDataSourceImpl(mealCache).getMealCount();
    }

    @Override
    public Observable<MealDataModel> getMealById(int id) {
        return new MealLocalDataSourceImpl(mealCache).getMealById(id).map(new Function<MealEntity, MealDataModel>() {
            @Override
            public MealDataModel apply(MealEntity mealEntity) throws Exception {
                return mapper.transform(mealEntity);
            }
        });
    }

    @Override
    public Observable<MealDataModel> getMealByName(String meal) {
        return new MealLocalDataSourceImpl(mealCache).getMealByName(meal).map(new Function<MealEntity, MealDataModel>() {
            @Override
            public MealDataModel apply(MealEntity mealEntity) throws Exception {
                return mapper.transform(mealEntity);
            }
        });
    }

    @Override
    public Observable<Boolean> putMeal(MealDataModel meal) {
        return new MealLocalDataSourceImpl(mealCache).putMeal(mapper.transformToEntity(meal));
    }

    @Override
    public Observable<Boolean> deleteMealById(int id) {
        return new MealLocalDataSourceImpl(mealCache).deleteMealById(id);
    }

    @Override
    public Observable<Boolean> updateMeal(MealDataModel meal) {
        return new MealLocalDataSourceImpl(mealCache).updateMeal(mapper.transformToEntity(meal));
    }
}