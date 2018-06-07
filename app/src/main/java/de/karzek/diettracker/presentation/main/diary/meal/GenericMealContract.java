package de.karzek.diettracker.presentation.main.diary.meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.diary.meal.adapter.viewHolder.DiaryEntryViewHolder;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public interface GenericMealContract {

    interface View extends BaseView<Presenter> {

        void showRecyclerView();

        void hideRecyclerView();

        void showGroceryListPlaceholder();

        void hideGroceryListPlaceholder();

        void showNutritionDetails(String value);

        void setNutritionMaxValues(HashMap<String, Long> values);

        void updateNutritionDetails(HashMap<String, Float> values);

        void updateGroceryList(ArrayList<DiaryEntryDisplayModel> diaryEntries);

        void setSelectedDate(String date);

        String getSelectedDate();

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter<View>, DiaryEntryViewHolder.OnDiaryEntryItemClickedListener {

        void setMeal(String meal);

        void updateDiaryEntries(String date);

    }
}