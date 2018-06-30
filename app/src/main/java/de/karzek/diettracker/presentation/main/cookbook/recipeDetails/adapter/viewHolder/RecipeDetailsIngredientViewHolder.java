package de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.itemWrapper.RecipeDetailsViewItemWrapper;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeDetailsIngredientViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ingredient_summary)
    TextView summary;

    public RecipeDetailsIngredientViewHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_details_ingredient, viewGroup, false));
        ButterKnife.bind(this, itemView);
    }

    public void bind(RecipeDetailsViewItemWrapper item) {
        summary.setText(formatSummary(item.getIngredientDisplayModel()));
    }

    private String formatSummary(IngredientDisplayModel ingredient){
        String amount = StringUtils.formatFloat(ingredient.getAmount());
        return amount + ingredient.getUnit().getName() + " " + ingredient.getGrocery().getName();
    }

}
