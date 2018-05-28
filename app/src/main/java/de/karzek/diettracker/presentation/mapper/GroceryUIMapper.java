package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GroceryUIMapper {
    public GroceryDisplayModel transform(GroceryDataModel groceryDataModel){
        GroceryDisplayModel groceryDisplayModel = null;
        if(groceryDataModel != null){
            groceryDisplayModel = new GroceryDisplayModel(groceryDataModel.getId(),
                    groceryDataModel.getBarcode(),
                    groceryDataModel.getName(),
                    groceryDataModel.getCalories_per_1U(),
                    groceryDataModel.getProteins_per_1U(),
                    groceryDataModel.getCarbohydrates_per_1U(),
                    groceryDataModel.getFats_per_1U(),
                    groceryDataModel.getType(),
                    new AllergenUIMapper().transformAll(groceryDataModel.getAllergens()),
                    new ServingUIMapper().transformAll(groceryDataModel.getServings())
            );
        }
        return groceryDisplayModel;
    }

    public ArrayList<GroceryDisplayModel> transformAll(List<GroceryDataModel> groceryDataModelList){
        ArrayList<GroceryDisplayModel> groceryDisplayModels = new ArrayList<>();
        for (GroceryDataModel data: groceryDataModelList){
            groceryDisplayModels.add(transform(data));
        }
        return groceryDisplayModels;
    }
}
