package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.AllergenCache;
import de.karzek.diettracker.data.cache.interfaces.ServingCache;
import de.karzek.diettracker.data.cache.model.AllergenEntity;
import de.karzek.diettracker.data.cache.model.ServingEntity;
import de.karzek.diettracker.data.repository.datasource.interfaces.AllergenDataSource;
import de.karzek.diettracker.data.repository.datasource.interfaces.ServingDataSource;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class AllergenLocalDataSourceImpl implements AllergenDataSource {

    private final AllergenCache allergenCache;

    public AllergenLocalDataSourceImpl(AllergenCache allergenCache){
        this.allergenCache = allergenCache;
    }

    @Override
    public Observable<Boolean> putAllAllergens(List<AllergenEntity> allergenEntities) {
        return allergenCache.putAllAllergens(allergenEntities);
    }

    @Override
    public Observable<AllergenEntity> getAllergenById(Integer id) {
        return allergenCache.getAllergenById(id);
    }

    @Override
    public Observable<List<AllergenEntity>> getAllAllergens() {
        return allergenCache.getAllAllergens();
    }
}
