package de.karzek.diettracker.presentation.search.grocery;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.search.grocery.adapter.viewHolder.GrocerySearchDrinkResultViewHolder;
import de.karzek.diettracker.presentation.search.grocery.adapter.viewHolder.GrocerySearchFoodResultViewHolder;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public interface GrocerySearchContract {

    interface View extends BaseView<Presenter> {

        void showGroceryDetails(int id);

        void updateFoodSearchResultList(ArrayList<GroceryDisplayModel> foods);

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
    }

    interface Presenter extends BasePresenter<View>,
            GrocerySearchFoodResultViewHolder.OnFoodSearchResultItemClickedListener,
            GrocerySearchDrinkResultViewHolder.OnGrocerySearchDrinkResultItemClickedListener,
            GrocerySearchDrinkResultViewHolder.OnGrocerySearchDrinkResultAddBottleClickedListener,
            GrocerySearchDrinkResultViewHolder.OnGrocerySearchDrinkResultAddGlassClickedListener {

        void getFavoriteGroceries(int type);

        void getGroceriesMatchingQuery(String query, int type);

    }
}
