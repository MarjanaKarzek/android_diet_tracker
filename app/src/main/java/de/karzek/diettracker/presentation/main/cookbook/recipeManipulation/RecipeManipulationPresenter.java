package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
public class RecipeManipulationPresenter implements RecipeManipulationContract.Presenter {

    private RecipeManipulationContract.View view;

    private boolean editMode = false;
    private RecipeDisplayModel displayModel;

    @Override
    public void start() {
        displayModel = new RecipeDisplayModel(-1,"",null,1.0f, new ArrayList<>(), new ArrayList<>());
        view.setupViewsInRecyclerView(displayModel);
    }

    @Override
    public void startEditMode(int recipeId) {
        editMode = true;
    }

    @Override
    public void onCameraIconClicked() {
        view.openBottomSheet();
    }

    @Override
    public void onOpenGalleryClicked() {
        view.openGallery();
        view.closeBottomSheet();
    }

    @Override
    public void onOpenCameraClicked() {
        view.openCamera();
        view.closeBottomSheet();
    }

    @Override
    public void addPhotoToRecipe(Bitmap bitmap) {
        if (!editMode){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            displayModel.setPhoto(byteArray);
        } else {
            //todo add edit mode
        }
        view.setupViewsInRecyclerView(displayModel);
    }

    @Override
    public void setView(RecipeManipulationContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {

    }

    @Override
    public void onDeleteImageClicked() {
        displayModel.setPhoto(null);
        view.setupViewsInRecyclerView(displayModel);
    }

    @Override
    public void onPortionChanges(float portion) {
        displayModel.setPortions(portion);
    }
}
