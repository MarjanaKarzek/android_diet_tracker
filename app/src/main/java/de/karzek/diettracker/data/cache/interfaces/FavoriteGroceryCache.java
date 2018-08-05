package de.karzek.diettracker.data.cache.interfaces;

import java.util.List;

import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface FavoriteGroceryCache {
    Observable<List<FavoriteGroceryEntity>> getAllFavoritesByType(int type);
    Observable<Boolean> putFavoriteGrocery(FavoriteGroceryEntity favoriteGroceryEntity);
    Observable<Boolean> removeFavoriteGroceryByName(String name);
    Observable<Boolean> getFavoriteStateForGroceryById(int id);
}
