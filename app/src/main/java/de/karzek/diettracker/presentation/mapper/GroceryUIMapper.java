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
}
