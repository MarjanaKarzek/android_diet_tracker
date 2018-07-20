package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.viewHolder;

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
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.itemWrapper.IngredientSearchItemWrapper;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.ManualIngredientDisplayModel;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class IngredientSearchLoadingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ingredient_summary)
    TextView summary;

    public IngredientSearchLoadingViewHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_ingredient_search_loading, viewGroup, false));
        ButterKnife.bind(this, itemView);
    }

    public void bind(IngredientSearchItemWrapper item) {
        summary.setText(formatSummary(item.getIngredientDisplayModel()));
        itemView.setTag(item.getIngredientDisplayModel().getId());
    }

    private String formatSummary(IngredientDisplayModel ingredient){
        if (ingredient instanceof ManualIngredientDisplayModel) {
            String amount = StringUtils.formatFloat(ingredient.getAmount());
            return amount + ingredient.getUnit().getName() + " " + ((ManualIngredientDisplayModel) ingredient).getGroceryQuery();
        } else {
            String amount = StringUtils.formatFloat(ingredient.getAmount());
            return amount + ingredient.getUnit().getName() + " " + ingredient.getGrocery().getName();
        }
    }

}
