package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.itemWrapper.RecipeEditDetailsViewItemWrapper;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeEditDetailsIngredientViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ingredient_summary)
    TextView summary;

    private OnDeleteIngredientClickListener onDeleteIngredientClickListener;

    public RecipeEditDetailsIngredientViewHolder(ViewGroup viewGroup,
                                                 OnDeleteIngredientClickListener onDeleteIngredientClickListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_ingredient, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onDeleteIngredientClickListener = onDeleteIngredientClickListener;
    }

    public void bind(RecipeEditDetailsViewItemWrapper item, int id) {
        summary.setText(formatSummary(item.getIngredientDisplayModel(), item.getPortions()));
        itemView.setTag(id);
    }

    private String formatSummary(IngredientDisplayModel ingredient, float portions){
        String amount = StringUtils.formatFloat(ingredient.getAmount() * portions);
        return amount + ingredient.getUnit().getName() + " " + ingredient.getGrocery().getName();
    }

    @OnClick(R.id.action_delete) public void onDeleteIngredientClicked() {
        onDeleteIngredientClickListener.onDeleteIngredientClicked((int) itemView.getTag());
    }

    public interface OnDeleteIngredientClickListener {
        void onDeleteIngredientClicked(int id);
    }

}
