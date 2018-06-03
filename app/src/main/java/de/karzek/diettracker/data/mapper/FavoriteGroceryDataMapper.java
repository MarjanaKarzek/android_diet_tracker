package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import io.realm.Realm;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class FavoriteGroceryDataMapper {

    public FavoriteGroceryDataModel transform(FavoriteGroceryEntity favoriteGroceryEntity){
        FavoriteGroceryDataModel favoriteGroceryDataModel = null;
        if(favoriteGroceryEntity != null){
            favoriteGroceryDataModel = new FavoriteGroceryDataModel(favoriteGroceryEntity.getId(),
                    new GroceryDataMapper().transform(favoriteGroceryEntity.getGrocery())
            );
        }
        return favoriteGroceryDataModel;
    }

    public ArrayList<FavoriteGroceryDataModel> transformAll(List<FavoriteGroceryEntity> favoriteEntities){
        ArrayList<FavoriteGroceryDataModel> favoriteGroceryDataModels = new ArrayList<>();
        for (FavoriteGroceryEntity entity: favoriteEntities){
            favoriteGroceryDataModels.add(transform(entity));
        }
        return favoriteGroceryDataModels;
    }

    private void startWriteTransaction(){
        Realm realm = Realm.getDefaultInstance();
        if(!realm.isInTransaction()){
            realm.beginTransaction();
        }
    }

}
