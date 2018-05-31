package de.karzek.diettracker.data.model;

import de.karzek.diettracker.data.cache.model.GroceryEntity;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Value
public class FavoriteDataModel {
    private int id;
    private GroceryDataModel grocery;
}
