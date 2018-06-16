package de.karzek.diettracker.presentation.model;

import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Data
@AllArgsConstructor
public class IngredientDisplayModel {
    private int id;
    private GroceryDisplayModel grocery;
    private float amount;
    private UnitDisplayModel unit;

    public IngredientDisplayModel() {
        id = -1;
        grocery = null;
        amount = 0;
        unit = null;
    }
}
