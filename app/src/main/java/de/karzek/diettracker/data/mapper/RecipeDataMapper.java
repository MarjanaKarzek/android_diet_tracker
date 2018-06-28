package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.GroceryEntity;
import de.karzek.diettracker.data.cache.model.RecipeEntity;
import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.data.model.RecipeDataModel;
import io.realm.Realm;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class RecipeDataMapper {

    public RecipeDataModel transform(RecipeEntity entity){
        RecipeDataModel dataModel = null;
        if(entity != null){
            dataModel = new RecipeDataModel(entity.getId(),
                    entity.getTitle(),
                    entity.getPhoto(),
                    entity.getPortions(),
                    new IngredientDataMapper().transformAll(entity.getIngredients()),
                    new PreparationStepDataMapper().transformAll(entity.getSteps()),
                    new MealDataMapper().transformAll(entity.getMeals()));
        }
        return dataModel;
    }

    public List<RecipeDataModel> transformAll(List<RecipeEntity> entities){
        ArrayList<RecipeDataModel> dataModels = new ArrayList<>();
        if(entities != null) {
            for (RecipeEntity entity : entities) {
                dataModels.add(transform(entity));
            }
        }
        return dataModels;
    }

    public RecipeEntity transformToEntity(RecipeDataModel dataModel) {
        Realm realm = Realm.getDefaultInstance();
        RecipeEntity entity = null;
        if(dataModel != null){
            startWriteTransaction();
            if(realm.where(RecipeEntity.class).equalTo("id",dataModel.getId()).findFirst() == null)
                realm.createObject(GroceryEntity.class, dataModel.getId());
            entity = realm.copyFromRealm(realm.where(RecipeEntity.class).equalTo("id",dataModel.getId()).findFirst());

            entity.setTitle(dataModel.getTitle());
            entity.setPhoto(dataModel.getPhoto());
            entity.setPortions(dataModel.getPortions());
            entity.setIngredients(new IngredientDataMapper().transformAllToEntity(dataModel.getIngredients()));
            entity.setSteps(new PreparationStepDataMapper().transformAllToEntity(dataModel.getSteps()));
            entity.setMeals(new MealDataMapper().transformAllToEntity(dataModel.getMeals()));
        }
        return entity;
    }

    public List<RecipeEntity> transformAllToEntity(List<RecipeDataModel> dataModels) {
        ArrayList<RecipeEntity> entities = new ArrayList<>();
        startWriteTransaction();
        for (RecipeDataModel data: dataModels){
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
