package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.MealDataModel;
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
public class MealDataMapper {

    public MealDataModel transform(MealEntity mealEntity){
        MealDataModel mealDataModel = null;
        if(mealEntity != null){
            mealDataModel = new MealDataModel(mealEntity.getId(),
                    mealEntity.getName(),
                    mealEntity.getStartTime(),
                    mealEntity.getEndTime()
            );
        }
        return mealDataModel;
    }

    public ArrayList<MealDataModel> transformAll(List<MealEntity> mealEntities){
        ArrayList<MealDataModel> mealDataModels = new ArrayList<>();
        for (MealEntity entity: mealEntities){
            mealDataModels.add(transform(entity));
        }
        return mealDataModels;
    }

    public MealEntity transformToEntity(MealDataModel mealDataModel){
        Realm realm = Realm.getDefaultInstance();
        MealEntity mealEntity = null;
        if(mealDataModel != null){
            startWriteTransaction();
            if(realm.where(MealEntity.class).equalTo("id", mealDataModel.getId()).findFirst() == null) {
                realm.createObject(MealEntity.class, mealDataModel.getId());
            }

            startWriteTransaction();
            mealEntity = realm.copyFromRealm(realm.where(MealEntity.class).equalTo("id", mealDataModel.getId()).findFirst());

            mealEntity.setName(mealDataModel.getName());
            mealEntity.setStartTime(mealDataModel.getStartTime());
            mealEntity.setEndTime(mealDataModel.getEndTime());
        }
        return mealEntity;
    }

    public RealmList<MealEntity> transformAllToEntity(List<MealDataModel> mealDataModels) {
        RealmList<MealEntity> mealEntities = new RealmList<>();
        startWriteTransaction();
        for (MealDataModel data: mealDataModels){
            mealEntities.add(transformToEntity(data));
        }
        return mealEntities;
    }

    private void startWriteTransaction(){
        Realm realm = Realm.getDefaultInstance();
        if(!realm.isInTransaction()){
            realm.beginTransaction();
        }
    }
}
