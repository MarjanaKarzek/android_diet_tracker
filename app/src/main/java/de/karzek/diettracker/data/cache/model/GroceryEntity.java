package de.karzek.diettracker.data.cache.model;

import android.support.annotation.IntDef;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
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
    private String barcode;
    @Index private String name;
    private float calories_per_1U;
    private float proteins_per_1U;
    private float carbohydrates_per_1U;
    private float fats_per_1U;
    private int type;
    private int unit_type;
    private RealmList<AllergenEntity> allergens;
    private RealmList<ServingEntity> servings;

    @GroceryEntityType
    public static final int TYPE_FOOD = 0;
    public static final int TYPE_DRINK = 1;

    @IntDef({TYPE_FOOD, TYPE_DRINK})

    private @interface GroceryEntityType { }

    @GroceryEntityUnitType
    public static final int TYPE_SOLID = 0;
    public static final int TYPE_LIQUID = 1;

    @IntDef({TYPE_FOOD, TYPE_DRINK})

    private @interface GroceryEntityUnitType { }
}
