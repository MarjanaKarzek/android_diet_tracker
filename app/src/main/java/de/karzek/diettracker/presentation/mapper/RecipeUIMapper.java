package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.model.IngredientDomainModel;
import de.karzek.diettracker.domain.model.RecipeDomainModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class RecipeUIMapper {
    public RecipeDisplayModel transform(RecipeDomainModel domainModel){
        RecipeDisplayModel displayModel = null;
        if(domainModel != null){
            displayModel = new RecipeDisplayModel(domainModel.getId(),
                    domainModel.getTitle(),
                    domainModel.getPhoto(),
                    domainModel.getPortions(),
                    new IngredientUIMapper().transformAll(domainModel.getIngredients()),
                    new PreparationStepUIMapper().transformAll(domainModel.getSteps()),
                    new MealUIMapper().transformAll(domainModel.getMeals())
            );
        }
        return displayModel;
    }

    public ArrayList<RecipeDisplayModel> transformAll(List<RecipeDomainModel> domainModels){
        ArrayList<RecipeDisplayModel> displayModels = new ArrayList<>();
        for (RecipeDomainModel data: domainModels){
            displayModels.add(transform(data));
        }
        return displayModels;
    }

    public RecipeDomainModel transformToDomain(RecipeDisplayModel displayModel){
        RecipeDomainModel domainModel = null;
        if(displayModel != null){
            domainModel = new RecipeDomainModel(displayModel.getId(),
                    displayModel.getTitle(),
                    displayModel.getPhoto(),
                    displayModel.getPortions(),
                    new IngredientUIMapper().transformAllToDomain(displayModel.getIngredients()),
                    new PreparationStepUIMapper().transformAllToDomain(displayModel.getSteps()),
                    new MealUIMapper().transformAllToDomain(displayModel.getMeals())
            );
        }
        return domainModel;
    }

    public ArrayList<RecipeDomainModel> transformAllToDomain(ArrayList<RecipeDisplayModel> displayModels) {
        ArrayList<RecipeDomainModel> domainModels = new ArrayList<>();
        for (RecipeDisplayModel data: displayModels){
            domainModels.add(transformToDomain(data));
        }
        return domainModels;
    }
}
