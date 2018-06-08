package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.repository.datasource.interfaces.FavoriteGroceryDataSource;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteGroceryLocalDataSourceImpl implements FavoriteGroceryDataSource {

    private final FavoriteGroceryCache favoriteGroceryCache;

    public FavoriteGroceryLocalDataSourceImpl(FavoriteGroceryCache favoriteGroceryCache){
        this.favoriteGroceryCache = favoriteGroceryCache;
    }

    @Override
    public Observable<List<FavoriteGroceryEntity>> getAllFavoritesByType(int type) {
        return favoriteGroceryCache.getAllFavoritesByType(type);
    }

    @Override
    public Observable<Boolean> putFavoriteGrocery(FavoriteGroceryEntity favoriteGroceryEntity) {
        return favoriteGroceryCache.putFavoriteGrocery(favoriteGroceryEntity);
    }

    @Override
    public Observable<Boolean> removeFavoriteGroceryByName(String name) {
        return favoriteGroceryCache.removeFavoriteGroceryByName(name);
    }

    @Override
    public Observable<Boolean> getFavoriteStateForGroceryById(int id) {
        return favoriteGroceryCache.getFavoriteStateForGroceryById(id);
    }
}
