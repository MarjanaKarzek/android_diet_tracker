package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.FavoriteGroceryCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.mapper.FavoriteGroceryDataMapper;
import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import de.karzek.diettracker.data.repository.datasource.local.FavoriteGroceryLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteGroceryRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteGroceryRepositoryImpl implements FavoriteGroceryRepository {

    private final FavoriteGroceryDataMapper mapper;
    private final FavoriteGroceryCache favoriteGroceryCache;

    public FavoriteGroceryRepositoryImpl(FavoriteGroceryCacheImpl favoriteCache, FavoriteGroceryDataMapper mapper) {
        this.favoriteGroceryCache = favoriteCache;
        this.mapper = mapper;
    }

    @Override
    public Observable<List<FavoriteGroceryDataModel>> getAllFavoritesByType(int type) {
        return new FavoriteGroceryLocalDataSourceImpl(favoriteGroceryCache).getAllFavoritesByType(type).map(new Function<List<FavoriteGroceryEntity>, List<FavoriteGroceryDataModel>>() {
            @Override
            public List<FavoriteGroceryDataModel> apply(List<FavoriteGroceryEntity> favoriteEntities) {
                return mapper.transformAll(favoriteEntities);
            }
        });
    }

    @Override
    public Observable<Boolean> putFavoriteGrocery(FavoriteGroceryDataModel favoriteGroceryDataModel) {
        return new FavoriteGroceryLocalDataSourceImpl(favoriteGroceryCache).putFavoriteGrocery(mapper.transformToEntity(favoriteGroceryDataModel));
    }

    @Override
    public Observable<Boolean> removeFavoriteGroceryByName(String name) {
        return new FavoriteGroceryLocalDataSourceImpl(favoriteGroceryCache).removeFavoriteGroceryByName(name);
    }

    @Override
    public Observable<Boolean> getFavoriteStateForGroceryById(int id) {
        return new FavoriteGroceryLocalDataSourceImpl(favoriteGroceryCache).getFavoriteStateForGroceryById(id);
    }
}