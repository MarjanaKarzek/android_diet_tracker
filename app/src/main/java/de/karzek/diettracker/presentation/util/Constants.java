package de.karzek.diettracker.presentation.util;

/**
 * Created by MarjanaKarzek on 04.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 04.06.2018
 */
public class Constants {
    public static final long weekInMilliS = 1000 * 60 * 60 * 24 * 7;

    public static final String CALORIES = "CALORIES";
    public static final String PROTEINS = "PROTEINS";
    public static final String CARBS = "CARBS";
    public static final String FATS = "FATS";

    public static final String SHARED_PREFERENCES_SPLIT_ARRAY_CHAR = ";";
    public static final int INVALID_ENTITY_ID = -1;

    //Permissions
    public static final int ZXING_CAMERA_PERMISSION = 3001;
    public static final int CAMERA_PERMISSION = 3002;

    //Intent Result Codes
    public static final int GET_IMAGE_FROM_GALLERY_RESULT = 2001;
    public static final int GET_IMAGE_FROM_CAMERA_RESULT = 2002;

    public static final int ADD_REPLACE_INGREDIENT_INTENT_RESULT = 2003;
    public static final int EDIT_INGREDIENT_INTENT_RESULT = 2004;

    public static final int CLOSE_SELF_RESULT = 2005;

    public static final String ALL_MEALS = "ALL_MEALS";

    //Onboarding Views
    public static final int ONBOARDING_INGREDIENT_SEARCH = 0;
    public static final int ONBOARDING_DISPLAY_SETTINGS = 1;
    public static final int ONBOARDING_SUPPORT_OPTIONS = 2;
    public static final int ONBOARDING_SLIDE_OPTIONS = 3;
    public static final int ONBOARDING_WELCOME = 4;


}
