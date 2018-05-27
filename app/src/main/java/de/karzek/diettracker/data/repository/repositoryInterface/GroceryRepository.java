package de.karzek.diettracker.data.repository.repositoryInterface;

import java.util.List;

import de.karzek.diettracker.domain.model.GroceryData;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface GroceryRepository {

    Observable<List<GroceryData>> getAllGroceries();
    Observable<GroceryData> getGroceryByID(int id);
    Observable<GroceryData> getGroceryByBarcode(int barcode);
    Observable<GroceryData> getGroceryByName(String name);

}
