package de.karzek.diettracker.domain.model;

import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Value
public class DiaryEntryDomainModel {
    private int id;
    private MealDomainModel meal;
    private float amount;
    private UnitDomainModel unit;
    private GroceryDomainModel grocery;
    private String date;
}
