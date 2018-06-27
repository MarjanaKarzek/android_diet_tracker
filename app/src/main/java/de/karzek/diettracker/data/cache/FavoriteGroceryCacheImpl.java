package de.karzek.diettracker.data.cache;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.cache.model.GroceryEntity;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_COMBINED;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteGroceryCacheImpl implements FavoriteGroceryCache {

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
        return realm.where(FavoriteGroceryEntity.class).findAll() != null && realm.where(FavoriteGroceryEntity.class).findAll().size() > 0;
    }

    @Override
    public Observable<List<FavoriteGroceryEntity>> getAllFavoritesByType(int type) {
        Realm realm = Realm.getDefaultInstance();

        if (type != TYPE_COMBINED) {
            List<FavoriteGroceryEntity> favorites = realm.copyFromRealm(realm.where(FavoriteGroceryEntity.class).equalTo("grocery.type", type).findAll());
            return Observable.just(favorites);
        } else {
            List<FavoriteGroceryEntity> favorites = realm.copyFromRealm(realm.where(FavoriteGroceryEntity.class).findAll());
            return Observable.just(favorites);
        }
    }

    @Override
    public Observable<Boolean> putFavoriteGrocery(FavoriteGroceryEntity favoriteGroceryEntity) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(favoriteGroceryEntity);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> removeFavoriteGroceryByName(String name) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<FavoriteGroceryEntity> result = realm.where(FavoriteGroceryEntity.class).equalTo("grocery.name", name).findAll();
                result.deleteAllFromRealm();
            }
        });
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> getFavoriteStateForGroceryById(int id) {
        if (Realm.getDefaultInstance().where(FavoriteGroceryEntity.class).equalTo("grocery.id", id).findFirst() == null)
            return Observable.just(false);
        else
            return Observable.just(true);
    }
}