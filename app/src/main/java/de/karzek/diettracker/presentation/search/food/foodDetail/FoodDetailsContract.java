package de.karzek.diettracker.presentation.search.food.foodDetail;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public interface FoodDetailsContract {

    interface View extends BaseView<Presenter> {

        void showNutritionDetails(String value);

        void fillGroceryDetails(GroceryDisplayModel grocery);

        void initializeServingsSpinner(ArrayList<UnitDisplayModel> defaultUnits, ArrayList<ServingDisplayModel> servings);

    }

    interface Presenter extends BasePresenter<View> {

        void setGroceryId(int groceryId);

    }
}
