package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationIngredientsTitleAndPortionsViewHolder;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.itemWrapper.RecipeEditDetailsViewItemWrapper;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeEditDetailsIngredientsAndPortionsTitleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.value_portions)
    EditText portions;
    @BindView(R.id.expandable_nutrition_details_action)
    ImageButton arrow;

    private Context context;
    private boolean expanded = false;

    private final OnPortionChangedListener onPortionChangedListener;
    private final OnExpandNutritionDetailsViewClickListener onExpandNutritionDetailsViewClickListener;

    public RecipeEditDetailsIngredientsAndPortionsTitleViewHolder(ViewGroup viewGroup,
                                                                  OnPortionChangedListener onPortionChangedListener,
                                                                  OnExpandNutritionDetailsViewClickListener onExpandNutritionDetailsViewClickListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_edit_details_ingredients_title_and_portions, viewGroup, false));
        ButterKnife.bind(this, itemView);
        context = viewGroup.getContext();

        this.onExpandNutritionDetailsViewClickListener = onExpandNutritionDetailsViewClickListener;
        this.onPortionChangedListener = onPortionChangedListener;

        setupPortionsChangedListener();
    }

    private void setupPortionsChangedListener() {
        portions.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onPortionChangedListener.onPortionChanged(Float.valueOf(portions.getText().toString()));
                }
                return true;
            }
        });
    }

    public void bind(RecipeEditDetailsViewItemWrapper item) {
        portions.setText(StringUtils.formatFloat(item.getPortions()));
    }

    @OnClick(R.id.remove_portion) public void onRemovePortionClicked() {
        float newValue = Float.valueOf(portions.getText().toString()) -1;
        portions.setText(StringUtils.formatFloat(newValue));
        onPortionChangedListener.onPortionChanged(newValue);
    }

    @OnClick(R.id.add_portion) public void onAddPortionClicked() {
        float newValue = Float.valueOf(portions.getText().toString()) +1;
        portions.setText(StringUtils.formatFloat(newValue));
        onPortionChangedListener.onPortionChanged(newValue);
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

    public interface OnPortionChangedListener {
        void onPortionChanged(float portion);
    }

}
