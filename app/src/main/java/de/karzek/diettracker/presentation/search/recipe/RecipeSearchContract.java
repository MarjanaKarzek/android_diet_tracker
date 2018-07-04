package de.karzek.diettracker.presentation.search.recipe;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.search.grocery.adapter.viewHolder.GrocerySearchDrinkResultViewHolder;
import de.karzek.diettracker.presentation.search.grocery.adapter.viewHolder.GrocerySearchFoodResultViewHolder;
import de.karzek.diettracker.presentation.search.recipe.adapter.viewHolder.RecipeSearchResultViewHolder;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public interface RecipeSearchContract {

    interface View extends BaseView<Presenter> {

        void showRecipeDetails(int id);

        void updateRecipeSearchResultList(ArrayList<RecipeDisplayModel> recipes);

        void showPlaceholder();

        void hidePlaceholder();

        void showLoading();

        void hideLoading();

        void showQueryWithoutResultPlaceholder();

        void hideQueryWithoutResultPlaceholder();

        void hideRecyclerView();

        void showRecyclerView();

        void finishActivity();

        String getSelectedDate();

        int getSelectedMeal();

    }

    interface Presenter extends BasePresenter<View>,
            RecipeSearchResultViewHolder.OnRecipeItemClickedListener,
            RecipeSearchResultViewHolder.OnRecipeAddPortionClickedListener {

        void getFavoriteRecipes(int selectedMeal);

        void getRecipesMatchingQuery(String query);

    }
}
