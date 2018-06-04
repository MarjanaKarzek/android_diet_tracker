package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.karzek.diettracker.data.cache.model.AllergenEntity;
import de.karzek.diettracker.data.cache.model.GroceryEntity;
import de.karzek.diettracker.data.cache.model.ServingEntity;
import de.karzek.diettracker.data.model.GroceryDataModel;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GroceryDataMapper {

    public GroceryDataModel transform(GroceryEntity groceryEntity){
        GroceryDataModel groceryDataModel = null;
        if(groceryEntity != null){
            groceryDataModel = new GroceryDataModel(groceryEntity.getId(),
                    groceryEntity.getBarcode(),
                    groceryEntity.getName(),
                    groceryEntity.getCalories_per_1U(),
                    groceryEntity.getProteins_per_1U(),
                    groceryEntity.getCarbohydrates_per_1U(),
                    groceryEntity.getFats_per_1U(),
                    groceryEntity.getType(),
                    groceryEntity.getUnit_type(),
                    new AllergenDataMapper().transformAll(groceryEntity.getAllergens()),
                    new ServingDataMapper().transformAll(groceryEntity.getServings())
                    );
        }
        return groceryDataModel;
    }

    public ArrayList<GroceryDataModel> transformAll(List<GroceryEntity> groceryEntities){
        ArrayList<GroceryDataModel> groceryDataModelList = new ArrayList<>();
        if(groceryEntities != null) {
            for (GroceryEntity entity : groceryEntities) {
                groceryDataModelList.add(transform(entity));
            }
        }
        return groceryDataModelList;
    }

    public GroceryEntity transformToEntity(GroceryDataModel groceryDataModel) {
        Realm realm = Realm.getDefaultInstance();
        GroceryEntity groceryEntity = null;
        if(groceryDataModel != null){
            startWriteTransaction();
            if(realm.where(GroceryEntity.class).equalTo("id",groceryDataModel.getId()).findFirst() == null)
                realm.createObject(GroceryEntity.class, groceryDataModel.getId());
            groceryEntity = realm.copyFromRealm(realm.where(GroceryEntity.class).equalTo("id",groceryDataModel.getId()).findFirst());

            groceryEntity.setBarcode(groceryDataModel.getBarcode());
            groceryEntity.setName(groceryDataModel.getName());
            groceryEntity.setCalories_per_1U(groceryDataModel.getCalories_per_1U());
            groceryEntity.setProteins_per_1U(groceryDataModel.getProteins_per_1U());
            groceryEntity.setCarbohydrates_per_1U(groceryDataModel.getCarbohydrates_per_1U());
            groceryEntity.setFats_per_1U(groceryDataModel.getFats_per_1U());
            groceryEntity.setType(groceryDataModel.getType());
            groceryEntity.setUnit_type(groceryDataModel.getUnit_type());
            groceryEntity.setAllergens(new AllergenDataMapper().transformAllToEntity(groceryDataModel.getAllergens()));
            groceryEntity.setServings(new ServingDataMapper().transformAllToEntity(groceryDataModel.getServings()));
        }
        return groceryEntity;
    }

    public List<GroceryEntity> transformAllToEntity(List<GroceryDataModel> groceryDataModels) {
        ArrayList<GroceryEntity> groceryEntities = new ArrayList<>();
        startWriteTransaction();
        for (GroceryDataModel data: groceryDataModels){
            groceryEntities.add(transformToEntity(data));
        }
        return groceryEntities;
    }

    private void startWriteTransaction(){
        Realm realm = Realm.getDefaultInstance();
        if(!realm.isInTransaction()){
            realm.beginTransaction();
        }
    }
}
