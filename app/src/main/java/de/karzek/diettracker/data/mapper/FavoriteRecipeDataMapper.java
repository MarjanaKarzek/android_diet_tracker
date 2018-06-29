package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.cache.model.FavoriteRecipeEntity;
import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import de.karzek.diettracker.data.model.FavoriteRecipeDataModel;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class FavoriteRecipeDataMapper {

    public FavoriteRecipeDataModel transform(FavoriteRecipeEntity entity){
        FavoriteRecipeDataModel dataModel = null;
        if(entity != null){
            dataModel = new FavoriteRecipeDataModel(entity.getId(),
                    new RecipeDataMapper().transform(entity.getRecipe())
            );
        }
        return dataModel;
    }

    public ArrayList<FavoriteRecipeDataModel> transformAll(List<FavoriteRecipeEntity> entities){
        ArrayList<FavoriteRecipeDataModel> dataModels = new ArrayList<>();
        for (FavoriteRecipeEntity entity: entities){
            dataModels.add(transform(entity));
        }
        return dataModels;
    }

    public FavoriteRecipeEntity transformToEntity(FavoriteRecipeDataModel dataModel){
        Realm realm = Realm.getDefaultInstance();
        FavoriteRecipeEntity entity = null;
        if(dataModel != null){
            startWriteTransaction();
            int id = dataModel.getId();
            if(realm.where(FavoriteRecipeEntity.class).equalTo("id", dataModel.getId()).findFirst() == null) {
                if(dataModel.getId() == -1)
                    id = getNextId();
                realm.createObject(FavoriteRecipeEntity.class, id);
            }

            entity = realm.copyFromRealm(realm.where(FavoriteRecipeEntity.class).equalTo("id", id).findFirst());
            entity.setRecipe(new RecipeDataMapper().transformToEntity(dataModel.getRecipe()));
        }
        return entity;
    }

    public RealmList<FavoriteRecipeEntity> transformAllToEntity(List<FavoriteRecipeDataModel> dataModels) {
        RealmList<FavoriteRecipeEntity> entities = new RealmList<>();
        startWriteTransaction();
        for (FavoriteRecipeDataModel data: dataModels){
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
        Number currentIdNum = Realm.getDefaultInstance().where(FavoriteRecipeEntity.class).max("id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }
}
