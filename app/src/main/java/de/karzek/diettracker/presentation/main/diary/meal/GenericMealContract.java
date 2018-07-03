package de.karzek.diettracker.presentation.main.diary.meal;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.diary.meal.adapter.diaryEntryList.viewHolder.DiaryEntryViewHolder;
import de.karzek.diettracker.presentation.main.diary.meal.adapter.favoriteRecipeList.viewHolder.FavoriteRecipeViewHolder;
import de.karzek.diettracker.presentation.main.diary.meal.dialog.MoveDiaryEntryDialog;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public interface GenericMealContract {

    interface View extends BaseView<Presenter>, MoveDiaryEntryDialog.MealSelectedInDialogListener {

        void showGroceryRecyclerView();

        void hideGroceryRecyclerView();

        void showGroceryListPlaceholder();

        void hideGroceryListPlaceholder();

        void showNutritionDetails(String value);

        void setNutritionMaxValues(HashMap<String, Long> values);

        void updateNutritionDetails(HashMap<String, Float> values);

        void updateGroceryList(ArrayList<DiaryEntryDisplayModel> diaryEntries);

        void setSelectedDate(String date);

        void updateRecipeList(ArrayList<RecipeDisplayModel> recipes);

        String getSelectedDate();

        void showLoading();

        void hideLoading();

        void refreshRecyclerView();

        void showMoveDiaryEntryDialog(int id, ArrayList<MealDisplayModel> allMeals, ArrayList<String> meals);

        void startEditMode(int id);

        void showRecipeRecyclerView();

        void hideRecipeRecyclerView();

    }

    interface Presenter extends BasePresenter<View>,
            DiaryEntryViewHolder.OnDiaryEntryItemClickedListener,
            DiaryEntryViewHolder.OnDeleteDiaryEntryItemListener,
            DiaryEntryViewHolder.OnMoveDiaryEntryItemListener,
            DiaryEntryViewHolder.OnEditDiaryEntryItemListener,
            FavoriteRecipeViewHolder.OnFavoriteRecipeItemClickedListener {

        void setMeal(String meal);

        void updateListItems(String date);

        void moveDiaryItemToMeal(int id, MealDisplayModel meal);

    }
}
