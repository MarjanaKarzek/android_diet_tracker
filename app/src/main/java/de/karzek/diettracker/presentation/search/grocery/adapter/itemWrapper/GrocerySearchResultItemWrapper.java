package de.karzek.diettracker.presentation.search.grocery.adapter.itemWrapper;

import android.support.annotation.IntDef;

import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
@Value
public class GrocerySearchResultItemWrapper {

    @IntDef({ItemType.FOOD, ItemType.DRINK})
    public @interface ItemType {
        public static final int FOOD = 0;
        public static final int DRINK = 1;
    }

    @ItemType
    int type;
    GroceryDisplayModel displayModel;

    public GrocerySearchResultItemWrapper(@ItemType int type, GroceryDisplayModel displayModel) {
        this.type = type;
        this.displayModel = displayModel;
    }

}
