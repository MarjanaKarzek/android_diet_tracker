package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.ServingCacheImpl;
import de.karzek.diettracker.data.cache.UnitCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.ServingCache;
import de.karzek.diettracker.data.mapper.ServingDataMapper;
import de.karzek.diettracker.data.mapper.UnitDataMapper;
import de.karzek.diettracker.data.model.ServingDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.datasource.local.ServingLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.datasource.local.UnitLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.ServingRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class ServingRepositoryImpl implements ServingRepository {

    private final ServingDataMapper mapper;
    private final ServingCache servingCache;

    public ServingRepositoryImpl(ServingCache servingCache, ServingDataMapper mapper) {
        this.servingCache = servingCache;
        this.mapper = mapper;
    }

    @Override
    public Observable<Boolean> putAllServings(List<ServingDataModel> servingDataModels) {
        return new ServingLocalDataSourceImpl(servingCache).putAllServings(mapper.transformAllToEntity(servingDataModels));
    }
}