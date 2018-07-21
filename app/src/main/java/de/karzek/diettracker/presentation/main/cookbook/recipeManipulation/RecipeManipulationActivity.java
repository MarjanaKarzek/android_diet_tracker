package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.main.MainActivity;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.RecipeManipulationViewListAdapter;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.AutomatedIngredientSearchActivity;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.AddIngredientDialog;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.AddPreparationStepDialog;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.bottomSheet.ImageSelectorBottomSheetDialogFragment;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals.EditMealsDialog;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.ManualIngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.PreparationStepDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;
import de.karzek.diettracker.presentation.onboarding.OnboardingActivity;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchActivity;
import de.karzek.diettracker.presentation.search.grocery.barcodeScanner.BarcodeScannerActivity;
import de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsActivity;
import de.karzek.diettracker.presentation.util.Constants;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_COMBINED;
import static de.karzek.diettracker.presentation.util.Constants.CAMERA_PERMISSION;
import static de.karzek.diettracker.presentation.util.Constants.GET_IMAGE_FROM_CAMERA_RESULT;
import static de.karzek.diettracker.presentation.util.Constants.GET_IMAGE_FROM_GALLERY_RESULT;
import static de.karzek.diettracker.presentation.util.Constants.INVALID_ENTITY_ID;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
public class RecipeManipulationActivity extends BaseActivity implements RecipeManipulationContract.View {

    @Inject
    RecipeManipulationContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recipe_title)
    EditText titleView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_view)
    FrameLayout loadingView;

    private int mode;

    private ArrayList<UnitDisplayModel> units;

    public static Intent newAddIntent(Context context) {
        Intent intent = new Intent(context, RecipeManipulationActivity.class);
        intent.putExtra("mode", RecipeManipulationMode.MODE_ADD_RECIPE);

        return intent;
    }

    public static Intent newEditIntent(Context context, int recipeId) {
        Intent intent = new Intent(context, RecipeManipulationActivity.class);

        intent.putExtra("mode", RecipeManipulationMode.MODE_EDIT_RECIPE);
        intent.putExtra("recipeId", recipeId);

        return intent;
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_recipe_man, menu);

        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_manipulation);
        ButterKnife.bind(this);

        presenter.setView(this);
        setupSupportActionBar();
        setupRecyclerView();
        setupTitleSetListener();

        mode = getIntent().getExtras().getInt("mode");
        if (mode == RecipeManipulationMode.MODE_ADD_RECIPE) {
            presenter.start();
        } else if (mode == RecipeManipulationMode.MODE_EDIT_RECIPE) {
            presenter.startEditMode(getIntent().getExtras().getInt("recipeId"));
        }
    }

    private void setupTitleSetListener() {
        titleView.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(titleView.getWindowToken(), 0);
                    titleView.clearFocus();
                    presenter.updateTitle(titleView.getText().toString());
                }
                return true;
            }
        });
        titleView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                presenter.updateTitle(titleView.getText().toString());
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeManipulationViewListAdapter(presenter, presenter, presenter, presenter, presenter, presenter, presenter, presenter, presenter, presenter, presenter, presenter, presenter, presenter, presenter));
    }

    private void setupSupportActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow_white, null));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        else if (item.getItemId() == R.id.recipe_manipulation_camera) {
            presenter.onCameraIconClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, GET_IMAGE_FROM_CAMERA_RESULT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, GET_IMAGE_FROM_CAMERA_RESULT);
                } else
                    Toast.makeText(this, getString(R.string.permission_grand_camera_for_recipe), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openBottomSheet() {
        ImageSelectorBottomSheetDialogFragment imageSelector =
                ImageSelectorBottomSheetDialogFragment.newInstance();
        imageSelector.show(getSupportFragmentManager(),
                "imageSelector");
    }

    @Override
    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GET_IMAGE_FROM_GALLERY_RESULT);
    }

    @Override
    public void setupViewsInRecyclerView(RecipeDisplayModel displayModel) {
        ArrayList<RecipeManipulationViewItemWrapper> views = new ArrayList<>();
        if (displayModel.getPhoto() != null) {
            views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.PHOTO_VIEW, BitmapFactory.decodeByteArray(displayModel.getPhoto(), 0, displayModel.getPhoto().length)));
        }
        views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.INGREDIENTS_TITLE_AND_PORTIONS_VIEW, displayModel.getPortions()));

        for (IngredientDisplayModel ingredient : displayModel.getIngredients()) {
            if (ingredient instanceof ManualIngredientDisplayModel)
                views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.MANUAL_INGREDIENT_ITEM, ingredient));
            else
                views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.INGREDIENT_ITEM, ingredient));
        }
        views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.INGREDIENT_ITEM_ADD_VIEW));

        views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.PREPARATION_STEPS_TITLE_VIEW));
        for (PreparationStepDisplayModel step : displayModel.getSteps())
            views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.PREPARATION_STEP_ITEM, step));
        views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.PREPARATION_STEP_ITEM_ADD_VIEW));

        views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.MEALS_TITLE_VIEW));
        views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.MEAL_LIST, displayModel.getMeals()));

        views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.RECIPE_SAVE_VIEW));
        if (mode == RecipeManipulationMode.MODE_EDIT_RECIPE)
            views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.RECIPE_DELETE_VIEW));

        ((RecipeManipulationViewListAdapter) recyclerView.getAdapter()).setList(views);
    }

    @Override
    public void startBarcodeScan() {
        startActivityForResult(BarcodeScannerActivity.newIngredientSearchIntent(this), Constants.ADD_REPLACE_INGREDIENT_INTENT_RESULT);
    }

    @Override
    public void startGrocerySearch() {
        startActivityForResult(GrocerySearchActivity.newIngredientSearchIntent(this, TYPE_COMBINED), Constants.ADD_REPLACE_INGREDIENT_INTENT_RESULT);
    }

    @Override
    public void openAddManualIngredientDialog(ArrayList<UnitDisplayModel> units) {
        this.units = units;

        ArrayList<String> unitStrings = new ArrayList<>();
        for (UnitDisplayModel unit : units)
            unitStrings.add(unit.getName());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment previous = getSupportFragmentManager().findFragmentByTag("dialog");
        if (previous != null) {
            fragmentTransaction.remove(previous);
        }
        fragmentTransaction.addToBackStack(null);

        AppCompatDialogFragment dialogFragment = new AddIngredientDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("manualIngredientId", INVALID_ENTITY_ID);
        bundle.putStringArrayList("units", unitStrings);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction, "dialog");
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showAddPreparationStepDialog() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment previous = getSupportFragmentManager().findFragmentByTag("dialog");
        if (previous != null) {
            fragmentTransaction.remove(previous);
        }
        fragmentTransaction.addToBackStack(null);

        AppCompatDialogFragment dialogFragment = new AddPreparationStepDialog();
        dialogFragment.show(fragmentTransaction, "dialog");
    }

    @Override
    public void openEditMealsDialog(ArrayList<MealDisplayModel> selectedMeals) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment previous = getSupportFragmentManager().findFragmentByTag("dialog");
        if (previous != null)
            fragmentTransaction.remove(previous);
        fragmentTransaction.addToBackStack(null);

        AppCompatDialogFragment dialogFragment = new EditMealsDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("SelectedMeals", selectedMeals);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction, "dialog");
    }

    @Override
    public void openEditManualIngredient(int id, ManualIngredientDisplayModel displayModel, ArrayList<UnitDisplayModel> units) {
        this.units = units;

        ArrayList<String> unitStrings = new ArrayList<>();
        for (UnitDisplayModel unit : units)
            unitStrings.add(unit.getName());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment previous = getSupportFragmentManager().findFragmentByTag("dialog");
        if (previous != null) {
            fragmentTransaction.remove(previous);
        }
        fragmentTransaction.addToBackStack(null);

        AppCompatDialogFragment dialogFragment = new AddIngredientDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("manualIngredientId", id);
        bundle.putString("groceryQuery", displayModel.getGroceryQuery());
        bundle.putFloat("amount", displayModel.getAmount());
        bundle.putInt("unitId", displayModel.getUnit().getId());
        bundle.putStringArrayList("units", unitStrings);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction, "dialog");
    }

    @Override
    public void openEditIngredient(int index, IngredientDisplayModel displayModel) {
        startActivityForResult(GroceryDetailsActivity.newEditIngredientIntent(this, index, displayModel), Constants.EDIT_INGREDIENT_INTENT_RESULT);
    }

    @Override
    public void showMissingTitleError() {
        titleView.setError(getString(R.string.error_message_missing_recipe_title));
    }

    @Override
    public void showMissingIngredientsError() {
        Toast.makeText(this, getString(R.string.error_message_missing_ingredient), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void setRecipeTitle(String title) {
        titleView.setText(title);
    }

    @Override
    public void showConfirmDeletionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.dialog_message_confirm_recipe_deletion));
        builder.setPositiveButton(getString(R.string.dialog_action_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDeleteRecipeConfirmed();
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_action_dismiss), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        builder.create().show();
    }

    @Override
    public void navigateToCookbook() {
        startActivity(MainActivity.newIntentToCookbook(this));
        finish();
    }

    @Override
    public void showOnboardingScreen(int onboardingTag) {
        startActivity(OnboardingActivity.newIntent(this, onboardingTag));
    }

    @Override
    public void navigateToAutomatedIngredientSearch(RecipeDisplayModel recipe) {
        startActivityForResult(AutomatedIngredientSearchActivity.newIntent(this, recipe), Constants.CLOSE_SELF_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GET_IMAGE_FROM_GALLERY_RESULT:
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        presenter.addPhotoToRecipe(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case GET_IMAGE_FROM_CAMERA_RESULT:
                if (data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    presenter.addPhotoToRecipe(bitmap);
                }
                break;
            case Constants.ADD_REPLACE_INGREDIENT_INTENT_RESULT:
                if (data != null) {
                    presenter.addIngredient(data.getIntExtra("groceryId", 0),
                            data.getFloatExtra("amount", 0.0f),
                            data.getIntExtra("unitId", 0));
                }
                break;
            case Constants.EDIT_INGREDIENT_INTENT_RESULT:
                if (data != null) {
                    presenter.editIngredient(
                            data.getIntExtra("ingredientId", 0),
                            data.getFloatExtra("amount", 0.0f));
                }
                break;
            case Constants.CLOSE_SELF_RESULT:
                finish();
        }
    }

    @Override
    public void onAddManualIngredientClicked(float amount, int selectedUnitId, String groceryQuery) {
        presenter.addManualIngredient(new ManualIngredientDisplayModel(INVALID_ENTITY_ID, null, amount, units.get(selectedUnitId), groceryQuery));
    }

    @Override
    public void onAddPreparationStepClicked(String description) {
        presenter.addPreparationStep(description);
    }

    @Override
    public void updateMeals(ArrayList<MealDisplayModel> selectedMeals) {
        presenter.updateMeals(selectedMeals);
    }

    @Override
    public void onSaveManualIngredientClicked(int id, float amount, int selectedUnitId, String groceryQuery) {
        presenter.editManualIngredient(id, amount, units.get(selectedUnitId), groceryQuery);
    }

    @Override
    public void onOpenCameraClickedInBottomSheet() {
        presenter.onOpenCameraClicked();
    }

    @Override
    public void onOpenGalleryClickedInBottomSheet() {
        presenter.onOpenGalleryClicked();
    }

}
