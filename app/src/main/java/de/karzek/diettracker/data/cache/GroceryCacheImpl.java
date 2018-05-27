package de.karzek.diettracker.data.cache;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.GroceryCache;
import de.karzek.diettracker.data.database.model.GroceryEntity;
import io.reactivex.Observable;
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
    public boolean isExpired() {
        /*Realm realm = Realm.getDefaultInstance();
        if (realm.where(TownshipEntity.class).count() != 0) {
            Date currentTime = new Date(System.currentTimeMillis());
            SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
            Date lastUpdated = null;
            try {
                lastUpdated = ISO8601DATEFORMAT.parse(realm.where(TownshipEntity.class).findFirst().getLastUpdated());
                boolean isExpired = currentTime.getTime() - lastUpdated.getTime() > EXPIRATION_TIME;
                if(isExpired){
                    realm.beginTransaction();
                    realm.delete(TownshipEntity.class);
                    realm.commitTransaction();
                    realm.close();
                }
                return isExpired;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return false;*/

        return false;
    }

    @Override
    public boolean isCached() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(GroceryEntity.class).findAll() != null && realm.where(GroceryEntity.class).findAll().size() > 0;
    }

    @Override
    public Observable<List<GroceryEntity>> getAllGroceries() {
        List<GroceryEntity> groceries = Realm.getDefaultInstance().where(GroceryEntity.class).findAll();
        return Observable.just(groceries);
    }

    @Override
    public Observable<GroceryEntity> getGroceryByID(int id) {
        return Observable.just(Realm.getDefaultInstance().where(GroceryEntity.class).equalTo("id",id).findFirst());
    }

    @Override
    public Observable<GroceryEntity> getGroceryByBarcode(int barcode) {
        return Observable.just(Realm.getDefaultInstance().where(GroceryEntity.class).equalTo("barcode",barcode).findFirst());
    }

    @Override
    public Observable<GroceryEntity> getGroceryByName(String name) {
        return Observable.just(Realm.getDefaultInstance().where(GroceryEntity.class).equalTo("name",name).findFirst());
    }

    @Override
    public void put(GroceryEntity groceryEntity) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(groceryEntity);
        realm.commitTransaction();
        realm.close();
    }
}