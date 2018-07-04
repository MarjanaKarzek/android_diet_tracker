package de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.itemWrapper;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.PreparationStepDisplayModel;
import lombok.Value;

import static de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.itemWrapper.RecipeDetailsViewItemWrapper.ItemType.TITLE_VIEW;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
@Value
public class RecipeDetailsViewItemWrapper {

    @IntDef({ItemType.PHOTO_VIEW,
            ItemType.CALORIES_AND_MAKROS_DETAILS_VIEW,
            ItemType.CALORY_DETAILS_VIEW,
            ItemType.TITLE_VIEW,
            ItemType.INGREDIENTS_TITLE_VIEW,
            ItemType.INGREDIENT_VIEW,
            ItemType.PREPARATION_STEP_VIEW,
            ItemType.MEALS_VIEW
    })
    public @interface ItemType {
        int PHOTO_VIEW = 0;
        int CALORIES_AND_MAKROS_DETAILS_VIEW = 1;
        int CALORY_DETAILS_VIEW = 2;
        int TITLE_VIEW = 3;
        int INGREDIENTS_TITLE_VIEW = 4;
        int INGREDIENT_VIEW = 5;
        int PREPARATION_STEP_VIEW = 6;
        int MEALS_VIEW = 7;
    }

    @ItemType
    int type;

    Bitmap image;
    String title;
    IngredientDisplayModel ingredientDisplayModel;
    PreparationStepDisplayModel preparationStepDisplayModel;
    List<MealDisplayModel> meals;
    HashMap<String, Long> maxValues;
    HashMap<String, Float> values;

    public RecipeDetailsViewItemWrapper(@ItemType int type, Bitmap image) {
        this.type = type;
        this.image = image;
        title = null;
        ingredientDisplayModel = null;
        preparationStepDisplayModel = null;
        meals = null;
        maxValues = null;
        values = null;
    }

    public RecipeDetailsViewItemWrapper(@ItemType int type, HashMap<String, Long> maxValues, HashMap<String, Float> values) {
        this.type = type;
        image = null;
        title = null;
        ingredientDisplayModel = null;
        preparationStepDisplayModel = null;
        meals = null;
        this.maxValues = maxValues;
        this.values = values;
    }

    public RecipeDetailsViewItemWrapper(@ItemType int type, String title) {
        this.type = type;
        image = null;
        this.title = title;
        ingredientDisplayModel = null;
        preparationStepDisplayModel = null;
        meals = null;
        maxValues = null;
        values = null;
    }

    public RecipeDetailsViewItemWrapper(@ItemType int type, IngredientDisplayModel ingredientDisplayModel) {
        this.type = type;
        image = null;
        title = null;
        this.ingredientDisplayModel = ingredientDisplayModel;
        preparationStepDisplayModel = null;
        meals = null;
        maxValues = null;
        values = null;
    }

    public RecipeDetailsViewItemWrapper(@ItemType int type, PreparationStepDisplayModel preparationStepDisplayModel) {
        this.type = type;
        image = null;
        title = null;
        ingredientDisplayModel = null;
        this.preparationStepDisplayModel = preparationStepDisplayModel;
        meals = null;
        maxValues = null;
        values = null;
    }

    public RecipeDetailsViewItemWrapper(@ItemType int type, ArrayList<MealDisplayModel> meals) {
        this.type = type;
        image = null;
        title = null;
        ingredientDisplayModel = null;
        preparationStepDisplayModel = null;
        this.meals = meals;
        maxValues = null;
        values = null;
    }

}
