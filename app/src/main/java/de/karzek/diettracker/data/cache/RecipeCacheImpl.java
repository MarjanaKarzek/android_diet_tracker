package de.karzek.diettracker.data.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.RecipeCache;
import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.RecipeEntity;
import io.reactivex.Observable;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class RecipeCacheImpl implements RecipeCache {

    @Override
    public Observable<Boolean> putRecipe(RecipeEntity recipeEntity) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(recipeEntity);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }

    @Override
    public Observable<List<RecipeEntity>> getAllRecipes(ArrayList<String> filterOptions, String sortOption, boolean asc) {
        Realm realm = Realm.getDefaultInstance();
        List<RecipeEntity> results;
        if(asc)
            results = realm.copyFromRealm(realm.where(RecipeEntity.class).sort(sortOption, Sort.ASCENDING).findAll());
        else
            results = realm.copyFromRealm(realm.where(RecipeEntity.class).sort(sortOption, Sort.DESCENDING).findAll());

        Iterator iterator = results.iterator();
        List<RecipeEntity> filteredResults = new ArrayList<>();

        if (filterOptions.size() > 0)
            while (iterator.hasNext()) {
                RecipeEntity entity = (RecipeEntity) iterator.next();

                for (MealEntity meal : entity.getMeals()) {
                    if (filterOptions.contains(meal.getName())) {
                        filteredResults.add(entity);
                        break;
                    }
                }
            }
        else
            return Observable.just(results);

        return Observable.just(filteredResults);
    }

    @Override
    public Observable<RecipeEntity> getRecipeById(int id) {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(RecipeEntity.class).equalTo("id", id).findFirst()));
    }

    @Override
    public Observable<Boolean> updateRecipe(RecipeEntity recipeEntity) {
        Realm realm = Realm.getDefaultInstance();
        realm.copyToRealmOrUpdate(recipeEntity);
        realm.commitTransaction();
        realm.close();
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> deleteRecipe(int id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RecipeEntity> result = realm.where(RecipeEntity.class).equalTo("id", id).findAll();
                result.deleteAllFromRealm();
            }
        });
        return Observable.just(true);
    }

    @Override
    public Observable<List<RecipeEntity>> getAllRecipesMatching(String query, ArrayList<String> filterOptions, String sortOption, boolean asc) {
        Realm realm = Realm.getDefaultInstance();

        List<RecipeEntity> results;
        if(asc)
            results = realm.copyFromRealm(realm.where(RecipeEntity.class).contains("title", query, Case.INSENSITIVE).sort(sortOption, Sort.ASCENDING).findAll());
        else
            results = realm.copyFromRealm(realm.where(RecipeEntity.class).contains("title", query, Case.INSENSITIVE).sort(sortOption, Sort.DESCENDING).findAll());

        Iterator iterator = results.iterator();
        List<RecipeEntity> filteredResults = new ArrayList<>();

        if (filterOptions.size() > 0)
            while (iterator.hasNext()) {
                RecipeEntity entity = (RecipeEntity) iterator.next();

                for (MealEntity meal : entity.getMeals()) {
                    if (filterOptions.contains(meal.getName())) {
                        filteredResults.add(entity);
                        break;
                    }
                }
            }
        else
            return Observable.just(results);

        return Observable.just(filteredResults);
    }

    @Override
    public Observable<List<RecipeEntity>> getAllRecipesMatching(String query) {
        Realm realm = Realm.getDefaultInstance();
        return Observable.just(realm.copyFromRealm(realm.where(RecipeEntity.class).contains("title", query, Case.INSENSITIVE).sort("title").findAll()));
    }

}