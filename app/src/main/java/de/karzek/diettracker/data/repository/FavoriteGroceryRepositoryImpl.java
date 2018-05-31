package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.FavoriteGroceryGroceryCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.mapper.FavoriteGroceryDataMapper;
import de.karzek.diettracker.data.model.FavoriteDataModel;
import de.karzek.diettracker.data.repository.datasource.local.FavoriteLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final FavoriteGroceryDataMapper mapper;
    private final FavoriteGroceryCache favoriteGroceryCache;

    public FavoriteRepositoryImpl(FavoriteGroceryGroceryCacheImpl favoriteCache, FavoriteGroceryDataMapper mapper) {
        this.favoriteGroceryCache = favoriteCache;
        this.mapper = mapper;
    }

    @Override
    public Observable<List<FavoriteDataModel>> getAllFavoritesByType(int type) {
        if(!favoriteGroceryCache.isExpired() && favoriteGroceryCache.isCached()){
            return new FavoriteLocalDataSourceImpl(favoriteGroceryCache).getAllFavoritesByType(type).map(new Function<List<FavoriteGroceryEntity>, List<FavoriteDataModel>>() {
                @Override
                public List<FavoriteDataModel> apply(List<FavoriteGroceryEntity> favoriteEntities) {
                    return mapper.transformAll(favoriteEntities);
                }
            });
        }else{
            return null;//new GroceryLocalDataSourceImpl(groceryCache);
        }
    }
}