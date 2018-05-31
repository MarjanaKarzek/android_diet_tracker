package de.karzek.diettracker.data.cache.interfaces;

import android.support.annotation.IntDef;

import java.util.List;

import de.karzek.diettracker.data.cache.model.FavoriteEntity;
import de.karzek.diettracker.data.cache.model.GroceryEntity;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface FavoriteCache {
    boolean isExpired();
    boolean isCached();
    Observable<List<FavoriteEntity>> getAllFavoritesByType(int type);
    void put(FavoriteEntity favoriteEntity);
}
