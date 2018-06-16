package de.karzek.diettracker.presentation.model;

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
public class ManualIngredientDisplayModel extends IngredientDisplayModel{
    private String groceryQuery;

    public ManualIngredientDisplayModel(int id, GroceryDisplayModel groceryDisplayModel, float amount, UnitDisplayModel unit, String groceryQuery){
        super(id, groceryDisplayModel, amount, unit);
        this.groceryQuery = groceryQuery;
    }
}
