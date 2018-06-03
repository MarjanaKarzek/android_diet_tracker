package de.karzek.diettracker.data.cache.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by MarjanaKarzek on 03.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 03.06.2018
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MealEntity extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String startTime;
    private String endTime;
}
