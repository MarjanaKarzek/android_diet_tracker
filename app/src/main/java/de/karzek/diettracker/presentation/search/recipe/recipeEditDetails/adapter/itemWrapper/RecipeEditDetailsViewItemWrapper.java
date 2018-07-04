package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.itemWrapper;

import android.graphics.Bitmap;
import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
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
public class RecipeEditDetailsViewItemWrapper {

    @IntDef({ItemType.PHOTO_VIEW,
            ItemType.CALORIES_AND_MAKROS_DETAILS_VIEW,
            ItemType.CALORY_DETAILS_VIEW,
            ItemType.INGREDIENTS_TITLE_VIEW,
            ItemType.INGREDIENT_VIEW,
            ItemType.MEAL_SELECTOR_VIEW,
            ItemType.DATE_SELECTOR_VIEW,
            ItemType.ADD_VIEW
    })
    public @interface ItemType {
        int PHOTO_VIEW = 0;
        int CALORIES_AND_MAKROS_DETAILS_VIEW = 1;
        int CALORY_DETAILS_VIEW = 2;
        int INGREDIENTS_TITLE_VIEW = 3;
        int INGREDIENT_VIEW = 4;
        int MEAL_SELECTOR_VIEW = 5;
        int DATE_SELECTOR_VIEW = 6;
        int ADD_VIEW = 7;
    }

    @ItemType
    int type;

    Bitmap image;
    IngredientDisplayModel ingredientDisplayModel;
    List<MealDisplayModel> meals;
    int selectedMeal;
    String date;
    float portions;
    HashMap<String, Long> maxValues;
    HashMap<String, Float> values;

    public RecipeEditDetailsViewItemWrapper(@ItemType int type) {
        this.type = type;
        image = null;
        ingredientDisplayModel = null;
        meals = null;
        selectedMeal = 0;
        date = null;
        portions = 0.0f;
        maxValues = null;
        values = null;
    }

    public RecipeEditDetailsViewItemWrapper(@ItemType int type, Bitmap image) {
        this.type = type;
        this.image = image;
        ingredientDisplayModel = null;
        meals = null;
        selectedMeal = 0;
        date = null;
        portions = 0.0f;
        maxValues = null;
        values = null;
    }

    public RecipeEditDetailsViewItemWrapper(@ItemType int type, HashMap<String, Long> maxValues, HashMap<String, Float> values) {
        this.type = type;
        image = null;
        ingredientDisplayModel = null;
        meals = null;
        selectedMeal = 0;
        date = null;
        portions = 0.0f;
        this.maxValues = maxValues;
        this.values = values;
    }

    public RecipeEditDetailsViewItemWrapper(@ItemType int type, IngredientDisplayModel ingredientDisplayModel, float portions) {
        this.type = type;
        image = null;
        this.ingredientDisplayModel = ingredientDisplayModel;
        meals = null;
        selectedMeal = 0;
        date = null;
        this.portions = portions;
        maxValues = null;
        values = null;
    }

    public RecipeEditDetailsViewItemWrapper(@ItemType int type, ArrayList<MealDisplayModel> meals, int selectedMeal) {
        this.type = type;
        image = null;
        ingredientDisplayModel = null;
        this.meals = meals;
        this.selectedMeal = selectedMeal;
        date = null;
        portions = 0.0f;
        maxValues = null;
        values = null;
    }

    public RecipeEditDetailsViewItemWrapper(@ItemType int type, String date) {
        this.type = type;
        image = null;
        ingredientDisplayModel = null;
        meals = null;
        selectedMeal = 0;
        this.date = date;
        portions = 0.0f;
        maxValues = null;
        values = null;
    }

    public RecipeEditDetailsViewItemWrapper(@ItemType int type, float portions) {
        this.type = type;
        image = null;
        ingredientDisplayModel = null;
        meals = null;
        selectedMeal = 0;
        date = null;
        this.portions = portions;
        maxValues = null;
        values = null;
    }

}
