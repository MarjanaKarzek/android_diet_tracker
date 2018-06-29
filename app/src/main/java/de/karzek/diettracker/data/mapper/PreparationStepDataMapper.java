package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.cache.model.IngredientEntity;
import de.karzek.diettracker.data.cache.model.PreparationStepEntity;
import de.karzek.diettracker.data.model.IngredientDataModel;
import de.karzek.diettracker.data.model.PreparationStepDataModel;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class PreparationStepDataMapper {

    public PreparationStepDataModel transform(PreparationStepEntity entity){
        PreparationStepDataModel dataModel = null;
        if(entity != null){
            dataModel = new PreparationStepDataModel(entity.getId(),
                    entity.getStepNo(),
                    entity.getDescription());
        }
        return dataModel;
    }

    public ArrayList<PreparationStepDataModel> transformAll(List<PreparationStepEntity> entities){
        ArrayList<PreparationStepDataModel> dataModels = new ArrayList<>();
        for (PreparationStepEntity entity: entities){
            dataModels.add(transform(entity));
        }
        return dataModels;
    }

    public PreparationStepEntity transformToEntity(PreparationStepDataModel dataModel){
        Realm realm = Realm.getDefaultInstance();
        PreparationStepEntity entity = null;
        if(dataModel != null){
            startWriteTransaction();
            int id = dataModel.getId();
            if(realm.where(PreparationStepEntity.class).equalTo("id", dataModel.getId()).findFirst() == null) {
                if(dataModel.getId() == -1)
                    id = getNextId();
                realm.createObject(PreparationStepEntity.class, id);
            }
            entity = realm.copyFromRealm(realm.where(PreparationStepEntity.class).equalTo("id", id).findFirst());

            entity.setStepNo(dataModel.getStepNo());
            entity.setDescription(dataModel.getDescription());
        }
        return entity;
    }

    public RealmList<PreparationStepEntity> transformAllToEntity(List<PreparationStepDataModel> dataModels) {
        RealmList<PreparationStepEntity> entities = new RealmList<>();
        startWriteTransaction();
        for (PreparationStepDataModel data: dataModels){
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
        Number currentIdNum = Realm.getDefaultInstance().where(IngredientEntity.class).max("id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }
}
