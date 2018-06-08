package de.karzek.diettracker.domain.model;

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
public class GroceryDomainModel {
    private int id;
    private String barcode;
    private String name;
    private float calories_per_1U;
    private float proteins_per_1U;
    private float carbohydrates_per_1U;
    private float fats_per_1U;
    private int type;
    private int unit_type;
    private ArrayList<AllergenDomainModel> allergens;
    private ArrayList<ServingDomainModel> servings;
}
