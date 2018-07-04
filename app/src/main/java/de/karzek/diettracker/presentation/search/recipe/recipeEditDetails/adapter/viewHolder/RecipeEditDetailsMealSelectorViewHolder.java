package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.itemWrapper.RecipeEditDetailsViewItemWrapper;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeEditDetailsMealSelectorViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.spinner_meal)
    Spinner spinner;

    private OnMealItemSelectedListener onMealItemSelectedListener;

    private Context context;

    public RecipeEditDetailsMealSelectorViewHolder(ViewGroup viewGroup,
                                                   OnMealItemSelectedListener onMealItemSelectedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_edit_details_meal_selector, viewGroup, false));
        ButterKnife.bind(this, itemView);

        context = viewGroup.getContext();
        this.onMealItemSelectedListener = onMealItemSelectedListener;
    }

    public void bind(RecipeEditDetailsViewItemWrapper item) {
        ArrayList<String> mealNames = new ArrayList<>();
        for (MealDisplayModel meal : item.getMeals())
            mealNames.add(meal.getName());

        ArrayAdapter<String> mealAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, mealNames);
        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(mealAdapter);
        spinner.setSelection(item.getSelectedMeal());
    }

    @OnItemSelected(R.id.spinner_meal) public void onItemSelected(){
        onMealItemSelectedListener.onMealItemSelected(spinner.getSelectedItemPosition());
    }

    public interface OnMealItemSelectedListener {
        void onMealItemSelected(int id);
    }

}
