package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.database.model.GroceryEntity;
import de.karzek.diettracker.data.mapper.GroceryMapper;
import de.karzek.diettracker.data.provider.GroceryProvider;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.domain.model.GroceryData;
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

    private final GroceryProvider provider;
    private final GroceryMapper mapper;

    public GroceryRepositoryImpl(GroceryProvider provider, GroceryMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public Observable<List<GroceryData>> getAllGroceries() {
        return provider.create().getAllGroceries().map(new Function<List<GroceryEntity>, List<GroceryData>>() {
            @Override
            public List<GroceryData> apply(List<GroceryEntity> groceryEntities) {
                return mapper.transformAll(groceryEntities);
            }
        });
    }

    @Override
    public Observable<GroceryData> getGroceryByID(int id) {
        return provider.create().getGroceryByID(id).map(new Function<GroceryEntity, GroceryData>() {
            @Override
            public GroceryData apply(GroceryEntity groceryEntity) {
                return mapper.transform(groceryEntity);
            }
        });
    }

    @Override
    public Observable<GroceryData> getGroceryByBarcode(int barcode) {
        return provider.create().getGroceryByBarcode(barcode).map(new Function<GroceryEntity, GroceryData>() {
            @Override
            public GroceryData apply(GroceryEntity groceryEntity) {
                return mapper.transform(groceryEntity);
            }
        });
    }

    @Override
    public Observable<GroceryData> getGroceryByName(String name) {
        return provider.create().getGroceryByName(name).map(new Function<GroceryEntity, GroceryData>() {
            @Override
            public GroceryData apply(GroceryEntity groceryEntity) {
                return mapper.transform(groceryEntity);
            }
        });
    }
}