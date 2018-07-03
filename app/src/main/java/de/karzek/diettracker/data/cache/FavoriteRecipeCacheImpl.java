package de.karzek.diettracker.data.cache;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.cache.interfaces.FavoriteRecipeCache;
import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.cache.model.FavoriteRecipeEntity;
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
public class FavoriteRecipeCacheImpl implements FavoriteRecipeCache {

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
        return realm.where(FavoriteRecipeEntity.class).findAll() != null && realm.where(FavoriteRecipeEntity.class).findAll().size() > 0;
    }

    @Override
    public Observable<List<FavoriteRecipeEntity>> getAllFavoriteRecipes() {
        Realm realm = Realm.getDefaultInstance();
        List<FavoriteRecipeEntity> favorites = realm.copyFromRealm(realm.where(FavoriteRecipeEntity.class).findAll());
        return Observable.just(favorites);
    }

    @Override
    public Observable<Boolean> putFavoriteRecipe(FavoriteRecipeEntity entity) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(entity);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> removeFavoriteRecipeByTitle(String title) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<FavoriteRecipeEntity> result = realm.where(FavoriteRecipeEntity.class).equalTo("recipe.title", title).findAll();
                result.deleteAllFromRealm();
            }
        });
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> getFavoriteStateForRecipeById(int id) {
        if (Realm.getDefaultInstance().where(FavoriteRecipeEntity.class).equalTo("recipe.id", id).findFirst() == null)
            return Observable.just(false);
        else
            return Observable.just(true);
    }

    @Override
    public Observable<List<FavoriteRecipeEntity>> getAllFavoriteRecipesForMeal(String meal) {
        Realm realm = Realm.getDefaultInstance();
        List<FavoriteRecipeEntity> favorites = realm.copyFromRealm(realm.where(FavoriteRecipeEntity.class).contains("recipe.meals.name", meal).findAll());
        return Observable.just(favorites);
    }
}