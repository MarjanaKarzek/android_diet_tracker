package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.AllergenCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.AllergenCache;
import de.karzek.diettracker.data.cache.model.AllergenEntity;
import de.karzek.diettracker.data.mapper.AllergenDataMapper;
import de.karzek.diettracker.data.model.AllergenDataModel;
import de.karzek.diettracker.data.model.ServingDataModel;
import de.karzek.diettracker.data.repository.datasource.local.AllergenLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.datasource.local.ServingLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.AllergenRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class AllergenRepositoryImpl implements AllergenRepository {

    private final AllergenDataMapper mapper;
    private final AllergenCache allergenCache;

    public AllergenRepositoryImpl(AllergenCache allergenCache, AllergenDataMapper mapper) {
        this.allergenCache = allergenCache;
        this.mapper = mapper;
    }

    @Override
    public Observable<Boolean> putAllAllergens(List<AllergenDataModel> allergenDataModels) {
        return new AllergenLocalDataSourceImpl(allergenCache).putAllAllergens(mapper.transformAllToEntity(allergenDataModels));
    }

    @Override
    public Observable<AllergenDataModel> getAllergenById(Integer id) {
        return new AllergenLocalDataSourceImpl(allergenCache).getAllergenById(id).map(new Function<AllergenEntity, AllergenDataModel>() {
            @Override
            public AllergenDataModel apply(AllergenEntity entity) {
                return mapper.transform(entity);
            }
        });
    }

    @Override
    public Observable<List<AllergenDataModel>> getAllAllergens() {
        return new AllergenLocalDataSourceImpl(allergenCache).getAllAllergens().map(new Function<List<AllergenEntity>, List<AllergenDataModel>>() {
            @Override
            public List<AllergenDataModel> apply(List<AllergenEntity> entities) {
                return mapper.transformAll(entities);
            }
        });
    }
}