package de.karzek.diettracker.presentation.search.food;

import android.view.View;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import de.karzek.diettracker.presentation.search.food.adapter.viewHolder.FoodSearchResultViewHolder;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class FoodSearchPresenter implements FoodSearchContract.Presenter {

    private FoodSearchContract.View view;

    @Override
    public void start() {

    }

    @Override
    public void setView(FoodSearchContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {

    }


    @Override
    public void getFoodsMatchingName(String query) {
        GroceryDisplayModel model1 = new GroceryDisplayModel(0,1,"Paprika",1,1,1,1,1,new ArrayList<AllergenDisplayModel>(),new ArrayList<ServingDisplayModel>());
        GroceryDisplayModel model2 = new GroceryDisplayModel(1,1,"Fleisch",1,1,1,1,1,new ArrayList<AllergenDisplayModel>(),new ArrayList<ServingDisplayModel>());
        GroceryDisplayModel model3 = new GroceryDisplayModel(2,1,"Milch",1,1,1,1,1,new ArrayList<AllergenDisplayModel>(),new ArrayList<ServingDisplayModel>());
        GroceryDisplayModel model4 = new GroceryDisplayModel(3,1,"Ei",1,1,1,1,1,new ArrayList<AllergenDisplayModel>(),new ArrayList<ServingDisplayModel>());

        ArrayList<GroceryDisplayModel> foods = new ArrayList<>();
        foods.add(model1);
        foods.add(model2);
        foods.add(model3);
        foods.add(model4);

        view.updateFoodSearchResultList(foods);
    }

    @Override
    public void onItemClicked(int id) {
        view.showFoodDetails(id);
    }
}
