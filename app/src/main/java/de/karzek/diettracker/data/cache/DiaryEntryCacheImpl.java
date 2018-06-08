package de.karzek.diettracker.data.cache;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.DiaryEntryCache;
import de.karzek.diettracker.data.cache.interfaces.UnitCache;
import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.MealDataModel;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class DiaryEntryCacheImpl implements DiaryEntryCache {

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
        return realm.where(DiaryEntryEntity.class).findAll() != null && realm.where(DiaryEntryEntity.class).findAll().size() > 0;
    }

    @Override
    public Observable<Boolean> putDiaryEntry(DiaryEntryEntity diaryEntryEntity) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(diaryEntryEntity);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }

    @Override
    public Observable<List<DiaryEntryEntity>> getAllDiaryEntriesMatching(String meal, String date) {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(DiaryEntryEntity.class).equalTo("meal.name", meal).equalTo("date", date).sort("id").findAll()));
    }

    @Override
    public Observable<Boolean> deleteDiaryEntry(int id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DiaryEntryEntity> result = realm.where(DiaryEntryEntity.class).equalTo("id",id).findAll();
                result.deleteAllFromRealm();
            }
        });
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> updateMealOfDiaryEntry(int id, MealEntity meal) {
        Realm realm = Realm.getDefaultInstance();
        DiaryEntryEntity entity = realm.copyFromRealm(realm.where(DiaryEntryEntity.class).equalTo("id",id).findFirst());

        if (entity == null)
            return Observable.just(false);

        entity.setMeal(meal);
        realm.copyToRealmOrUpdate(entity);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }
}