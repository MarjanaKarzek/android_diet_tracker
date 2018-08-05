package de.karzek.diettracker.data.cache;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.ServingCache;
import de.karzek.diettracker.data.cache.interfaces.UnitCache;
import de.karzek.diettracker.data.cache.model.ServingEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import io.reactivex.Observable;
import io.realm.Realm;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class ServingCacheImpl implements ServingCache {

    @Override
    public Observable<Boolean> putAllServings(List<ServingEntity> servingEntities) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(servingEntities);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }
}