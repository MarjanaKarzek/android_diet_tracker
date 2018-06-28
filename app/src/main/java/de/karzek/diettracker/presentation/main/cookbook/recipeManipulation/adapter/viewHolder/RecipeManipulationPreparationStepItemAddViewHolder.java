package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
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
public class RecipeManipulationPreparationStepItemAddViewHolder extends RecyclerView.ViewHolder {

    private final OnAddPreparationStepClickedListener onAddPreparationStepClickedListener;

    public RecipeManipulationPreparationStepItemAddViewHolder(ViewGroup viewGroup,
                                                              OnAddPreparationStepClickedListener onAddPreparationStepClickedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_man_preparation_step_add, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onAddPreparationStepClickedListener = onAddPreparationStepClickedListener;
    }

    public void bind(RecipeManipulationViewItemWrapper item) {
    }

    @OnClick(R.id.add_preparation_step) public void onAddPreparationStepClickedListener() {
        onAddPreparationStepClickedListener.onAddPreparationStepClicked();
    }

    public interface OnAddPreparationStepClickedListener {
        void onAddPreparationStepClicked();
    }

}
