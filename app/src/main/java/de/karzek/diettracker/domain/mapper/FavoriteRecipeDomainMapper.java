package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import de.karzek.diettracker.data.model.FavoriteRecipeDataModel;
import de.karzek.diettracker.domain.model.FavoriteGroceryDomainModel;
import de.karzek.diettracker.domain.model.FavoriteRecipeDomainModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteRecipeDomainMapper {
    public FavoriteRecipeDomainModel transform(FavoriteRecipeDataModel dataModel){
        FavoriteRecipeDomainModel domainModel = null;
        if(dataModel != null){
            domainModel = new FavoriteRecipeDomainModel(dataModel.getId(),
                    new RecipeDomainMapper().transform(dataModel.getRecipe())
            );
        }
        return domainModel;
    }

    public ArrayList<FavoriteRecipeDomainModel> transformAll(List<FavoriteRecipeDataModel> dataModels){
        ArrayList<FavoriteRecipeDomainModel> domainModels = new ArrayList<>();
        for (FavoriteRecipeDataModel data: dataModels){
            domainModels.add(transform(data));
        }
        return domainModels;
    }

    public FavoriteRecipeDataModel transformToData(FavoriteRecipeDomainModel domainModel){
        FavoriteRecipeDataModel dataModel = null;
        if(domainModel != null){
            dataModel = new FavoriteRecipeDataModel(domainModel.getId(),
                    new RecipeDomainMapper().transformToData(domainModel.getRecipe())
            );
        }
        return dataModel;
    }

    public ArrayList<FavoriteRecipeDataModel> transformAllToData(List<FavoriteRecipeDomainModel> domainModels) {
        ArrayList<FavoriteRecipeDataModel> dataModels = new ArrayList<>();
        for (FavoriteRecipeDomainModel data: domainModels){
            dataModels.add(transformToData(data));
        }
        return dataModels;
    }
}
