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
public class RecipeManipulationItemAddViewHolder extends RecyclerView.ViewHolder {

    private final OnAddManualIngredientClickListener onAddManualIngredientClickListener;
    private final OnStartGrocerySearchClickListener onStartGrocerySearchClickListener;
    private final OnStartBarcodeScanClickListener onStartBarcodeScanClickListener;

    public RecipeManipulationItemAddViewHolder(ViewGroup viewGroup,
                                               OnAddManualIngredientClickListener onAddManualIngredientClickListener,
                                               OnStartGrocerySearchClickListener onStartGrocerySearchClickListener,
                                               OnStartBarcodeScanClickListener onStartBarcodeScanClickListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_man_ingredient_add, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onAddManualIngredientClickListener = onAddManualIngredientClickListener;
        this.onStartGrocerySearchClickListener = onStartGrocerySearchClickListener;
        this.onStartBarcodeScanClickListener = onStartBarcodeScanClickListener;
    }

    public void bind(RecipeManipulationViewItemWrapper item) {

    }

    @OnClick(R.id.add_ingredient) public void onAddManualIngredientClicked() {
        onAddManualIngredientClickListener.onAddManualIngredientClicked();
    }

    @OnClick(R.id.action_search) public void onStartGrocerySearchClicked() {
        onStartGrocerySearchClickListener.onStartGrocerySearchClicked();
    }

    @OnClick(R.id.action_barcode_search) public void onStartBarcodeScanClicked() {
        onStartBarcodeScanClickListener.onStartBarcodeScanClicked();
    }

    public interface OnAddManualIngredientClickListener {
        void onAddManualIngredientClicked();
    }

    public interface OnStartGrocerySearchClickListener {
        void onStartGrocerySearchClicked();
    }

    public interface OnStartBarcodeScanClickListener {
        void onStartBarcodeScanClicked();
    }

}
