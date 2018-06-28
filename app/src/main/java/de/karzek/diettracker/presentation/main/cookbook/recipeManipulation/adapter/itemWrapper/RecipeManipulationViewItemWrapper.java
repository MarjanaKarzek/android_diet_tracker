package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
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
public class RecipeManipulationViewItemWrapper {

    @IntDef({ItemType.PHOTO_VIEW,
            ItemType.INGREDIENTS_TITLE_AND_PORTIONS_VIEW,
            ItemType.INGREDIENT_ITEM,
            ItemType.MANUAL_INGREDIENT_ITEM,
            ItemType.INGREDIENT_ITEM_ADD_VIEW,
            ItemType.PREPARATION_STEPS_TITLE_VIEW,
            ItemType.PREPARATION_STEP_ITEM,
            ItemType.PREPARATION_STEP_ITEM_ADD_VIEW,
            ItemType.MEALS_TITLE_VIEW,
            ItemType.MEAL_LIST,
            ItemType.RECIPE_SAVE_VIEW,
            ItemType.RECIPE_DELETE_VIEW})
    public @interface ItemType {
        int PHOTO_VIEW = 0;
        int INGREDIENTS_TITLE_AND_PORTIONS_VIEW = 1;
        int INGREDIENT_ITEM = 2;
        int MANUAL_INGREDIENT_ITEM = 3;
        int INGREDIENT_ITEM_ADD_VIEW = 4;
        int PREPARATION_STEPS_TITLE_VIEW = 5;
        int PREPARATION_STEP_ITEM = 6;
        int PREPARATION_STEP_ITEM_ADD_VIEW = 7;
        int MEALS_TITLE_VIEW = 8;
        int MEAL_LIST = 9;
        int RECIPE_SAVE_VIEW = 10;
        int RECIPE_DELETE_VIEW = 11;
    }

    @ItemType
    int type;
    Bitmap image;
    Float portions;
    IngredientDisplayModel ingredientDisplayModel;
    PreparationStepDisplayModel preparationStepDisplayModel;
    List<MealDisplayModel> meals;

    public RecipeManipulationViewItemWrapper(@ItemType int type) {
        this.type = type;
        image = null;
        portions = null;
        ingredientDisplayModel = null;
        preparationStepDisplayModel = null;
        meals = new ArrayList<>();
    }

    public RecipeManipulationViewItemWrapper(@ItemType int type, Bitmap image) {
        this.type = type;
        this.image = image;
        portions = null;
        ingredientDisplayModel = null;
        preparationStepDisplayModel = null;
        meals = new ArrayList<>();
    }

    public RecipeManipulationViewItemWrapper(@ItemType int type, float portions) {
        this.type = type;
        image = null;
        this.portions = portions;
        ingredientDisplayModel = null;
        preparationStepDisplayModel = null;
        meals = new ArrayList<>();
    }

    public RecipeManipulationViewItemWrapper(@ItemType int type, IngredientDisplayModel displayModel) {
        this.type = type;
        image = null;
        portions = null;
        this.ingredientDisplayModel = displayModel;
        preparationStepDisplayModel = null;
        meals = new ArrayList<>();
    }

    public RecipeManipulationViewItemWrapper(@ItemType int type, PreparationStepDisplayModel displayModel) {
        this.type = type;
        image = null;
        portions = null;
        ingredientDisplayModel = null;
        this.preparationStepDisplayModel = displayModel;
        meals = new ArrayList<>();
    }

    public RecipeManipulationViewItemWrapper(@ItemType int type, List<MealDisplayModel> displayModels) {
        this.type = type;
        image = null;
        portions = null;
        ingredientDisplayModel = null;
        preparationStepDisplayModel = null;
        this.meals = displayModels;
    }
}
