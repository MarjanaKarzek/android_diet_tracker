package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals.adapter.viewHolder.MealViewHolder;
import de.karzek.diettracker.presentation.model.MealDisplayModel;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public interface EditMealsDialogContract {

    interface View extends BaseView<Presenter> {

        void updateRecyclerView(ArrayList<MealDisplayModel> allergens, HashMap<Integer, Boolean> mealStatus);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter<View>,
            MealViewHolder.OnItemCheckedChangeListener {

        void setSelectedMealList(ArrayList<MealDisplayModel> mealList);

        ArrayList<MealDisplayModel> getSelectedMeals();

        void onResetSelectionClicked();

    }
}
