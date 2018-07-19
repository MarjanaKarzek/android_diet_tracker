package de.karzek.diettracker.data.cache.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DiaryEntryEntity extends RealmObject {
    @PrimaryKey
    private int id;
    private MealEntity meal;
    private float amount;
    private UnitEntity unit;
    private GroceryEntity grocery;
    private String date;
}