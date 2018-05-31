package de.karzek.diettracker.data.cache.model;

import android.support.annotation.IntDef;

import java.util.List;

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
public class FavoriteEntity extends RealmObject {
    @PrimaryKey
    private int id;
    private int type;
    private int favoriteId;
}
