package de.karzek.diettracker.data.cache.model;

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
public class RecipeEntity extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private byte[] photo;
    private int portions;
    private RealmList<IngredientEntity> ingredients;
    private RealmList<PreparationStepEntity> steps;
}