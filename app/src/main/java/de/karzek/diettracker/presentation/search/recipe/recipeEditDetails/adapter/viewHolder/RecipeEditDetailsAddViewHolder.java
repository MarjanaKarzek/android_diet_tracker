package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.itemWrapper.RecipeEditDetailsViewItemWrapper;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeEditDetailsAddViewHolder extends RecyclerView.ViewHolder {

    private final OnSaveRecipeClickedListener onSaveRecipeClickedListener;

    @BindView(R.id.add_recipe)
    Button addRecipe;

    public RecipeEditDetailsAddViewHolder(ViewGroup viewGroup,
                                          OnSaveRecipeClickedListener onSaveRecipeClickedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_save, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onSaveRecipeClickedListener = onSaveRecipeClickedListener;
    }

    public void bind(RecipeEditDetailsViewItemWrapper item) {
        addRecipe.setText(R.string.button_add);
    }

    @OnClick(R.id.add_recipe) public void onAddRecipeToDiaryClicked() {
        onSaveRecipeClickedListener.onSaveRecipeClicked();
    }

    public interface OnSaveRecipeClickedListener {
        void onSaveRecipeClicked();
    }

}
