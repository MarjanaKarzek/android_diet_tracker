package de.karzek.diettracker.presentation.main.cookbook;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.cookbook.adapter.viewHolder.RecipeSearchResultViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog.RecipeFilterOptionsDialog;
import de.karzek.diettracker.presentation.main.cookbook.dialog.sortOptionsDialog.RecipeSortOptionsDialog;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public interface CookbookContract {

    interface View extends BaseView<Presenter>,
            RecipeFilterOptionsDialog.FilterOptionsSelectedDialogListener,
            RecipeSortOptionsDialog.SortOptionSelectedDialogListener {

        void hideLoading();

        void showLoading();

        void showPlaceholder();

        void updateRecyclerView(ArrayList<RecipeDisplayModel> recipes);

        void hideRecyclerView();

        void hidePlaceholder();

        void startRecipeDetailsActivity(int id);

        void startEditRecipe(int id);

        void showConfirmRecipeDeletionDialog(int id);

        void hideQueryWithoutResultPlaceholder();

        void showRecyclerView();

        void showQueryWithoutResultPlaceholder();

        void openFilterOptionsDialog(ArrayList<String> filterOptions);

        void openSortOptionsDialog(String sortOption, boolean asc);

    }

    interface Presenter extends BasePresenter<View>,
            RecipeSearchResultViewHolder.OnRecipeItemClickedListener,
            RecipeSearchResultViewHolder.OnRecipeAddPortionClickedListener,
            RecipeSearchResultViewHolder.OnRecipeEditClickedListener,
            RecipeSearchResultViewHolder.OnRecipeDeleteClickedListener {

        void getRecipesMatchingQuery(String query);

        void onDeleteRecipeConfirmed(int id);

        void onFilterOptionSelected();

        void onSortOptionSelected();

        void filterRecipesBy(ArrayList<String> filterOptions);

        void sortRecipesBy(String sortOption, boolean asc);
    }
}
