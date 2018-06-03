package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.GroceryCache;
import de.karzek.diettracker.data.cache.interfaces.UnitCache;
import de.karzek.diettracker.data.cache.model.GroceryEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.datasource.interfaces.GroceryDataSource;
import de.karzek.diettracker.data.repository.datasource.interfaces.UnitDataSource;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class UnitLocalDataSourceImpl implements UnitDataSource {

    private final UnitCache unitCache;

    public UnitLocalDataSourceImpl(UnitCache unitCache){
        this.unitCache = unitCache;
    }

    @Override
    public Observable<Boolean> putAllUnits(List<UnitEntity> unitEntities) {
        return unitCache.putAllUnits(unitEntities);
    }

    @Override
    public Observable<List<UnitEntity>> getAllDefaultUnits(int type) {
        return unitCache.getAllDefaultUnits(type);
    }
}
