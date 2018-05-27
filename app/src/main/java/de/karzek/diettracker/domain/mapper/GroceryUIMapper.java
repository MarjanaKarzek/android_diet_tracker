package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.model.GroceryData;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GroceryUIMapper {
    public GroceryDisplayModel transform(GroceryData groceryData){
        GroceryDisplayModel groceryDisplayModel = null;
        if(groceryData != null){
            groceryDisplayModel = new GroceryDisplayModel(groceryData.getId(),
                    groceryData.getBarcode(),
                    groceryData.getName(),
                    groceryData.getCalories_per_1U(),
                    groceryData.getProteins_per_1U(),
                    groceryData.getCarbohydrates_per_1U(),
                    groceryData.getFats_per_1U(),
                    groceryData.getType(),
                    new AllergenUIMapper().transformAll(groceryData.getAllergens()),
                    new ServingUIMapper().transformAll(groceryData.getServings())
            );
        }
        return groceryDisplayModel;
    }

    public ArrayList<GroceryDisplayModel> transformAll(List<GroceryData> groceryDataList){
        ArrayList<GroceryDisplayModel> groceryDisplayModels = new ArrayList<>();
        for (GroceryData data: groceryDataList){
            groceryDisplayModels.add(transform(data));
        }
        return groceryDisplayModels;
    }
}
