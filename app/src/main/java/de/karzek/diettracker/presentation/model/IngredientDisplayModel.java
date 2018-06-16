package de.karzek.diettracker.presentation.model;

import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Value
public class IngredientDisplayModel {
    private int id;
    private GroceryDisplayModel grocery;
    private int amount;
    private UnitDisplayModel unit;
}
