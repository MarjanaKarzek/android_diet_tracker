package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.main.cookbook.adapter.viewHolder.RecipeSearchResultViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationIngredientItemViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationIngredientsTitleAndPortionsViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationItemAddViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationManualIngredientItemViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationMealsViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationPhotoViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationPreparationStepItemAddViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder.RecipeManipulationPreparationStepItemViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.AddIngredientDialog;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.AddPreparationStepDialog;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals.EditMealsDialog;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.ManualIngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public interface RecipeManipulationContract {

    interface View extends BaseView<Presenter>,
            AddIngredientDialog.OnSaveIngredientClickedInDialogListener,
            AddIngredientDialog.OnAddIngredientClickedInDialogListener,
            AddPreparationStepDialog.OnAddPreparationStepClickedInDialogListener,
            EditMealsDialog.SaveMealsSelectionDialogListener {
        void openCamera();

        void closeBottomSheet();

        void openBottomSheet();

        void openGallery();

        void setupViewsInRecyclerView(RecipeDisplayModel displayModel);

        void startBarcodeScan();

        void startGrocerySearch();

        void openAddManualIngredientDialog(ArrayList<UnitDisplayModel> units);

        void showLoading();

        void hideLoading();

        void showAddPreparationStepDialog();

        void openEditMealsDialog(ArrayList<Integer> selectedMeals);

        void openEditManualIngredient(int id, ManualIngredientDisplayModel displayModel, ArrayList<UnitDisplayModel> units);

        void openEditIngredient(IngredientDisplayModel displayModel);

    }

    interface Presenter extends BasePresenter<View>,
            RecipeManipulationPhotoViewHolder.OnDeleteImageClickListener,
            RecipeManipulationIngredientsTitleAndPortionsViewHolder.OnPortionChangedListener,
            RecipeManipulationManualIngredientItemViewHolder.OnDeleteIngredientClickListener,
            RecipeManipulationManualIngredientItemViewHolder.OnManualIngredientClickedListener,
            RecipeManipulationIngredientItemViewHolder.OnDeleteIngredientClickListener,
            RecipeManipulationIngredientItemViewHolder.OnIngredientClickListener,
            RecipeManipulationItemAddViewHolder.OnAddManualIngredientClickListener,
            RecipeManipulationItemAddViewHolder.OnStartGrocerySearchClickListener,
            RecipeManipulationItemAddViewHolder.OnStartBarcodeScanClickListener,
            RecipeManipulationPreparationStepItemViewHolder.OnDeletePreparationStepClickedListener,
            RecipeManipulationPreparationStepItemViewHolder.OnEditPreparationStepFinishedListener,
            RecipeManipulationPreparationStepItemAddViewHolder.OnAddPreparationStepClickedListener,
            RecipeManipulationMealsViewHolder.OnEditMealsClickedListener {

        void startEditMode(int recipeId);

        void onCameraIconClicked();

        void onOpenGalleryClicked();

        void onOpenCameraClicked();

        void addPhotoToRecipe(Bitmap bitmap);

        void addManualIngredient(ManualIngredientDisplayModel manualIngredientDisplayModel);

        void addIngredient(int groceryId, float amount, int unitId);

        void addPreparationStep(String description);

        void updateMeals(ArrayList<MealDisplayModel> selectedMeals);

        void editIngredient(int ingredientId, float amount);

        void editManualIngredient(int id, float amount, UnitDisplayModel unit, String groceryQuery);
    }
}
