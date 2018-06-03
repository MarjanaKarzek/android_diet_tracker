package de.karzek.diettracker.data.cache.model;

import android.support.annotation.IntDef;

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
public class UnitEntity extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private int multiplier;
    private int type;

    @UnitEntityType
    public static final int UNIT_TYPE_SOLID = 0;
    public static final int UNIT_TYPE_LIQUID = 1;

    @IntDef({UNIT_TYPE_SOLID, UNIT_TYPE_LIQUID})

    private @interface UnitEntityType { }
}

