package de.karzek.diettracker.data.model;

import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Value
public class DiaryEntryDataModel {
    private int id;
    private MealDataModel meal;
    private float amount;
    private UnitDataModel unit;
    private GroceryDataModel grocery;
    private String date;
}
