package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GroceryDomainMapper {
    public GroceryDomainModel transform(GroceryDataModel groceryDataModel){
        GroceryDomainModel groceryDomainModel = null;
        if(groceryDataModel != null){
            groceryDomainModel = new GroceryDomainModel(groceryDataModel.getId(),
                    groceryDataModel.getBarcode(),
                    groceryDataModel.getName(),
                    groceryDataModel.getCalories_per_1U(),
                    groceryDataModel.getProteins_per_1U(),
                    groceryDataModel.getCarbohydrates_per_1U(),
                    groceryDataModel.getFats_per_1U(),
                    groceryDataModel.getType(),
                    new AllergenDomainMapper().transformAll(groceryDataModel.getAllergens()),
                    new ServingDomainMapper().transformAll(groceryDataModel.getServings())
            );
        }
        return groceryDomainModel;
    }

    public ArrayList<GroceryDomainModel> transformAll(List<GroceryDataModel> groceryDataModelList){
        ArrayList<GroceryDomainModel> groceryDomainModels = new ArrayList<>();
        for (GroceryDataModel data: groceryDataModelList){
            groceryDomainModels.add(transform(data));
        }
        return groceryDomainModels;
    }
}
