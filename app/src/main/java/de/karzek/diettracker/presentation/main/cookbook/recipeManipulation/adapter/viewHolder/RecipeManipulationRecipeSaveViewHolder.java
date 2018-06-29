package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeManipulationRecipeSaveViewHolder extends RecyclerView.ViewHolder {

    private final OnSaveRecipeClickedListener onSaveRecipeClickedListener;

    public RecipeManipulationRecipeSaveViewHolder(ViewGroup viewGroup,
                                                  OnSaveRecipeClickedListener onSaveRecipeClickedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_man_save, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onSaveRecipeClickedListener = onSaveRecipeClickedListener;
    }

    public void bind(RecipeManipulationViewItemWrapper item) {

    }

    @OnClick(R.id.add_recipe) public void onDeleteImageClicked() {
        onSaveRecipeClickedListener.onSaveRecipeClicked();
    }

    public interface OnSaveRecipeClickedListener {
        void onSaveRecipeClicked();
    }

}
