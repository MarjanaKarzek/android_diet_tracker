package de.karzek.diettracker.data.cache;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.GroceryCache;
import de.karzek.diettracker.data.cache.model.GroceryEntity;
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
public class GroceryCacheImpl implements GroceryCache {

    @Override
    public Observable<List<GroceryEntity>> getAllGroceries() {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(GroceryEntity.class).notEqualTo("id", -1).notEqualTo("id",0).findAll()));
    }

    @Override
    public Observable<List<GroceryEntity>> getAllGroceriesMatching(int type, String query) {
        Realm realm = Realm.getDefaultInstance();
        if (type != GroceryEntity.GroceryEntityType.TYPE_COMBINED)
            return Observable.just(realm.copyFromRealm(realm.where(GroceryEntity.class).equalTo("type", type).notEqualTo("id", -1).notEqualTo("id",0).contains("name",query, Case.INSENSITIVE).sort("name").findAll()));
        else
            return Observable.just(realm.copyFromRealm(realm.where(GroceryEntity.class).notEqualTo("id", -1).notEqualTo("id",0).contains("name",query, Case.INSENSITIVE).sort("name").findAll()));

    }

    @Override
    public Observable<List<GroceryEntity>> getAllGroceriesExactlyMatching(String query) {
        Realm realm = Realm.getDefaultInstance();
            return Observable.just(realm.copyFromRealm(realm.where(GroceryEntity.class).notEqualTo("id", -1).notEqualTo("id",0).equalTo("name", query, Case.INSENSITIVE).sort("name").findAll()));
    }

    @Override
    public Observable<GroceryEntity> getGroceryByID(int id) {
        Realm realm = Realm.getDefaultInstance();
        GroceryEntity entity = realm.copyFromRealm(realm.where(GroceryEntity.class).equalTo("id", id).findFirst());
        return Observable.just(entity);
    }

    @Override
    public Observable<GroceryEntity> getGroceryByBarcode(String barcode) {
        Realm realm = Realm.getDefaultInstance();
        GroceryEntity result = realm.where(GroceryEntity.class).equalTo("barcode", barcode).findFirst();
        if (result == null) {
            return Observable.just(realm.copyFromRealm(realm.where(GroceryEntity.class).equalTo("id", -1).findFirst()));
        }
        else
            return Observable.just(realm.copyFromRealm(result));
    }

    @Override
    public Observable<GroceryEntity> getGroceryByName(String name) {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(GroceryEntity.class).equalTo("name", name).findFirst()));
    }

    @Override
    public void put(GroceryEntity groceryEntity) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(groceryEntity);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public Observable<Boolean> putAllGroceries(List<GroceryEntity> groceryEntities) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(groceryEntities);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }
}