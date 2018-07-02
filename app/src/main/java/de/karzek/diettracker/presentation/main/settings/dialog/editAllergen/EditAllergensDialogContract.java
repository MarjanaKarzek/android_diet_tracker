package de.karzek.diettracker.presentation.main.settings.dialog.editAllergen;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.settings.adapter.viewHolder.SettingsMealViewHolder;
import de.karzek.diettracker.presentation.main.settings.dialog.editAllergen.adapter.viewHolder.AllergenViewHolder;
import de.karzek.diettracker.presentation.main.settings.dialog.editMealTime.EditMealTimeDialog;
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
public interface EditAllergensDialogContract {

    interface View extends BaseView<Presenter> {

        void updateRecyclerView(ArrayList<AllergenDisplayModel> allergens, HashMap<Integer, Boolean> allergenStatus);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter<View>,
            AllergenViewHolder.OnItemCheckedChangeListener {

        void saveAllergenSelection();

        void onResetSelectionClicked();

    }
}
