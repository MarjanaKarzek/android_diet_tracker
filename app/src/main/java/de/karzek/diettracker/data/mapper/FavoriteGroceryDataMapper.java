package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.FavoriteEntity;
import de.karzek.diettracker.data.model.FavoriteDataModel;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class FavoriteDataMapper {

    public FavoriteDataModel transform(FavoriteEntity favoriteEntity){
        FavoriteDataModel favoriteDataModel = null;
        if(favoriteEntity != null){
            favoriteDataModel = new FavoriteDataModel(favoriteEntity.getId(),
                    favoriteEntity.getType(),
                    favoriteEntity.getFavoriteId()
            );
        }
        return favoriteDataModel;
    }

    public ArrayList<FavoriteDataModel> transformAll(List<FavoriteEntity> favoriteEntities){
        ArrayList<FavoriteDataModel> favoriteDataModels = new ArrayList<>();
        for (FavoriteEntity entity: favoriteEntities){
            favoriteDataModels.add(transform(entity));
        }
        return favoriteDataModels;
    }

}
