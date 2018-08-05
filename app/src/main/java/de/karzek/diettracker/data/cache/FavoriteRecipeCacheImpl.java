package de.karzek.diettracker.data.cache;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.FavoriteRecipeCache;
import de.karzek.diettracker.data.cache.model.FavoriteRecipeEntity;
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
public class FavoriteRecipeCacheImpl implements FavoriteRecipeCache {

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