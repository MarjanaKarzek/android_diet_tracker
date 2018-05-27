package de.karzek.diettracker.domain.model;

import java.util.ArrayList;

import de.karzek.diettracker.data.database.model.AllergenEntity;
import de.karzek.diettracker.data.database.model.ServingEntity;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Value
public class GroceryData {
    private int id;
    private int barcode;
    private String name;
    private int calories_per_1U;
    private int proteins_per_1U;
    private int carbohydrates_per_1U;
    private int fats_per_1U;
    private int type;
    private ArrayList<AllergenData> allergens;
    private ArrayList<ServingData> servings;
}
