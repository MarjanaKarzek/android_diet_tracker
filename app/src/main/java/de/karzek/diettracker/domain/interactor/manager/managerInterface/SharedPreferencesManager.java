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
}