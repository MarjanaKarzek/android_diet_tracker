package de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog.adapter.viewHolder.RecipeFilterOptionViewHolder;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public interface RecipeFilterOptionsDialogContract {

    interface View extends BaseView<Presenter> {

        void updateRecyclerView(ArrayList<String> options, HashMap<String, Boolean> optionStatus);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter<View>,
            RecipeFilterOptionViewHolder.OnItemCheckedChangeListener {

        ArrayList<String> getSelectedFilterOptions();

        void onResetSelectionClicked();

        void setFilterOptions(ArrayList<String> filterOptions);

    }
}
