package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.ServingCache;
import de.karzek.diettracker.data.cache.interfaces.UnitCache;
import de.karzek.diettracker.data.cache.model.ServingEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.repository.datasource.interfaces.ServingDataSource;
import de.karzek.diettracker.data.repository.datasource.interfaces.UnitDataSource;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class ServingLocalDataSourceImpl implements ServingDataSource {

    private final ServingCache servingCache;

    public ServingLocalDataSourceImpl(ServingCache servingCache){
        this.servingCache = servingCache;
    }

    @Override
    public Observable<Boolean> putAllServings(List<ServingEntity> servingEntities) {
        return servingCache.putAllServings(servingEntities);
    }
}
