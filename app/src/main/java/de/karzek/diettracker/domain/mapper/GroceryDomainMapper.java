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
                    groceryDataModel.getUnit_type(),
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

    public GroceryDataModel transformToData(GroceryDomainModel groceryDomainModel){
        GroceryDataModel groceryDataModel = null;
        if(groceryDomainModel != null){
            groceryDataModel = new GroceryDataModel(groceryDomainModel.getId(),
                    groceryDomainModel.getBarcode(),
                    groceryDomainModel.getName(),
                    groceryDomainModel.getCalories_per_1U(),
                    groceryDomainModel.getProteins_per_1U(),
                    groceryDomainModel.getCarbohydrates_per_1U(),
                    groceryDomainModel.getFats_per_1U(),
                    groceryDomainModel.getType(),
                    groceryDomainModel.getUnit_type(),
                    new AllergenDomainMapper().transformAllToData(groceryDomainModel.getAllergens()),
                    new ServingDomainMapper().transformAllToData(groceryDomainModel.getServings())
            );
        }
        return groceryDataModel;
    }

    public List<GroceryDataModel> transformAllToData(List<GroceryDomainModel> groceryDomainModels) {
        ArrayList<GroceryDataModel> groceryDataModels = new ArrayList<>();
        for (GroceryDomainModel data: groceryDomainModels){
            groceryDataModels.add(transformToData(data));
        }
        return groceryDataModels;
    }
}
