package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import io.realm.Realm;
import io.realm.RealmList;

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

    public FavoriteGroceryEntity transformToEntity(FavoriteGroceryDataModel dataModel){
        Realm realm = Realm.getDefaultInstance();
        FavoriteGroceryEntity entity = null;
        if(dataModel != null){
            startWriteTransaction();
            int id = dataModel.getId();
            if(realm.where(FavoriteGroceryEntity.class).equalTo("id", dataModel.getId()).findFirst() == null) {
                if(dataModel.getId() == -1)
                    id = getNextId();
                realm.createObject(FavoriteGroceryEntity.class, id);
            }

            entity = realm.copyFromRealm(realm.where(FavoriteGroceryEntity.class).equalTo("id", id).findFirst());
            entity.setGrocery(new GroceryDataMapper().transformToEntity(dataModel.getGrocery()));
        }
        return entity;
    }

    public RealmList<FavoriteGroceryEntity> transformAllToEntity(List<FavoriteGroceryDataModel> dataModels) {
        RealmList<FavoriteGroceryEntity> entities = new RealmList<>();
        startWriteTransaction();
        for (FavoriteGroceryDataModel data: dataModels){
            entities.add(transformToEntity(data));
        }
        return entities;
    }

    private void startWriteTransaction(){
        Realm realm = Realm.getDefaultInstance();
        if(!realm.isInTransaction()){
            realm.beginTransaction();
        }
    }

    private int getNextId(){
        Number currentIdNum = Realm.getDefaultInstance().where(FavoriteGroceryEntity.class).max("id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }
}
