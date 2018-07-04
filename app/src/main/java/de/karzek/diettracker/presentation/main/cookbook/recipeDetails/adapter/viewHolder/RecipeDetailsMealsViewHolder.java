package de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.itemWrapper.RecipeDetailsViewItemWrapper;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeDetailsMealsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_meals)
    TextView mealList;

    public RecipeDetailsMealsViewHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_details_meal_list, viewGroup, false));
        ButterKnife.bind(this, itemView);
    }

    public void bind(RecipeDetailsViewItemWrapper item) {
        String meals = "";

        for (int i = 0; i < item.getMeals().size(); i++) {
            meals += item.getMeals().get(i).getName();
            if (i < item.getMeals().size() - 1)
                meals += ", ";
        }

        if (item.getMeals().size() > 0)
            mealList.setText(meals);
    }

}
