package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import de.karzek.diettracker.domain.model.FavoriteGroceryDomainModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteGroceryDomainMapper {
    public FavoriteGroceryDomainModel transform(FavoriteGroceryDataModel favoriteGroceryDataModel){
        FavoriteGroceryDomainModel favoriteDomainModel = null;
        if(favoriteGroceryDataModel != null){
            favoriteDomainModel = new FavoriteGroceryDomainModel(favoriteGroceryDataModel.getId(),
                    new GroceryDomainMapper().transform(favoriteGroceryDataModel.getGrocery())
            );
        }
        return favoriteDomainModel;
    }

    public ArrayList<FavoriteGroceryDomainModel> transformAll(List<FavoriteGroceryDataModel> favoriteGroceryDataModels){
        ArrayList<FavoriteGroceryDomainModel> favoriteDomainModels = new ArrayList<>();
        for (FavoriteGroceryDataModel data: favoriteGroceryDataModels){
            favoriteDomainModels.add(transform(data));
        }
        return favoriteDomainModels;
    }

    public FavoriteGroceryDataModel transformToData(FavoriteGroceryDomainModel domainModel){
        FavoriteGroceryDataModel dataModel = null;
        if(domainModel != null){
            dataModel = new FavoriteGroceryDataModel(domainModel.getId(),
                    new GroceryDomainMapper().transformToData(domainModel.getGrocery())
            );
        }
        return dataModel;
    }

    public ArrayList<FavoriteGroceryDataModel> transformAllToData(List<FavoriteGroceryDomainModel> domainModels) {
        ArrayList<FavoriteGroceryDataModel> dataModels = new ArrayList<>();
        for (FavoriteGroceryDomainModel data: domainModels){
            dataModels.add(transformToData(data));
        }
        return dataModels;
    }
}
