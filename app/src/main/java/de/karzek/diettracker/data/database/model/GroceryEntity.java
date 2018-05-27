package de.karzek.diettracker.data.database.model;

import io.realm.RealmList;
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
public class GroceryEntity extends RealmObject {
    @PrimaryKey
    private int id;
    private int barcode;
    private String name;
    private int calories_per_1U;
    private int proteins_per_1U;
    private int carbohydrates_per_1U;
    private int fats_per_1U;
    private int type;
    private RealmList<AllergenEntity> allergens;
    private RealmList<ServingEntity> servings;
}
