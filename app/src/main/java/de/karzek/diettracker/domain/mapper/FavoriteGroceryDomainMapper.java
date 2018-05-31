package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.AllergenDataModel;
import de.karzek.diettracker.data.model.FavoriteDataModel;
import de.karzek.diettracker.domain.model.AllergenDomainModel;
import de.karzek.diettracker.domain.model.FavoriteDomainModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteDomainMapper {
    public FavoriteDomainModel transform(FavoriteDataModel favoriteDataModel){
        FavoriteDomainModel favoriteDomainModel = null;
        if(favoriteDataModel != null){
            favoriteDomainModel = new FavoriteDomainModel(favoriteDataModel.getId(),
                    favoriteDataModel.getType(),
                    favoriteDataModel.getFavoriteId()
            );
        }
        return favoriteDomainModel;
    }

    public ArrayList<FavoriteDomainModel> transformAll(List<FavoriteDataModel> favoriteDataModels){
        ArrayList<FavoriteDomainModel> favoriteDomainModels = new ArrayList<>();
        for (FavoriteDataModel data: favoriteDataModels){
            favoriteDomainModels.add(transform(data));
        }
        return favoriteDomainModels;
    }
}
