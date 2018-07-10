package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.presentation.util.Constants;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class MealDataMapper {

    public MealDataModel transform(MealEntity entity){
        MealDataModel dataModel = null;
        if(entity != null){
            dataModel = new MealDataModel(entity.getId(),
                    entity.getName(),
                    entity.getStartTime(),
                    entity.getEndTime()
            );
        }
        return dataModel;
    }

    public ArrayList<MealDataModel> transformAll(List<MealEntity> entities){
        ArrayList<MealDataModel> dataModels = new ArrayList<>();
        for (MealEntity entity: entities){
            dataModels.add(transform(entity));
        }
        return dataModels;
    }

    public MealEntity transformToEntity(MealDataModel dataModel){
        Realm realm = Realm.getDefaultInstance();
        MealEntity entity = null;
        if(dataModel != null){
            startWriteTransaction();
            int id = dataModel.getId();

            if(realm.where(MealEntity.class).equalTo("id", dataModel.getId()).findFirst() == null) {
                if(dataModel.getId() == Constants.INVALID_ENTITY_ID)
                    id = getNextId();
                realm.createObject(MealEntity.class, id);
            }

            entity = realm.copyFromRealm(realm.where(MealEntity.class).equalTo("id", id).findFirst());

            entity.setName(dataModel.getName());
            entity.setStartTime(dataModel.getStartTime());
            entity.setEndTime(dataModel.getEndTime());
        }
        return entity;
    }

    public RealmList<MealEntity> transformAllToEntity(List<MealDataModel> dataModels) {
        RealmList<MealEntity> entities = new RealmList<>();
        startWriteTransaction();
        for (MealDataModel data: dataModels){
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
        Number currentIdNum = Realm.getDefaultInstance().where(MealEntity.class).max("id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }
}
