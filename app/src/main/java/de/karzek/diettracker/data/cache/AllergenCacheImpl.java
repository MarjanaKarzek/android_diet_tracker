package de.karzek.diettracker.data.cache;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.AllergenCache;
import de.karzek.diettracker.data.cache.interfaces.ServingCache;
import de.karzek.diettracker.data.cache.model.AllergenEntity;
import de.karzek.diettracker.data.cache.model.ServingEntity;
import io.reactivex.Observable;
import io.realm.Case;
import io.realm.Realm;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class AllergenCacheImpl implements AllergenCache {

    @Override
    public Observable<Boolean> putAllAllergens(List<AllergenEntity> allergenEntities) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(allergenEntities);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }

    @Override
    public Observable<AllergenEntity> getAllergenById(Integer id) {
        Realm realm = Realm.getDefaultInstance();
        AllergenEntity entity = realm.copyFromRealm(realm.where(AllergenEntity.class).equalTo("id", id).findFirst());
        return Observable.just(entity);
    }

    @Override
    public Observable<List<AllergenEntity>> getAllAllergens() {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(AllergenEntity.class).sort("id").findAll()));
    }
}