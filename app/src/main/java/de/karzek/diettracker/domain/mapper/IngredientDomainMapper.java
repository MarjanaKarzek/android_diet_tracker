package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.IngredientDataModel;
import de.karzek.diettracker.data.model.PreparationStepDataModel;
import de.karzek.diettracker.domain.model.IngredientDomainModel;
import de.karzek.diettracker.domain.model.PreparationStepDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class IngredientDomainMapper {
    public IngredientDomainModel transform(IngredientDataModel dataModel){
        IngredientDomainModel domainModel = null;
        if(dataModel != null){
            domainModel = new IngredientDomainModel(dataModel.getId(),
                    new GroceryDomainMapper().transform(dataModel.getGrocery()),
                    dataModel.getAmount(),
                    new UnitDomainMapper().transform(dataModel.getUnit()));
        }
        return domainModel;
    }

    public ArrayList<IngredientDomainModel> transformAll(List<IngredientDataModel> dataModels){
        ArrayList<IngredientDomainModel> domainModels = new ArrayList<>();
        for (IngredientDataModel data: dataModels){
            domainModels.add(transform(data));
        }
        return domainModels;
    }

    public IngredientDataModel transformToData(IngredientDomainModel domainModel){
        IngredientDataModel dataModel = null;
        if(domainModel != null){
            dataModel = new IngredientDataModel(domainModel.getId(),
                    new GroceryDomainMapper().transformToData(domainModel.getGrocery()),
                    domainModel.getAmount(),
                    new UnitDomainMapper().transformToData(domainModel.getUnit()));
        }
        return dataModel;
    }

    public ArrayList<IngredientDataModel> transformAllToData(List<IngredientDomainModel> domainModels) {
        ArrayList<IngredientDataModel> dataModels = new ArrayList<>();
        for (IngredientDomainModel data: domainModels){
            dataModels.add(transformToData(data));
        }
        return dataModels;
    }
}
