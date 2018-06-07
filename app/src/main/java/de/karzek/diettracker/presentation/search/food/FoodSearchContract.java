package de.karzek.diettracker.presentation.search.food;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.model.FavoriteGroceryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.search.food.adapter.viewHolder.FoodSearchResultViewHolder;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public interface FoodSearchContract {

    interface View extends BaseView<Presenter> {

        void showFoodDetails(int id);

        void updateFoodSearchResultList(ArrayList<GroceryDisplayModel> foods);

        void showPlaceholder();

        void hidePlaceholder();

        void showLoading();

        void hideLoading();

        void showQueryWithoutResultPlaceholder();

        void hideQueryWithoutResultPlaceholder();

    }

    interface Presenter extends BasePresenter<View>, FoodSearchResultViewHolder.OnFoodSearchResultItemClickedListener {

        void getFoodsMatchingQuery(String query);

    }
}