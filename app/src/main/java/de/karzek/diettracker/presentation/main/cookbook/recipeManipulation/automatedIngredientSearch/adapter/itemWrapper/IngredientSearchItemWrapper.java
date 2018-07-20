package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.itemWrapper;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.ManualIngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.PreparationStepDisplayModel;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
@Value
public class IngredientSearchItemWrapper {

    @IntDef({ItemType.LOADING_INGREDIENT,
            ItemType.FOUND_INGREDIENT,
            ItemType.FAILED_INGREDIENT
    })
    public @interface ItemType {
        int LOADING_INGREDIENT = 0;
        int FOUND_INGREDIENT = 1;
        int FAILED_INGREDIENT = 2;
    }

    @ItemType
    int type;
    IngredientDisplayModel ingredientDisplayModel;
    int failReason;

    public IngredientSearchItemWrapper(@ItemType int type, IngredientDisplayModel displayModel) {
        this.type = type;
        this.ingredientDisplayModel = displayModel;
        failReason = -1;
    }

    public IngredientSearchItemWrapper(@ItemType int type, IngredientDisplayModel displayModel, int failReason) {
        this.type = type;
        this.ingredientDisplayModel = displayModel;
        this.failReason = failReason;
    }

}
