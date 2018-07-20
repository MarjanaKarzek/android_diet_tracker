package de.karzek.diettracker.domain.interactor.manager;

import java.util.ArrayList;

import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_DISPLAY_SETTINGS;
import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_INGREDIENT_SEARCH;
import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_SLIDE_OPTIONS;
import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_SUPPORT_OPTIONS;
import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_WELCOME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_BOTTLE_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_GLASS_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_ONBOARDING_DISPLAY_SETTINGS_STATUS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_ONBOARDING_INGREDIENT_SEARCH_STATUS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_ONBOARDING_SLIDE_OPTIONS_STATUS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_ONBOARDING_SUPPORT_OPTIONS_STATUS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_ONBOARDING_WELCOME_STATUS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_CALORIES_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_CARBS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_FATS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_LIQUID_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_PROTEINS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_SETTING_NUTRITION_DETAILS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_START_SCREEN_LIQUIDS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_START_SCREEN_RECIPE;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_BOTTLE_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_FALSE;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_GLASS_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_CALORIES_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_CARBS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_FATS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_LIQUID_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_PROTEINS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_TRUE;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class SharedPreferencesManagerImpl implements SharedPreferencesManager {

    private SharedPreferencesUtil sharedPreferencesUtil;

    public SharedPreferencesManagerImpl(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void initializeStandardValues() {
        sharedPreferencesUtil.initialiseStandardValues();
    }

    @Override
    public ArrayList<Integer> getAllergenIds() {
        String allAllergens = sharedPreferencesUtil.getString(SharedPreferencesUtil.KEY_ALLERGENS, "");

        ArrayList<Integer> allergens = new ArrayList<>();
        if(!allAllergens.equals("")) {
            String[] allergenStrings = allAllergens.split(Constants.SHARED_PREFERENCES_SPLIT_ARRAY_CHAR);
            for (String allergen : allergenStrings) {
                allergens.add(Integer.valueOf(allergen));
            }
        }

        return allergens;
    }

    @Override
    public void putAllergenIds(String allergenSelection) {
        sharedPreferencesUtil.setString(SharedPreferencesUtil.KEY_ALLERGENS, allergenSelection);
    }

    @Override
    public String getNutritionDetailsSetting() {
        return sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
    }

    @Override
    public void setNutritionDetailsSetting(boolean checked) {
        if (checked)
            sharedPreferencesUtil.setString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        else
            sharedPreferencesUtil.setString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
    }

    @Override
    public void setStartScreenRecipesSetting(boolean checked) {
        sharedPreferencesUtil.setBoolean(KEY_START_SCREEN_RECIPE, checked);
    }

    @Override
    public void setStartScreenLiquidsSetting(boolean checked) {
        sharedPreferencesUtil.setBoolean(KEY_START_SCREEN_LIQUIDS, checked);
    }

    @Override
    public int getCaloriesGoal() {
        return sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CALORIES_DAILY, VALUE_REQUIREMENT_CALORIES_DAILY);
    }

    @Override
    public int getProteinsGoal() {
        return sharedPreferencesUtil.getInt(KEY_REQUIREMENT_PROTEINS_DAILY, VALUE_REQUIREMENT_PROTEINS_DAILY);
    }

    @Override
    public int getCarbsGoal() {
        return sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CARBS_DAILY, VALUE_REQUIREMENT_CARBS_DAILY);
    }

    @Override
    public int getFatsGoal() {
        return sharedPreferencesUtil.getInt(KEY_REQUIREMENT_FATS_DAILY, VALUE_REQUIREMENT_FATS_DAILY);
    }

    @Override
    public boolean isStartScreenWithRecipesSet() {
        return sharedPreferencesUtil.getBoolean(KEY_START_SCREEN_RECIPE, VALUE_FALSE);
    }

    @Override
    public boolean isStartScreenWithDrinksSet() {
        return sharedPreferencesUtil.getBoolean(KEY_START_SCREEN_LIQUIDS, VALUE_TRUE);
    }

    @Override
    public float getLiquidGoal() {
        return sharedPreferencesUtil.getFloat(KEY_REQUIREMENT_LIQUID_DAILY, VALUE_REQUIREMENT_LIQUID_DAILY);
    }

    @Override
    public float getVolumeForBottle() {
        return sharedPreferencesUtil.getFloat(KEY_BOTTLE_VOLUME, VALUE_BOTTLE_VOLUME);
    }

    @Override
    public float getVolumeForGlass() {
        return sharedPreferencesUtil.getFloat(KEY_GLASS_VOLUME, VALUE_GLASS_VOLUME);
    }

    @Override
    public boolean getOnboardingViewed(int onboardingTag) {
        switch (onboardingTag){
            case ONBOARDING_INGREDIENT_SEARCH:
                return sharedPreferencesUtil.getBoolean(KEY_ONBOARDING_INGREDIENT_SEARCH_STATUS, VALUE_FALSE);
            case ONBOARDING_DISPLAY_SETTINGS:
                return sharedPreferencesUtil.getBoolean(KEY_ONBOARDING_DISPLAY_SETTINGS_STATUS, VALUE_FALSE);
            case ONBOARDING_SUPPORT_OPTIONS:
                return sharedPreferencesUtil.getBoolean(KEY_ONBOARDING_SUPPORT_OPTIONS_STATUS, VALUE_FALSE);
            case ONBOARDING_SLIDE_OPTIONS:
                return sharedPreferencesUtil.getBoolean(KEY_ONBOARDING_SLIDE_OPTIONS_STATUS, VALUE_FALSE);
            case ONBOARDING_WELCOME:
                return sharedPreferencesUtil.getBoolean(KEY_ONBOARDING_WELCOME_STATUS, VALUE_FALSE);
            default:
                return VALUE_FALSE;
        }
    }

    @Override
    public void setOnboardingToViewed(int onboardingTag) {
        switch (onboardingTag){
            case ONBOARDING_INGREDIENT_SEARCH:
                sharedPreferencesUtil.setBoolean(KEY_ONBOARDING_INGREDIENT_SEARCH_STATUS, VALUE_TRUE);
                break;
            case ONBOARDING_DISPLAY_SETTINGS:
                sharedPreferencesUtil.setBoolean(KEY_ONBOARDING_DISPLAY_SETTINGS_STATUS, VALUE_TRUE);
                break;
            case ONBOARDING_SUPPORT_OPTIONS:
                sharedPreferencesUtil.setBoolean(KEY_ONBOARDING_SUPPORT_OPTIONS_STATUS, VALUE_TRUE);
                break;
            case ONBOARDING_SLIDE_OPTIONS:
                sharedPreferencesUtil.setBoolean(KEY_ONBOARDING_SLIDE_OPTIONS_STATUS, VALUE_TRUE);
                break;
            case ONBOARDING_WELCOME:
                sharedPreferencesUtil.setBoolean(KEY_ONBOARDING_WELCOME_STATUS, VALUE_TRUE);
                break;
        }
    }

}