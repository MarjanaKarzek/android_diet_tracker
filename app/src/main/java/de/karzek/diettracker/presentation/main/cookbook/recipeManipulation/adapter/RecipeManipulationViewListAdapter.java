package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter;

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

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeManipulationViewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<RecipeManipulationViewItemWrapper> list;

    private int currentPreparationStepInnerListId = 0;
    private int currentIngredientInnerListId = 0;

    private RecipeManipulationPhotoViewHolder.OnDeleteImageClickListener onDeleteImageClickListener;
    private RecipeManipulationIngredientsTitleAndPortionsViewHolder.OnPortionChangedListener onPortionChangedListener;
    private RecipeManipulationManualIngredientItemViewHolder.OnDeleteIngredientClickListener onDeleteManualIngredientClickListener;
    private RecipeManipulationManualIngredientItemViewHolder.OnManualIngredientClickedListener onManualIngredientClickedListener;
    private RecipeManipulationIngredientItemViewHolder.OnDeleteIngredientClickListener onDeleteIngredientClickListener;
    private RecipeManipulationIngredientItemViewHolder.OnIngredientClickListener onIngredientClickListener;
    private RecipeManipulationItemAddViewHolder.OnAddManualIngredientClickListener onAddManualIngredientClickListener;
    private RecipeManipulationItemAddViewHolder.OnStartGrocerySearchClickListener onStartGrocerySearchClickListener;
    private RecipeManipulationItemAddViewHolder.OnStartBarcodeScanClickListener onStartBarcodeScanClickListener;
    private RecipeManipulationPreparationStepItemViewHolder.OnDeletePreparationStepClickedListener onDeletePreparationStepClickedListener;
    private RecipeManipulationPreparationStepItemViewHolder.OnEditPreparationStepFinishedListener onEditPreparationStepFinishedListener;
    private RecipeManipulationPreparationStepItemAddViewHolder.OnAddPreparationStepClickedListener onAddPreparationStepClickedListener;
    private RecipeManipulationMealsViewHolder.OnEditMealsClickedListener onEditMealsClickedListener;
    private RecipeManipulationRecipeSaveViewHolder.OnSaveRecipeClickedListener onSaveRecipeClickedListener;
    private RecipeManipulationRecipeDeleteViewHolder.OnDeleteRecipeClickListener onDeleteRecipeClickedListener;

    public RecipeManipulationViewListAdapter(RecipeManipulationPhotoViewHolder.OnDeleteImageClickListener onDeleteImageClickListener,
                                             RecipeManipulationIngredientsTitleAndPortionsViewHolder.OnPortionChangedListener onPortionChangedListener,
                                             RecipeManipulationManualIngredientItemViewHolder.OnDeleteIngredientClickListener onDeleteManualIngredientClickListener,
                                             RecipeManipulationManualIngredientItemViewHolder.OnManualIngredientClickedListener onManualIngredientClickedListener,
                                             RecipeManipulationIngredientItemViewHolder.OnDeleteIngredientClickListener onDeleteIngredientClickListener,
                                             RecipeManipulationIngredientItemViewHolder.OnIngredientClickListener onIngredientClickListener,
                                             RecipeManipulationItemAddViewHolder.OnAddManualIngredientClickListener onAddManualIngredientClickListener,
                                             RecipeManipulationItemAddViewHolder.OnStartGrocerySearchClickListener onStartGrocerySearchClickListener,
                                             RecipeManipulationItemAddViewHolder.OnStartBarcodeScanClickListener onStartBarcodeScanClickListener,
                                             RecipeManipulationPreparationStepItemViewHolder.OnDeletePreparationStepClickedListener onDeletePreparationStepClickedListener,
                                             RecipeManipulationPreparationStepItemViewHolder.OnEditPreparationStepFinishedListener onEditPreparationStepFinishedListener,
                                             RecipeManipulationPreparationStepItemAddViewHolder.OnAddPreparationStepClickedListener onAddPreparationStepClickedListener,
                                             RecipeManipulationMealsViewHolder.OnEditMealsClickedListener onEditMealsClickedListener,
                                             RecipeManipulationRecipeSaveViewHolder.OnSaveRecipeClickedListener onSaveRecipeClickedListener,
                                             RecipeManipulationRecipeDeleteViewHolder.OnDeleteRecipeClickListener onDeleteRecipeClickedListener){
        list = new ArrayList<>();

        this.onDeleteImageClickListener = onDeleteImageClickListener;
        this.onPortionChangedListener = onPortionChangedListener;
        this.onDeleteManualIngredientClickListener = onDeleteManualIngredientClickListener;
        this.onManualIngredientClickedListener = onManualIngredientClickedListener;
        this.onDeleteIngredientClickListener = onDeleteIngredientClickListener;
        this.onIngredientClickListener = onIngredientClickListener;
        this.onAddManualIngredientClickListener = onAddManualIngredientClickListener;
        this.onStartGrocerySearchClickListener = onStartGrocerySearchClickListener;
        this.onStartBarcodeScanClickListener = onStartBarcodeScanClickListener;
        this.onDeletePreparationStepClickedListener = onDeletePreparationStepClickedListener;
        this.onEditPreparationStepFinishedListener = onEditPreparationStepFinishedListener;
        this.onAddPreparationStepClickedListener = onAddPreparationStepClickedListener;
        this.onEditMealsClickedListener = onEditMealsClickedListener;
        this.onSaveRecipeClickedListener = onSaveRecipeClickedListener;
        this.onDeleteRecipeClickedListener = onDeleteRecipeClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RecipeManipulationViewItemWrapper.ItemType.PHOTO_VIEW:
                return new RecipeManipulationPhotoViewHolder(parent, onDeleteImageClickListener);
            case RecipeManipulationViewItemWrapper.ItemType.INGREDIENTS_TITLE_AND_PORTIONS_VIEW:
                return new RecipeManipulationIngredientsTitleAndPortionsViewHolder(parent, onPortionChangedListener);
            case RecipeManipulationViewItemWrapper.ItemType.MANUAL_INGREDIENT_ITEM:
                return new RecipeManipulationManualIngredientItemViewHolder(parent, onDeleteManualIngredientClickListener, onManualIngredientClickedListener);
            case RecipeManipulationViewItemWrapper.ItemType.INGREDIENT_ITEM:
                return new RecipeManipulationIngredientItemViewHolder(parent, onDeleteIngredientClickListener, onIngredientClickListener);
            case RecipeManipulationViewItemWrapper.ItemType.INGREDIENT_ITEM_ADD_VIEW:
                return new RecipeManipulationItemAddViewHolder(parent, onAddManualIngredientClickListener, onStartGrocerySearchClickListener, onStartBarcodeScanClickListener);
            case RecipeManipulationViewItemWrapper.ItemType.PREPARATION_STEPS_TITLE_VIEW:
                return new RecipeManipulationPreparationStepTitleViewHolder(parent);
            case RecipeManipulationViewItemWrapper.ItemType.PREPARATION_STEP_ITEM:
                return new RecipeManipulationPreparationStepItemViewHolder(parent, onDeletePreparationStepClickedListener, onEditPreparationStepFinishedListener);
            case RecipeManipulationViewItemWrapper.ItemType.PREPARATION_STEP_ITEM_ADD_VIEW:
                return new RecipeManipulationPreparationStepItemAddViewHolder(parent, onAddPreparationStepClickedListener);
            case RecipeManipulationViewItemWrapper.ItemType.MEALS_TITLE_VIEW:
                return new RecipeManipulationMealsTitleViewHolder(parent);
            case RecipeManipulationViewItemWrapper.ItemType.MEAL_LIST:
                return new RecipeManipulationMealsViewHolder(parent, onEditMealsClickedListener);
            case RecipeManipulationViewItemWrapper.ItemType.RECIPE_SAVE_VIEW:
                return new RecipeManipulationRecipeSaveViewHolder(parent, onSaveRecipeClickedListener);
            case RecipeManipulationViewItemWrapper.ItemType.RECIPE_DELETE_VIEW:
                return new RecipeManipulationRecipeDeleteViewHolder(parent, onDeleteRecipeClickedListener);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecipeManipulationPhotoViewHolder) {
            ((RecipeManipulationPhotoViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof RecipeManipulationIngredientsTitleAndPortionsViewHolder){
            ((RecipeManipulationIngredientsTitleAndPortionsViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof RecipeManipulationManualIngredientItemViewHolder){
            ((RecipeManipulationManualIngredientItemViewHolder) holder).bind(list.get(position), currentIngredientInnerListId);
            currentIngredientInnerListId++;
        } else if (holder instanceof RecipeManipulationIngredientItemViewHolder){
            ((RecipeManipulationIngredientItemViewHolder) holder).bind(list.get(position), currentIngredientInnerListId);
            currentIngredientInnerListId++;
        } else if (holder instanceof RecipeManipulationItemAddViewHolder){
            ((RecipeManipulationItemAddViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof RecipeManipulationPreparationStepTitleViewHolder){
            ((RecipeManipulationPreparationStepTitleViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof RecipeManipulationPreparationStepItemViewHolder){
            ((RecipeManipulationPreparationStepItemViewHolder) holder).bind(list.get(position), currentPreparationStepInnerListId);
            currentPreparationStepInnerListId++;
        } else if (holder instanceof RecipeManipulationPreparationStepItemAddViewHolder){
            ((RecipeManipulationPreparationStepItemAddViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof RecipeManipulationMealsTitleViewHolder){
            ((RecipeManipulationMealsTitleViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof RecipeManipulationMealsViewHolder){
            ((RecipeManipulationMealsViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof RecipeManipulationRecipeSaveViewHolder){
            ((RecipeManipulationRecipeSaveViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof RecipeManipulationRecipeDeleteViewHolder){
            ((RecipeManipulationRecipeDeleteViewHolder) holder).bind(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<RecipeManipulationViewItemWrapper> list){
        currentIngredientInnerListId = 0;
        currentPreparationStepInnerListId = 0;
        this.list = list;
        notifyDataSetChanged();
    }

    @Override public int getItemViewType(int position) {
        return list.get(position).getType();
    }

}
