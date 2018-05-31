package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.repository.datasource.interfaces.FavoriteDataSource;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteLocalDataSourceImpl implements FavoriteDataSource {

    private final FavoriteGroceryCache favoriteGroceryCache;

    public FavoriteLocalDataSourceImpl(FavoriteGroceryCache favoriteGroceryCache){
        this.favoriteGroceryCache = favoriteGroceryCache;
    }

    @Override
    public Observable<List<FavoriteGroceryEntity>> getAllFavoritesByType(int type) {
        return favoriteGroceryCache.getAllFavoritesByType(type);
    }
}
