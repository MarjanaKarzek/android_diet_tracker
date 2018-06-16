package de.karzek.diettracker.domain.model;

import java.util.ArrayList;

import de.karzek.diettracker.data.model.IngredientDataModel;
import de.karzek.diettracker.data.model.PreparationStepDataModel;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Value
public class RecipeDomainModel {
    private int id;
    private String title;
    private byte[] photo;
    private float portions;
    private ArrayList<IngredientDomainModel> ingredients;
    private ArrayList<PreparationStepDomainModel> steps;
}
