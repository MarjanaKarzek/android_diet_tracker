package de.karzek.diettracker.data.repository.repositoryInterface;

import java.util.List;

import de.karzek.diettracker.data.model.GroceryDataModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface GroceryRepository {

    Observable<List<GroceryDataModel>> getAllGroceries();
    Observable<List<GroceryDataModel>> getAllGroceriesMatching(int type, String query);

    Observable<GroceryDataModel> getGroceryByID(int id);
    Observable<GroceryDataModel> getGroceryByBarcode(int barcode);
    Observable<GroceryDataModel> getGroceryByName(String name);

    Observable<Boolean> putAllGroceries(List<GroceryDataModel> groceryDataModels);
}
