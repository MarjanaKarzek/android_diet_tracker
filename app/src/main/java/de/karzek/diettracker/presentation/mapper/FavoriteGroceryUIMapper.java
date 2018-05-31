package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.AllergenDataModel;
import de.karzek.diettracker.domain.model.FavoriteGroceryDomainModel;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.FavoriteGroceryDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteGroceryUIMapper {
    public FavoriteGroceryDisplayModel transform(FavoriteGroceryDomainModel favoriteGroceryDomainModel){
        FavoriteGroceryDisplayModel favoriteGroceryDisplayModel = null;
        if(favoriteGroceryDomainModel != null){
            favoriteGroceryDisplayModel = new FavoriteGroceryDisplayModel(favoriteGroceryDomainModel.getId(),
                    new GroceryUIMapper().transform(favoriteGroceryDomainModel.getGrocery())
            );
        }
        return favoriteGroceryDisplayModel;
    }

    public ArrayList<FavoriteGroceryDisplayModel> transformAll(List<FavoriteGroceryDomainModel> favoriteGroceryDomainModels){
        ArrayList<FavoriteGroceryDisplayModel> favoriteGroceryDisplayModels = new ArrayList<>();
        for (FavoriteGroceryDomainModel data: favoriteGroceryDomainModels){
            favoriteGroceryDisplayModels.add(transform(data));
        }
        return favoriteGroceryDisplayModels;
    }
}
