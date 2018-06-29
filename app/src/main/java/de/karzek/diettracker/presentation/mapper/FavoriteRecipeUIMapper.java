package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.model.FavoriteGroceryDomainModel;
import de.karzek.diettracker.domain.model.FavoriteRecipeDomainModel;
import de.karzek.diettracker.presentation.model.FavoriteGroceryDisplayModel;
import de.karzek.diettracker.presentation.model.FavoriteRecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteRecipeUIMapper {
    public FavoriteRecipeDisplayModel transform(FavoriteRecipeDomainModel domainModel){
        FavoriteRecipeDisplayModel displayModel = null;
        if(domainModel != null){
            displayModel = new FavoriteRecipeDisplayModel(domainModel.getId(),
                    new RecipeUIMapper().transform(domainModel.getRecipe())
            );
        }
        return displayModel;
    }

    public ArrayList<FavoriteRecipeDisplayModel> transformAll(List<FavoriteRecipeDomainModel> domainModels){
        ArrayList<FavoriteRecipeDisplayModel> displayModels = new ArrayList<>();
        for (FavoriteRecipeDomainModel data: domainModels){
            displayModels.add(transform(data));
        }
        return displayModels;
    }
}
