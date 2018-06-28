package de.karzek.diettracker.presentation.model;

import java.util.ArrayList;

import de.karzek.diettracker.domain.model.IngredientDomainModel;
import de.karzek.diettracker.domain.model.PreparationStepDomainModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Data
@AllArgsConstructor
public class RecipeDisplayModel {
    private int id;
    private String title;
    private byte[] photo;
    private float portions;
    private ArrayList<IngredientDisplayModel> ingredients;
    private ArrayList<PreparationStepDisplayModel> steps;
    private ArrayList<MealDisplayModel> meals;
}
