package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class DiaryEntryDataMapper {

    public DiaryEntryDataModel transform(DiaryEntryEntity entity){
        DiaryEntryDataModel data = null;
        if(entity != null){
            data = new DiaryEntryDataModel(entity.getId(),
                    new MealDataMapper().transform(entity.getMeal()),
                    entity.getAmount(),
                    new UnitDataMapper().transform(entity.getUnit()),
                    new GroceryDataMapper().transform(entity.getGrocery()),
                    entity.getDate()
            );
        }
        return data;
    }

    public ArrayList<DiaryEntryDataModel> transformAll(List<DiaryEntryEntity> entities){
        ArrayList<DiaryEntryDataModel> dataModels = new ArrayList<>();
        for (DiaryEntryEntity entity: entities){
            dataModels.add(transform(entity));
        }
        return dataModels;
    }

    public DiaryEntryEntity transformToEntity(DiaryEntryDataModel dataModel){
        Realm realm = Realm.getDefaultInstance();
        DiaryEntryEntity entity = null;
        if(dataModel != null){
            startWriteTransaction();
            int id = dataModel.getId();
            if(realm.where(DiaryEntryEntity.class).equalTo("id", dataModel.getId()).findFirst() == null) {
                if(dataModel.getId() == -1)
                    id = getNextId();
                realm.createObject(DiaryEntryEntity.class, id);
            }

            entity = realm.copyFromRealm(realm.where(DiaryEntryEntity.class).equalTo("id", id).findFirst());

            entity.setMeal(new MealDataMapper().transformToEntity(dataModel.getMeal()));
            entity.setAmount(dataModel.getAmount());
            entity.setUnit(new UnitDataMapper().transformToEntity(dataModel.getUnit()));
            entity.setGrocery(new GroceryDataMapper().transformToEntity(dataModel.getGrocery()));
        }
        return entity;
    }

    public RealmList<DiaryEntryEntity> transformAllToEntity(List<DiaryEntryDataModel> dataModels) {
        RealmList<DiaryEntryEntity> entities = new RealmList<>();
        startWriteTransaction();
        for (DiaryEntryDataModel data: dataModels){
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
        Number currentIdNum = Realm.getDefaultInstance().where(DiaryEntryEntity.class).max("id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }
}
