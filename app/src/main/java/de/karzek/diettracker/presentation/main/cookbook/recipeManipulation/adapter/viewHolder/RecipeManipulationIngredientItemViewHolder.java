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
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeManipulationIngredientItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ingredient_summary) TextView summary;

    private final OnDeleteIngredientClickListener onDeleteIngredientClickListener;
    private final OnIngredientClickListener onIngredientClickListener;

    public RecipeManipulationIngredientItemViewHolder(ViewGroup viewGroup,
                                                      OnDeleteIngredientClickListener onDeleteIngredientClickListener,
                                                      OnIngredientClickListener onIngredientClickListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_man_ingredient, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onDeleteIngredientClickListener = onDeleteIngredientClickListener;
        this.onIngredientClickListener = onIngredientClickListener;
    }

    public void bind(RecipeManipulationViewItemWrapper item, int id) {
        summary.setText(formatSummary(item.getIngredientDisplayModel()));
        itemView.setTag(id);
    }

    private String formatSummary(IngredientDisplayModel ingredient){
        String amount = StringUtils.formatFloat(ingredient.getAmount());
        return amount + ingredient.getUnit().getName() + " " + ingredient.getGrocery().getName();
    }

    @OnClick(R.id.action_delete) public void onDeleteIngredientClicked() {
        onDeleteIngredientClickListener.onDeleteIngredientClicked((int) itemView.getTag());
    }

    @OnClick(R.id.item_layout) public void onIngredientClicked() {
        onIngredientClickListener.onIngredientClicked((int) itemView.getTag());
    }

    public interface OnDeleteIngredientClickListener {
        void onDeleteIngredientClicked(int id);
    }

    public interface OnIngredientClickListener {
        void onIngredientClicked(int id);
    }

}
