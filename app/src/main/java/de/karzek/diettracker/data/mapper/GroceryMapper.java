package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.database.model.GroceryEntity;
import de.karzek.diettracker.domain.model.GroceryData;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GroceryMapper {

    public GroceryData transform(GroceryEntity groceryEntity){
        GroceryData groceryData = null;
        if(groceryEntity != null){
            groceryData = new GroceryData(groceryEntity.getId(),
                    groceryEntity.getBarcode(),
                    groceryEntity.getName(),
                    groceryEntity.getCalories_per_1U(),
                    groceryEntity.getProteins_per_1U(),
                    groceryEntity.getCarbohydrates_per_1U(),
                    groceryEntity.getFats_per_1U(),
                    groceryEntity.getType(),
                    new AllergenMapper().transformAll(groceryEntity.getAllergens()),
                    new ServingMapper().transformAll(groceryEntity.getServings())
                    );
        }
        return groceryData;
    }

    public ArrayList<GroceryData> transformAll(List<GroceryEntity> groceryEntities){
        ArrayList<GroceryData> groceryDataList = new ArrayList<>();
        for (GroceryEntity entity: groceryEntities){
            groceryDataList.add(transform(entity));
        }
        return groceryDataList;
    }
}
