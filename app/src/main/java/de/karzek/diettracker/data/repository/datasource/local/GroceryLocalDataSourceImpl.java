package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.GroceryCache;
import de.karzek.diettracker.data.cache.model.GroceryEntity;
import de.karzek.diettracker.data.repository.datasource.interfaces.GroceryDataSource;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GroceryLocalDataSourceImpl implements GroceryDataSource {

    private final GroceryCache groceryCache;

    public GroceryLocalDataSourceImpl(GroceryCache groceryCache){
        this.groceryCache = groceryCache;
    }

    @Override
    public Observable<List<GroceryEntity>> getAllGroceries() {
        return groceryCache.getAllGroceries();
    }

    @Override
    public Observable<List<GroceryEntity>> getAllGroceriesMatching(int type, String query) {
        return groceryCache.getAllGroceriesMatching(type, query);
    }

    @Override
    public Observable<GroceryEntity> getGroceryByID(int id) {
        return groceryCache.getGroceryByID(id);
    }

    @Override
    public Observable<GroceryEntity> getGroceryByBarcode(String barcode) {
        return groceryCache.getGroceryByBarcode(barcode);
    }

    @Override
    public Observable<GroceryEntity> getGroceryByName(String name) {
        return groceryCache.getGroceryByName(name);
    }

    @Override
    public Observable<Boolean> putAllGroceries(List<GroceryEntity> groceryEntities) {
        return groceryCache.putAllGroceries(groceryEntities);
    }

    @Override
    public Observable<List<GroceryEntity>> getAllGroceriesExactlyMatching(String query) {
        return groceryCache.getAllGroceriesExactlyMatching(query);
    }
}
