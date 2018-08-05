package de.karzek.diettracker.domain.interactor.manager.managerInterface;

import android.support.annotation.IntDef;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.common.BaseObservableUseCase;
import de.karzek.diettracker.domain.common.BaseUseCase;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import io.reactivex.disposables.Disposable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface SharedPreferencesManager {

    void initializeStandardValues();

    ArrayList<Integer> getAllergenIds();

    void putAllergenIds(String allergenSelection);

    String getNutritionDetailsSetting();

    void setNutritionDetailsSetting(boolean checked);

    void setStartScreenRecipesSetting(boolean checked);

    void setStartScreenLiquidsSetting(boolean checked);

    int getCaloriesGoal();

    int getProteinsGoal();

    int getCarbsGoal();

    int getFatsGoal();

    boolean isStartScreenWithRecipesSet();

    boolean isStartScreenWithDrinksSet();

    float getLiquidGoal();

    float getVolumeForBottle();

    float getVolumeForGlass();

    boolean getOnboardingViewed(int onboardingTag);

    void setOnboardingToViewed(int onboardingTag);

    void setInt(String key, Integer value);

    void setFloat(String key, Float value);

    int getInt(String keyRequirementCaloriesDaily, int valueRequirementCaloriesDaily);

    float getFloat(String keyBottleVolume, float valueBottleVolume);

    boolean getBoolean(String keyStartScreenRecipe, boolean valueTrue);
}