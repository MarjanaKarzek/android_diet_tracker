package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.GroceryEntity;
import de.karzek.diettracker.data.model.GroceryDataModel;

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
                    new AllergenDataMapper().transformAll(groceryEntity.getAllergens()),
                    new ServingDataMapper().transformAll(groceryEntity.getServings())
                    );
        }
        return groceryDataModel;
    }

    public ArrayList<GroceryDataModel> transformAll(List<GroceryEntity> groceryEntities){
        ArrayList<GroceryDataModel> groceryDataModelList = new ArrayList<>();
        for (GroceryEntity entity: groceryEntities){
            groceryDataModelList.add(transform(entity));
        }
        return groceryDataModelList;
    }
}
