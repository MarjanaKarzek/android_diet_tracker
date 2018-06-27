package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.GroceryCacheImpl;
import de.karzek.diettracker.data.cache.UnitCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.GroceryCache;
import de.karzek.diettracker.data.cache.interfaces.UnitCache;
import de.karzek.diettracker.data.cache.model.GroceryEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.mapper.GroceryDataMapper;
import de.karzek.diettracker.data.mapper.UnitDataMapper;
import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.datasource.local.GroceryLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.datasource.local.UnitLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
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
public class UnitRepositoryImpl implements UnitRepository {

    private final UnitDataMapper mapper;
    private final UnitCache unitCache;

    public UnitRepositoryImpl(UnitCache unitCache, UnitDataMapper mapper) {
        this.unitCache = unitCache;
        this.mapper = mapper;
    }

    @Override
    public Observable<Boolean> putAllUnits(List<UnitDataModel> unitDataModels) {
        return new UnitLocalDataSourceImpl(unitCache).putAllUnits(mapper.transformAllToEntity(unitDataModels));
    }

    @Override
    public Observable<List<UnitDataModel>> getAllDefaultUnits(int type) {
        return new UnitLocalDataSourceImpl(unitCache).getAllDefaultUnits(type).map(new Function<List<UnitEntity>, List<UnitDataModel>>() {
            @Override
            public List<UnitDataModel> apply(List<UnitEntity> unitEntities) {
                return mapper.transformAll(unitEntities);
            }
        });
    }

    @Override
    public Observable<UnitDataModel> getUnitByName(String name) {
        return new UnitLocalDataSourceImpl(unitCache).getUnitByName(name).map(new Function<UnitEntity, UnitDataModel>() {
                @Override
                public UnitDataModel apply(UnitEntity unitEntities) {
                    return mapper.transform(unitEntities);
                }
            });
    }

    @Override
    public Observable<UnitDataModel> getUnitById(int id) {
        return new UnitLocalDataSourceImpl(unitCache).getUnitById(id).map(new Function<UnitEntity, UnitDataModel>() {
            @Override
            public UnitDataModel apply(UnitEntity unitEntities) {
                return mapper.transform(unitEntities);
            }
        });
    }
}