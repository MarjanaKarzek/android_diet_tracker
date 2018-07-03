package de.karzek.diettracker.presentation.main.diary.drink;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.diary.meal.adapter.diaryEntryList.viewHolder.DiaryEntryViewHolder;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public interface GenericDrinkContract {

    interface View extends BaseView<Presenter> {

        void showNutritionDetails(String value);

        void showLoading();

        void hideLoading();

        String getSelectedDate();

        void showRecyclerView();

        void hideGroceryListPlaceholder();

        void hideRecyclerView();

        void showGroceryListPlaceholder();

        void updateGroceryList(ArrayList<DiaryEntryDisplayModel> displayModels);

        void hideExpandOption();

        void showExpandOption();

        void setLiquidStatus(DiaryEntryDisplayModel displayModel, float waterGoal);

        void addToLiquidStatus(ArrayList<DiaryEntryDisplayModel> displayModels, float waterGoal);

        void refreshLiquidStatus();

        void setNutritionMaxValues(HashMap<String, Long> maxValues);

        void updateNutritionDetails(HashMap<String, Float> values);

        void startEditMode(int id);
    }

    interface Presenter extends BasePresenter<View>,
            DiaryEntryViewHolder.OnDiaryEntryItemClickedListener,
            DiaryEntryViewHolder.OnDeleteDiaryEntryItemListener,
            DiaryEntryViewHolder.OnEditDiaryEntryItemListener {

        void updateLiquidStatus(String selectedDate);

        void addFavoriteDrinkClicked(String selectedDate);

        void addGlassWaterClicked(String selectedDate);

        void addBottleWaterClicked(String selectedDate);

        void updateAmountOfWater(Float amount, String selectedDate);

        void addAmountOfWater(Float aFloat, String selectedDate);
    }
}
