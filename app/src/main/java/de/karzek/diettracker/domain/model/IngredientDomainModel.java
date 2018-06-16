package de.karzek.diettracker.domain.model;

import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Value
public class IngredientDomainModel {
    private int id;
    private GroceryDomainModel grocery;
    private int amount;
    private UnitDomainModel unit;
}
