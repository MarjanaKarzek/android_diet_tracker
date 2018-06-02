package de.karzek.diettracker.data.repository.datasource.interfaces;

import java.util.List;

import de.karzek.diettracker.data.cache.model.GroceryEntity;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface GroceryDataSource {

    Observable<List<GroceryEntity>> getAllGroceries();
    Observable<List<GroceryEntity>> getAllGroceriesMatching(int type, String query);

    Observable<GroceryEntity> getGroceryByID(int id);
    Observable<GroceryEntity> getGroceryByBarcode(int barcode);
    Observable<GroceryEntity> getGroceryByName(String name);

    Observable<Boolean> putAllGroceries(List<GroceryEntity> groceryEntities);

}
