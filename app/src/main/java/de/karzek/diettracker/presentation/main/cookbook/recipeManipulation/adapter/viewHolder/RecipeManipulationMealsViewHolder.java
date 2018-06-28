package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;
import de.karzek.diettracker.presentation.model.MealDisplayModel;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeManipulationMealsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_meals) TextView mealList;

    private final OnEditMealsClickedListener onEditMealsClickedListener;

    public RecipeManipulationMealsViewHolder(ViewGroup viewGroup,
                                             OnEditMealsClickedListener onEditMealsClickedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_man_meal_list, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onEditMealsClickedListener = onEditMealsClickedListener;
    }

    public void bind(RecipeManipulationViewItemWrapper item) {
        String meals = "";

        for(int i=0; i < item.getMeals().size(); i++){
            meals += item.getMeals().get(i).getName();
            if(i < item.getMeals().size()-1)
                meals += ", ";
        }

        if (item.getMeals().size() > 0)
            mealList.setText(meals);
    }

    @OnClick(R.id.text_meals) public void onEditMealsClicked() {
        onEditMealsClickedListener.onEditMealsClicked();
    }

    public interface OnEditMealsClickedListener {
        void onEditMealsClicked();
    }

}
