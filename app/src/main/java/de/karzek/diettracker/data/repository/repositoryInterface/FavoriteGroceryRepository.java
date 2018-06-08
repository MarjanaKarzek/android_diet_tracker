package de.karzek.diettracker.data.repository.repositoryInterface;

import java.util.List;

import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface FavoriteGroceryRepository {

    Observable<List<FavoriteGroceryDataModel>> getAllFavoritesByType(int type);

    Observable<Boolean> putFavoriteGrocery(FavoriteGroceryDataModel favoriteGroceryDataModel);

    Observable<Boolean> removeFavoriteGroceryByName(String name);

    Observable<Boolean> getFavoriteStateForGroceryById(int id);
}
