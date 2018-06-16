package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.domain.model.IngredientDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class IngredientUIMapper {
    public IngredientDisplayModel transform(IngredientDomainModel domainModel){
        IngredientDisplayModel displayModel = null;
        if(domainModel != null){
            displayModel = new IngredientDisplayModel(domainModel.getId(),
                    new GroceryUIMapper().transform(domainModel.getGrocery()),
                    domainModel.getAmount(),
                    new UnitUIMapper().transform(domainModel.getUnit())
            );
        }
        return displayModel;
    }

    public ArrayList<IngredientDisplayModel> transformAll(List<IngredientDomainModel> domainModels){
        ArrayList<IngredientDisplayModel> displayModels = new ArrayList<>();
        for (IngredientDomainModel data: domainModels){
            displayModels.add(transform(data));
        }
        return displayModels;
    }

    public IngredientDomainModel transformToDomain(IngredientDisplayModel displayModel){
        IngredientDomainModel domainModel = null;
        if(displayModel != null){
            domainModel = new IngredientDomainModel(displayModel.getId(),
                    new GroceryUIMapper().transformToDomain(displayModel.getGrocery()),
                    displayModel.getAmount(),
                    new UnitUIMapper().transformToDomain(displayModel.getUnit())
            );
        }
        return domainModel;
    }

    public ArrayList<IngredientDomainModel> transformAllToDomain(ArrayList<IngredientDisplayModel> displayModels) {
        ArrayList<IngredientDomainModel> domainModels = new ArrayList<>();
        for (IngredientDisplayModel data: displayModels){
            domainModels.add(transformToDomain(data));
        }
        return domainModels;
    }
}
