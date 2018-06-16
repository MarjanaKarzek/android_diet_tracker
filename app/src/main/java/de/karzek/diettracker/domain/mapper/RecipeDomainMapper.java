package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.IngredientDataModel;
import de.karzek.diettracker.data.model.RecipeDataModel;
import de.karzek.diettracker.domain.model.IngredientDomainModel;
import de.karzek.diettracker.domain.model.RecipeDomainModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class RecipeDomainMapper {
    public RecipeDomainModel transform(RecipeDataModel dataModel){
        RecipeDomainModel domainModel = null;
        if(dataModel != null){
            domainModel = new RecipeDomainModel(dataModel.getId(),
                    dataModel.getTitle(),
                    dataModel.getPhoto(),
                    dataModel.getPortions(),
                    new IngredientDomainMapper().transformAll(dataModel.getIngredients()),
                    new PreparationStepDomainMapper().transformAll(dataModel.getSteps()));
        }
        return domainModel;
    }

    public ArrayList<RecipeDomainModel> transformAll(List<RecipeDataModel> dataModels){
        ArrayList<RecipeDomainModel> domainModels = new ArrayList<>();
        for (RecipeDataModel data: dataModels){
            domainModels.add(transform(data));
        }
        return domainModels;
    }

    public RecipeDataModel transformToData(RecipeDomainModel domainModel){
        RecipeDataModel dataModel = null;
        if(domainModel != null){
            dataModel = new RecipeDataModel(domainModel.getId(),
                    domainModel.getTitle(),
                    domainModel.getPhoto(),
                    domainModel.getPortions(),
                    new IngredientDomainMapper().transformAllToData(domainModel.getIngredients()),
                    new PreparationStepDomainMapper().transformAllToData(domainModel.getSteps()));
        }
        return dataModel;
    }

    public ArrayList<RecipeDataModel> transformAllToData(List<RecipeDomainModel> domainModels) {
        ArrayList<RecipeDataModel> dataModels = new ArrayList<>();
        for (RecipeDomainModel data: domainModels){
            dataModels.add(transformToData(data));
        }
        return dataModels;
    }
}
