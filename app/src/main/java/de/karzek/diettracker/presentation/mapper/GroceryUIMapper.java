package de.karzek.diettracker.presentation.mapper;

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
public class GroceryUIMapper {
    public GroceryDisplayModel transform(GroceryDomainModel groceryDomainModel){
        GroceryDisplayModel groceryDisplayModel = null;
        if(groceryDomainModel != null){
            groceryDisplayModel = new GroceryDisplayModel(groceryDomainModel.getId(),
                    groceryDomainModel.getBarcode(),
                    groceryDomainModel.getName(),
                    groceryDomainModel.getCalories_per_1U(),
                    groceryDomainModel.getProteins_per_1U(),
                    groceryDomainModel.getCarbohydrates_per_1U(),
                    groceryDomainModel.getFats_per_1U(),
                    groceryDomainModel.getType(),
                    groceryDomainModel.getUnit_type(),
                    new AllergenUIMapper().transformAll(groceryDomainModel.getAllergens()),
                    new ServingUIMapper().transformAll(groceryDomainModel.getServings())
            );
        }
        return groceryDisplayModel;
    }

    public ArrayList<GroceryDisplayModel> transformAll(List<GroceryDomainModel> groceryDataModelList){
        ArrayList<GroceryDisplayModel> groceryDisplayModels = new ArrayList<>();
        for (GroceryDomainModel data: groceryDataModelList){
            groceryDisplayModels.add(transform(data));
        }
        return groceryDisplayModels;
    }

    public GroceryDomainModel transformToDomain(GroceryDisplayModel groceryDisplayModel){
        GroceryDomainModel groceryDomainModel = null;
        if(groceryDisplayModel != null){
            groceryDomainModel = new GroceryDomainModel(groceryDisplayModel.getId(),
                    groceryDisplayModel.getBarcode(),
                    groceryDisplayModel.getName(),
                    groceryDisplayModel.getCalories_per_1U(),
                    groceryDisplayModel.getProteins_per_1U(),
                    groceryDisplayModel.getCarbohydrates_per_1U(),
                    groceryDisplayModel.getFats_per_1U(),
                    groceryDisplayModel.getType(),
                    groceryDisplayModel.getUnit_type(),
                    new AllergenUIMapper().transformAllToDomain(groceryDisplayModel.getAllergens()),
                    new ServingUIMapper().transformAllToDomain(groceryDisplayModel.getServings())
            );
        }
        return groceryDomainModel;
    }

    public ArrayList<GroceryDomainModel> transformAllToDomain(ArrayList<GroceryDisplayModel> groceryDisplayModels) {
        ArrayList<GroceryDomainModel> groceryDomainModels = new ArrayList<>();
        for (GroceryDisplayModel data: groceryDisplayModels){
            groceryDomainModels.add(transformToDomain(data));
        }
        return groceryDomainModels;
    }
}
