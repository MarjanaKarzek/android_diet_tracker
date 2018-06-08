package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.GroceryCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.GroceryCache;
import de.karzek.diettracker.data.cache.model.GroceryEntity;
import de.karzek.diettracker.data.repository.datasource.local.GroceryLocalDataSourceImpl;
import de.karzek.diettracker.data.mapper.GroceryDataMapper;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.data.model.GroceryDataModel;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GroceryRepositoryImpl implements GroceryRepository {

    private final GroceryDataMapper mapper;
    private final GroceryCache groceryCache;

    public GroceryRepositoryImpl(GroceryCacheImpl groceryCache, GroceryDataMapper mapper) {
        this.groceryCache = groceryCache;
        this.mapper = mapper;
    }

    @Override
    public Observable<List<GroceryDataModel>> getAllGroceries() {
        if(!groceryCache.isExpired() && groceryCache.isCached()){
            return new GroceryLocalDataSourceImpl(groceryCache).getAllGroceries().map(new Function<List<GroceryEntity>, List<GroceryDataModel>>() {
                @Override
                public List<GroceryDataModel> apply(List<GroceryEntity> groceryEntities) {
                    return mapper.transformAll(groceryEntities);
                }
            });
        }else{
            return null;//new GroceryLocalDataSourceImpl(groceryCache);
        }
    }

    @Override
    public Observable<List<GroceryDataModel>> getAllGroceriesMatching(int type, String query) {
        return new GroceryLocalDataSourceImpl(groceryCache).getAllGroceriesMatching(type, query).map(new Function<List<GroceryEntity>, List<GroceryDataModel>>() {
            @Override
            public List<GroceryDataModel> apply(List<GroceryEntity> groceryEntities) {
                return mapper.transformAll(groceryEntities);
            }
        });
    }

    @Override
    public Observable<GroceryDataModel> getGroceryByID(int id) {
        if(!groceryCache.isExpired() && groceryCache.isCached()){
            return new GroceryLocalDataSourceImpl(groceryCache).getGroceryByID(id).map(new Function<GroceryEntity, GroceryDataModel>() {
                @Override
                public GroceryDataModel apply(GroceryEntity groceryEntity) {
                    return mapper.transform(groceryEntity);
                }
            });
        }else{
            return null;//new GroceryLocalDataSourceImpl(groceryCache);
        }
    }

    @Override
    public Observable<GroceryDataModel> getGroceryByBarcode(String barcode) {
        if(!groceryCache.isExpired() && groceryCache.isCached()){
            return new GroceryLocalDataSourceImpl(groceryCache).getGroceryByBarcode(barcode).map(new Function<GroceryEntity, GroceryDataModel>() {
                @Override
                public GroceryDataModel apply(GroceryEntity groceryEntity) {
                    return mapper.transform(groceryEntity);
                }
            });
        }else{
            return null;//new GroceryLocalDataSourceImpl(groceryCache);
        }
    }

    @Override
    public Observable<GroceryDataModel> getGroceryByName(String name) {
        if(!groceryCache.isExpired() && groceryCache.isCached()){
            return new GroceryLocalDataSourceImpl(groceryCache).getGroceryByName(name).map(new Function<GroceryEntity, GroceryDataModel>() {
                @Override
                public GroceryDataModel apply(GroceryEntity groceryEntity) {
                    return mapper.transform(groceryEntity);
                }
            });
        }else{
            return null;//new GroceryLocalDataSourceImpl(groceryCache);
        }
    }

    @Override
    public Observable<Boolean> putAllGroceries(List<GroceryDataModel> groceryDataModels) {
        return new GroceryLocalDataSourceImpl(groceryCache).putAllGroceries(mapper.transformAllToEntity(groceryDataModels));
    }
}