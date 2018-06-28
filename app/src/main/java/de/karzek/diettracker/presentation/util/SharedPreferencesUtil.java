package de.karzek.diettracker.presentation.util;

import android.content.SharedPreferences;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class SharedPreferencesUtil {
    private final SharedPreferences sharedPreferences;

    public static final String KEY_APP_INITIALIZED = "KEY_APP_INITIALIZED";

    public static final String KEY_SETTING_NUTRITION_DETAILS = "KEY_SETTING_NUTRITION_DETAILS";
    public static final String VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY = "CALORIES_ONLY";
    public static final String VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS = "CALORIES_AND_MACROS";

    public static final String KEY_REQUIREMENT_CALORIES_DAILY = "KEY_REQUIREMENT_CALORIES_DAILY";
    public static final String KEY_REQUIREMENT_PROTEINS_DAILY = "KEY_REQUIREMENT_PROTEINS_DAILY";
    public static final String KEY_REQUIREMENT_CARBS_DAILY = "KEY_REQUIREMENT_CARBS_DAILY";
    public static final String KEY_REQUIREMENT_FATS_DAILY = "KEY_REQUIREMENT_FATS_DAILY";
    public static final int VALUE_REQUIREMENT_CALORIES_DAILY = 2000;
    public static final int VALUE_REQUIREMENT_PROTEINS_DAILY = 60;
    public static final int VALUE_REQUIREMENT_CARBS_DAILY = 230;
    public static final int VALUE_REQUIREMENT_FATS_DAILY = 65;

    public static final String KEY_REQUIREMENT_LIQUID_DAILY = "KEY_REQUIREMENT_LIQUID_DAILY";
    public static final float VALUE_REQUIREMENT_LIQUID_DAILY = 2000.0f;

    public static final String KEY_BOTTLE_VOLUME = "KEY_BOTTLE_VOLUME";
    public static final String KEY_GLASS_VOLUME = "KEY_GLASS_VOLUME";
    public static final float VALUE_BOTTLE_VOLUME = 500.0f;
    public static final float VALUE_GLASS_VOLUME = 200.0f;

    public static final String KEY_START_SCREEN_RECIPE = "KEY_START_SCREEN_RECIPE";
    public static final String KEY_START_SCREEN_LIQUIDS = "KEY_START_SCREEN_LIQUIDS";

    public static final String KEY_ALLERGENS = "KEY_ALLERGENS";
    private static final String VALUE_ALLERGENS = "";

    public static final boolean VALUE_TRUE = true;
    public static final boolean VALUE_FALSE = false;

    public SharedPreferencesUtil(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean initialiseStandardValues() {
        try {
            sharedPreferences
                    .edit()
                    .putBoolean(KEY_APP_INITIALIZED, VALUE_TRUE)
                    .putString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS)
                    .putInt(KEY_REQUIREMENT_CALORIES_DAILY, VALUE_REQUIREMENT_CALORIES_DAILY)
                    .putInt(KEY_REQUIREMENT_PROTEINS_DAILY, VALUE_REQUIREMENT_PROTEINS_DAILY)
                    .putInt(KEY_REQUIREMENT_CARBS_DAILY, VALUE_REQUIREMENT_CARBS_DAILY)
                    .putInt(KEY_REQUIREMENT_FATS_DAILY, VALUE_REQUIREMENT_FATS_DAILY)
                    .putFloat(KEY_BOTTLE_VOLUME, VALUE_BOTTLE_VOLUME)
                    .putFloat(KEY_GLASS_VOLUME, VALUE_GLASS_VOLUME)
                    .putFloat(KEY_REQUIREMENT_LIQUID_DAILY, VALUE_REQUIREMENT_LIQUID_DAILY)
                    .putBoolean(KEY_START_SCREEN_RECIPE, VALUE_TRUE)
                    .putBoolean(KEY_START_SCREEN_LIQUIDS, VALUE_TRUE)
                    .putString(KEY_ALLERGENS, VALUE_ALLERGENS)
                    .apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void setString(String key, String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    public void setInt(String key, int value) {
        sharedPreferences.edit()
                .putInt(key, value)
                .apply();
    }

    public void setFloat(String key, float value) {
        sharedPreferences.edit()
                .putFloat(key, value)
                .apply();
    }

    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

}
