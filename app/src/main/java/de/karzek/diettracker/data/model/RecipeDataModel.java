package de.karzek.diettracker.data.model;

import java.util.ArrayList;

import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Value
public class RecipeDataModel {
    private int id;
    private String title;
    private byte[] photo;
    private int portions;
    private ArrayList<IngredientDataModel> ingredients;
    private ArrayList<PreparationStepDataModel> steps;
}
