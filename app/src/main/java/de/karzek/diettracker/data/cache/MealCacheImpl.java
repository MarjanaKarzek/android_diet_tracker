package de.karzek.diettracker.data.cache;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.MealCache;
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
public class MealCacheImpl implements MealCache {

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

    @Override
    public Observable<MealEntity> getMealById(int id) {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(MealEntity.class).equalTo("id", id).findFirst()));
    }

    @Override
    public Observable<MealEntity> getMealByName(String meal) {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(MealEntity.class).equalTo("name", meal).findFirst()));
    }

    @Override
    public Observable<Boolean> putMeal(MealEntity meal) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(meal);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> deleteMealById(int id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<MealEntity> result = realm.where(MealEntity.class).equalTo("id", id).findAll();
                result.deleteAllFromRealm();
            }
        });
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> updateMeal(MealEntity meal) {
        Realm realm = Realm.getDefaultInstance();
        MealEntity entity = realm.copyFromRealm(realm.where(MealEntity.class).equalTo("id", meal.getId()).findFirst());

        if (entity == null)
            return Observable.just(false);

        entity.setName(meal.getName());
        entity.setStartTime(meal.getStartTime());
        entity.setEndTime(meal.getEndTime());

        realm.copyToRealmOrUpdate(entity);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }
}