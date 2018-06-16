package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationIngredientsTitleAndPortionsViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationPhotoViewHolder;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchContract;
import de.karzek.diettracker.presentation.search.grocery.adapter.itemWrapper.GrocerySearchResultItemWrapper;
import de.karzek.diettracker.presentation.search.grocery.adapter.viewHolder.GrocerySearchDrinkResultViewHolder;
import de.karzek.diettracker.presentation.search.grocery.adapter.viewHolder.GrocerySearchFoodResultViewHolder;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeManipulationViewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<RecipeManipulationViewItemWrapper> list;

    private RecipeManipulationPhotoViewHolder.OnDeleteImageClickListener onDeleteImageClickListener;
    private RecipeManipulationIngredientsTitleAndPortionsViewHolder.OnPortionChangedListener onPortionChangedListener;

    public RecipeManipulationViewListAdapter(RecipeManipulationPhotoViewHolder.OnDeleteImageClickListener onDeleteImageClickListener,
                                             RecipeManipulationIngredientsTitleAndPortionsViewHolder.OnPortionChangedListener onPortionChangedListener){
        list = new ArrayList<>();

        this.onDeleteImageClickListener = onDeleteImageClickListener;
        this.onPortionChangedListener = onPortionChangedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RecipeManipulationViewItemWrapper.ItemType.PHOTO_VIEW:
                return new RecipeManipulationPhotoViewHolder(parent, onDeleteImageClickListener);
            case RecipeManipulationViewItemWrapper.ItemType.INGREDIENTS_TITLE_AND_PORTIONS_VIEW:
                return new RecipeManipulationIngredientsTitleAndPortionsViewHolder(parent, onPortionChangedListener);
            case RecipeManipulationViewItemWrapper.ItemType.INGREDIENT_ITEM:
                //return new RecipeManipulationIngredientItemViewHolder();
            case RecipeManipulationViewItemWrapper.ItemType.INGREDIENT_ITEM_ADD_VIEW:
                //return new RecipeManipulationItemAddViewHolder();
            case RecipeManipulationViewItemWrapper.ItemType.PREPARATION_STEPS_TITLE_VIEW:
                //return new RecipeManipulationPreparationStepsTitleViewHolder();
            case RecipeManipulationViewItemWrapper.ItemType.PREPARATION_STEP_ITEM:
                //return new RecipeManipulationPreparationStepItemViewHolder();
            case RecipeManipulationViewItemWrapper.ItemType.PREPARATION_STEP_ITEM_ADD_VIEW:
                //return new RecipeManipulationPreparationSetpItemAddViewHolder();
            case RecipeManipulationViewItemWrapper.ItemType.MEALS_TITLE_VIEW:
                //return new RecipeManipulationMealsTitleViewHolder();
            case RecipeManipulationViewItemWrapper.ItemType.MEAL_ITEM:
                //return new RecipeManipulationMealItemViewHolder();
            case RecipeManipulationViewItemWrapper.ItemType.MEAL_ITEM_ADD_VIEW:
                //return new RecipeManipulationMealItemAddViewHolder();
            case RecipeManipulationViewItemWrapper.ItemType.RECIPE_SAVE_VIEW:
                //return new RecipeManipulationRecipeSaveViewHolder();
            case RecipeManipulationViewItemWrapper.ItemType.RECIPE_DELETE_VIEW:
                //return new RecipeManipulationRecipeDeleteViewHolder();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecipeManipulationPhotoViewHolder) {
            ((RecipeManipulationPhotoViewHolder) holder).bind(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<RecipeManipulationViewItemWrapper> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }
}
