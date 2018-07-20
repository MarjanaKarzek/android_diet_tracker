package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationIngredientItemViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationIngredientsTitleAndPortionsViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationItemAddViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationManualIngredientItemViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationMealsTitleViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationMealsViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationPhotoViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationPreparationStepItemAddViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationPreparationStepItemViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationPreparationStepTitleViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationRecipeDeleteViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationRecipeSaveViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.itemWrapper.IngredientSearchItemWrapper;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.viewHolder.IngredientSearchFailedViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.viewHolder.IngredientSearchFoundViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.viewHolder.IngredientSearchLoadingViewHolder;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class IngredientSearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<IngredientSearchItemWrapper> list;

    //TODO
    private IngredientSearchFailedViewHolder.OnStartGrocerySearchClickListener onStartGrocerySearchClickListener;
    private IngredientSearchFailedViewHolder.OnStartBarcodeScanClickListener onStartBarcodeScanClickListener;
    private IngredientSearchFailedViewHolder.OnDeleteIngredientClickListener onDeleteIngredientClickListener;

    public IngredientSearchListAdapter(IngredientSearchFailedViewHolder.OnStartGrocerySearchClickListener onStartGrocerySearchClickListener,
                                       IngredientSearchFailedViewHolder.OnStartBarcodeScanClickListener onStartBarcodeScanClickListener,
                                       IngredientSearchFailedViewHolder.OnDeleteIngredientClickListener onDeleteIngredientClickListener){
        list = new ArrayList<>();

        this.onStartGrocerySearchClickListener = onStartGrocerySearchClickListener;
        this.onStartBarcodeScanClickListener = onStartBarcodeScanClickListener;
        this.onDeleteIngredientClickListener = onDeleteIngredientClickListener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case IngredientSearchItemWrapper.ItemType.LOADING_INGREDIENT:
                return new IngredientSearchLoadingViewHolder(parent);
            case IngredientSearchItemWrapper.ItemType.FOUND_INGREDIENT:
                return new IngredientSearchFoundViewHolder(parent);
            case IngredientSearchItemWrapper.ItemType.FAILED_INGREDIENT:
                return new IngredientSearchFailedViewHolder(parent, onStartGrocerySearchClickListener, onStartBarcodeScanClickListener, onDeleteIngredientClickListener);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IngredientSearchLoadingViewHolder) {
            ((IngredientSearchLoadingViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof IngredientSearchFoundViewHolder) {
            ((IngredientSearchFoundViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof IngredientSearchFailedViewHolder) {
            ((IngredientSearchFailedViewHolder) holder).bind(list.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<IngredientSearchItemWrapper> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override public int getItemViewType(int position) {
        return list.get(position).getType();
    }

}
