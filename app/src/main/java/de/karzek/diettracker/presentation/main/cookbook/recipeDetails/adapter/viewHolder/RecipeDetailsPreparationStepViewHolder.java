package de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.itemWrapper.RecipeDetailsViewItemWrapper;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeDetailsPreparationStepViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.preparation_step_number)
    TextView number;
    @BindView(R.id.preparation_step_text)
    TextView text;

    public RecipeDetailsPreparationStepViewHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_details_preparation_step, viewGroup, false));
        ButterKnife.bind(this, itemView);
    }

    public void bind(RecipeDetailsViewItemWrapper item) {
        number.setText(StringUtils.formatInt(item.getPreparationStepDisplayModel().getStepNo()));
        text.setText(item.getPreparationStepDisplayModel().getDescription());
    }

}
