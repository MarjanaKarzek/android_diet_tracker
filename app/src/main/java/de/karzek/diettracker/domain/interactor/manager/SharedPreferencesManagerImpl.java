package de.karzek.diettracker.domain.interactor.manager;

import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.data.repository.repositoryInterface.AllergenRepository;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.mapper.AllergenDomainMapper;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_SETTING_NUTRITION_DETAILS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_START_SCREEN_LIQUIDS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_START_SCREEN_RECIPE;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

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

}