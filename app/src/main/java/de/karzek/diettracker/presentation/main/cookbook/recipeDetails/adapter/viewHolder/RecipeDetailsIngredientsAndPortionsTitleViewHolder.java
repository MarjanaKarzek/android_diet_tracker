package de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.itemWrapper.RecipeDetailsViewItemWrapper;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeDetailsIngredientsAndPortionsTitleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_details_ingredients_title)
    TextView title;
    @BindView(R.id.expandable_nutrition_details_action)
    ImageButton arrow;

    private Context context;
    private boolean expanded = false;

    private OnExpandNutritionDetailsViewClickListener onExpandNutritionDetailsViewClickListener;

    public RecipeDetailsIngredientsAndPortionsTitleViewHolder(ViewGroup viewGroup,
                                                              OnExpandNutritionDetailsViewClickListener onExpandNutritionDetailsViewClickListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_details_ingredients_title_and_portions, viewGroup, false));
        ButterKnife.bind(this, itemView);

        context = viewGroup.getContext();

        this.onExpandNutritionDetailsViewClickListener = onExpandNutritionDetailsViewClickListener;
    }

    public void bind(RecipeDetailsViewItemWrapper item) {
        title.setText(item.getTitle());
    }

    @OnClick(R.id.expandable_nutrition_details_action) void onExpandNutritionDetailsViewClicked(){
        expanded = !expanded;
        if(expanded)
            arrow.setImageDrawable(context.getDrawable(R.drawable.ic_expand_less_primary_text));
        else
            arrow.setImageDrawable(context.getDrawable(R.drawable.ic_expand_more_primary_text));
        onExpandNutritionDetailsViewClickListener.onExpandNutritionDetailsViewClicked();
    }

    public interface OnExpandNutritionDetailsViewClickListener {
        void onExpandNutritionDetailsViewClicked();
    }

}
