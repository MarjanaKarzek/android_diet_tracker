package de.karzek.diettracker.presentation.main.settings;

import android.widget.EditText;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.settings.adapter.viewHolder.SettingsMealViewHolder;
import de.karzek.diettracker.presentation.main.settings.dialog.EditMealTimeDialog;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public interface SettingsContract {

    interface View extends BaseView<Presenter>,
            EditMealTimeDialog.SaveMealTimeDialogListener {

        void clearFocusOfView(EditText view);

        void setupMealRecyclerView(ArrayList<MealDisplayModel> meals);

        void setupAllergenTextView(ArrayList<AllergenDisplayModel> allergens);

        void fillSettingsOptions(SharedPreferencesUtil sharedPreferencesUtil);

        void showMealEditTimeDialog(MealDisplayModel mealDisplayModel);

        void updateRecyclerView();
    }

    interface Presenter extends BasePresenter<View>,
            SettingsMealViewHolder.OnMealNameChangedListener,
            SettingsMealViewHolder.OnMealEditTimeClickedListener,
            SettingsMealViewHolder.OnDeleteMealClickedListener {

        void updateSharedPreferenceIntValue(String key, Integer value);

        void updateSharedPreferenceFloatValue(String key, Float value);

        void updateMealTime(int id, String startTime, String endTime);
    }
}
