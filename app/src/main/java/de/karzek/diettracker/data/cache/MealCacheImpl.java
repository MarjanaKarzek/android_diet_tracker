package de.karzek.diettracker.data.cache;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.MealCache;
import de.karzek.diettracker.data.cache.model.MealEntity;
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
public class MealCacheImpl implements MealCache {

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
        return realm.where(UnitEntity.class).findAll() != null && realm.where(UnitEntity.class).findAll().size() > 0;
    }

    @Override
    public Observable<Boolean> putAllMeals(List<MealEntity> mealEntities) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(mealEntities);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }

    @Override
    public Observable<List<MealEntity>> getAllMeals() {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(MealEntity.class).sort("id").findAll()));
    }

    @Override
    public Observable<Long> getMealCount() {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.where(MealEntity.class).count());
    }
}