package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.IngredientEntity;
import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.model.IngredientDataModel;
import de.karzek.diettracker.data.model.MealDataModel;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class IngredientDataMapper {

    public IngredientDataModel transform(IngredientEntity entity){
        IngredientDataModel dataModel = null;
        if(entity != null){
            dataModel = new IngredientDataModel(entity.getId(),
                    new GroceryDataMapper().transform(entity.getGrocery()),
                    entity.getAmount(),
                    new UnitDataMapper().transform(entity.getUnit()));
        }
        return dataModel;
    }

    public ArrayList<IngredientDataModel> transformAll(List<IngredientEntity> entities){
        ArrayList<IngredientDataModel> dataModels = new ArrayList<>();
        for (IngredientEntity entity: entities){
            dataModels.add(transform(entity));
        }
        return dataModels;
    }

    public IngredientEntity transformToEntity(IngredientDataModel dataModel){
        Realm realm = Realm.getDefaultInstance();
        IngredientEntity entity = null;
        if(dataModel != null){
            startWriteTransaction();
            if(realm.where(IngredientEntity.class).equalTo("id", dataModel.getId()).findFirst() == null) {
                realm.createObject(IngredientEntity.class, dataModel.getId());
            }

            startWriteTransaction();
            entity = realm.copyFromRealm(realm.where(IngredientEntity.class).equalTo("id", dataModel.getId()).findFirst());

            entity.setAmount(dataModel.getAmount());
            entity.setGrocery(new GroceryDataMapper().transformToEntity(dataModel.getGrocery()));
            entity.setUnit(new UnitDataMapper().transformToEntity(dataModel.getUnit()));
        }
        return entity;
    }

    public RealmList<IngredientEntity> transformAllToEntity(List<IngredientDataModel> dataModels) {
        RealmList<IngredientEntity> entities = new RealmList<>();
        startWriteTransaction();
        for (IngredientDataModel data: dataModels){
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
}
