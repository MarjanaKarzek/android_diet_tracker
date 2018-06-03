package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.ServingEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.ServingDataModel;
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
public class UnitDataMapper {

    public UnitDataModel transform(UnitEntity unitEntity){
        UnitDataModel unitDataModel = null;
        if(unitEntity != null){
            unitDataModel = new UnitDataModel(unitEntity.getId(),
                    unitEntity.getName(),
                    unitEntity.getMultiplier(),
                    unitEntity.getType()
            );
        }
        return unitDataModel;
    }

    public ArrayList<UnitDataModel> transformAll(List<UnitEntity> unitEntities){
        ArrayList<UnitDataModel> unitDataModels = new ArrayList<>();
        for (UnitEntity entity: unitEntities){
            unitDataModels.add(transform(entity));
        }
        return unitDataModels;
    }

    public UnitEntity transformToEntity(UnitDataModel unitDataModel){
        Realm realm = Realm.getDefaultInstance();
        UnitEntity unitEntity = null;
        if(unitDataModel != null){
            startWriteTransaction();
            if(realm.where(UnitEntity.class).equalTo("id", unitDataModel.getId()).findFirst() == null)
                realm.createObject(UnitEntity.class, unitDataModel.getId());

            unitEntity = realm.copyFromRealm(realm.where(UnitEntity.class).equalTo("id", unitDataModel.getId()).findFirst());

            unitEntity.setName(unitDataModel.getName());
            unitEntity.setMultiplier(unitDataModel.getMultiplier());
            unitEntity.setType(unitDataModel.getType());
        }
        return unitEntity;
    }

    public RealmList<UnitEntity> transformAllToEntity(List<UnitDataModel> unitDataModels) {
        RealmList<UnitEntity> unitEntities = new RealmList<>();
        startWriteTransaction();
        for (UnitDataModel data: unitDataModels){
            unitEntities.add(transformToEntity(data));
        }
        return unitEntities;
    }

    private void startWriteTransaction(){
        Realm realm = Realm.getDefaultInstance();
        if(!realm.isInTransaction()){
            realm.beginTransaction();
        }
    }
}
