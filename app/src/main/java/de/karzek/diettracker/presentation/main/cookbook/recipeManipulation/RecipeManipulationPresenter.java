package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation;

import android.graphics.Bitmap;
import android.text.Editable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.DeleteRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.PutRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.UpdateRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByIdUseCase;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.ManualIngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.PreparationStepDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_LIQUID;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_SOLID;
import static de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.PutRecipeUseCase.Output.SUCCESS;
import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_INGREDIENT_SEARCH;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
public class RecipeManipulationPresenter implements RecipeManipulationContract.Presenter {

    private RecipeManipulationContract.View view;

    private SharedPreferencesManager sharedPreferencesManager;
    private Lazy<GetAllDefaultUnitsUseCase> getAllDefaultUnitsUseCase;
    private Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase;
    private Lazy<GetUnitByIdUseCase> getUnitByIdUseCase;
    private Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase;
    private Lazy<DeleteRecipeByIdUseCase> deleteRecipeByIdUseCase;

    private UnitUIMapper unitMapper;
    private GroceryUIMapper groceryMapper;
    private RecipeUIMapper recipeMapper;

    private RecipeDisplayModel recipe;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ArrayList<UnitDisplayModel> units;

    public RecipeManipulationPresenter(SharedPreferencesManager sharedPreferencesManager,
                                       Lazy<GetAllDefaultUnitsUseCase> getAllDefaultUnitsUseCase,
                                       Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase,
                                       Lazy<GetUnitByIdUseCase> getUnitByIdUseCase,
                                       Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase,
                                       Lazy<DeleteRecipeByIdUseCase> deleteRecipeByIdUseCase,
                                       UnitUIMapper unitMapper,
                                       GroceryUIMapper groceryMapper,
                                       RecipeUIMapper recipeMapper) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.getAllDefaultUnitsUseCase = getAllDefaultUnitsUseCase;
        this.getGroceryByIdUseCase = getGroceryByIdUseCase;
        this.getUnitByIdUseCase = getUnitByIdUseCase;
        this.getRecipeByIdUseCase = getRecipeByIdUseCase;
        this.deleteRecipeByIdUseCase = deleteRecipeByIdUseCase;

        this.unitMapper = unitMapper;
        this.groceryMapper = groceryMapper;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public void start() {
        recipe = new RecipeDisplayModel(Constants.INVALID_ENTITY_ID, "", null, 1.0f, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        view.setupViewsInRecyclerView(recipe);
        if(!sharedPreferencesManager.getOnboardingViewed(ONBOARDING_INGREDIENT_SEARCH))
            view.showOnboardingScreen(ONBOARDING_INGREDIENT_SEARCH);
    }

    @Override
    public void startEditMode(int recipeId) {
        compositeDisposable.add(getRecipeByIdUseCase.get().execute(new GetRecipeByIdUseCase.Input(recipeId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == SUCCESS) {
                        recipe = recipeMapper.transform(output.getRecipe());
                        view.setRecipeTitle(recipe.getTitle());
                        view.setupViewsInRecyclerView(recipe);
                    } else {
                        view.finishActivity();
                    }
                })
        );
    }

    @Override
    public void onCameraIconClicked() {
        view.openBottomSheet();
    }

    @Override
    public void onOpenGalleryClicked() {
        view.openGallery();
    }

    @Override
    public void onOpenCameraClicked() {
        view.openCamera();
    }

    @Override
    public void addPhotoToRecipe(Bitmap bitmap) {
        view.showLoading();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        compositeDisposable.add(Observable.just(bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    Observable.just(stream.toByteArray())
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(output2 -> {
                                recipe.setPhoto(output2);
                                view.setupViewsInRecyclerView(recipe);
                                view.hideLoading();
                            });
                }));
    }

    @Override
    public void addManualIngredient(ManualIngredientDisplayModel manualIngredientDisplayModel) {
        recipe.getIngredients().add(manualIngredientDisplayModel);
        view.setupViewsInRecyclerView(recipe);
    }

    @Override
    public void addIngredient(int groceryId, float amount, int unitId) {
        view.showLoading();

        compositeDisposable.add(getGroceryByIdUseCase.get().execute(new GetGroceryByIdUseCase.Input(groceryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(groceryOutput -> {
                    getUnitByIdUseCase.get().execute(new GetUnitByIdUseCase.Input(unitId))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(unitOutput -> {
                                recipe.getIngredients().add(new IngredientDisplayModel(Constants.INVALID_ENTITY_ID, groceryMapper.transform(groceryOutput.getGrocery()), amount, unitMapper.transform(unitOutput.getUnit())));
                                view.setupViewsInRecyclerView(recipe);
                                view.hideLoading();
                            });
                }));
    }

    @Override
    public void addPreparationStep(String description) {
        recipe.getSteps().add(new PreparationStepDisplayModel(Constants.INVALID_ENTITY_ID, recipe.getSteps().size() + 1, description));
        view.setupViewsInRecyclerView(recipe);
    }

    @Override
    public void updateMeals(ArrayList<MealDisplayModel> selectedMeals) {
        recipe.setMeals(selectedMeals);
        view.setupViewsInRecyclerView(recipe);
    }

    @Override
    public void editIngredient(int ingredientId, float amount) {
        recipe.getIngredients().get(ingredientId).setAmount(amount);
        view.setupViewsInRecyclerView(recipe);
    }

    @Override
    public void editManualIngredient(int id, float amount, UnitDisplayModel unit, String groceryQuery) {
        recipe.getIngredients().get(id).setAmount(amount);
        ((ManualIngredientDisplayModel) recipe.getIngredients().get(id)).setGroceryQuery(groceryQuery);
        recipe.getIngredients().get(id).setUnit(unit);
        view.setupViewsInRecyclerView(recipe);
    }

    @Override
    public void updateTitle(String text) {
        if (recipe != null)
            recipe.setTitle(text);
    }

    @Override
    public void setView(RecipeManipulationContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void onDeleteImageClicked() {
        recipe.setPhoto(null);
        view.setupViewsInRecyclerView(recipe);
    }

    @Override
    public void onPortionChanges(float portion) {
        recipe.setPortions(portion);
    }

    @Override
    public void onAddManualIngredientClicked() {
        if (units == null) {
            compositeDisposable.add(getAllDefaultUnitsUseCase.get().execute(new GetAllDefaultUnitsUseCase.Input(TYPE_SOLID))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                                units = new ArrayList<>();
                                units.addAll(unitMapper.transformAll(output.getUnitList()));
                                getAllDefaultUnitsUseCase.get().execute(new GetAllDefaultUnitsUseCase.Input(TYPE_LIQUID))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(output2 -> {
                                                    units.addAll(unitMapper.transformAll(output.getUnitList()));
                                                    view.openAddManualIngredientDialog(units);
                                                }
                                        );
                            }
                    ));
        } else {
            view.openAddManualIngredientDialog(units);
        }
    }

    @Override
    public void onStartGrocerySearchClicked() {
        view.startGrocerySearch();
    }

    @Override
    public void onStartBarcodeScanClicked() {
        view.startBarcodeScan();
    }

    @Override
    public void onDeletePreparationStepClicked(int id) {
        recipe.getSteps().remove(id);

        for (int i = id; i < recipe.getSteps().size(); i++)
            recipe.getSteps().get(i).setStepNo(i + 1);

        view.setupViewsInRecyclerView(recipe);
    }

    @Override
    public void onEditPreparationStepFinished(int id, String description) {
        recipe.getSteps().get(id).setDescription(description);
    }

    @Override
    public void onAddPreparationStepClicked() {
        view.showAddPreparationStepDialog();
    }

    @Override
    public void onEditMealsClicked() {
        ArrayList<Integer> selectedMeals = new ArrayList<>();

        for (MealDisplayModel meals : recipe.getMeals())
            selectedMeals.add(meals.getId());

        view.openEditMealsDialog(selectedMeals);
    }

    @Override
    public void onDeleteIngredientClicked(int id) {
        recipe.getIngredients().remove(id);
        view.setupViewsInRecyclerView(recipe);
    }

    @Override
    public void onIngredientClicked(int id) {
        view.openEditIngredient(recipe.getIngredients().get(id));
    }

    @Override
    public void onDeleteManualIngredientClicked(int id) {
        recipe.getIngredients().remove(id);
        view.setupViewsInRecyclerView(recipe);
    }

    @Override
    public void onManualIngredientClicked(int id) {
        if (units == null) {
            compositeDisposable.add(getAllDefaultUnitsUseCase.get().execute(new GetAllDefaultUnitsUseCase.Input(TYPE_SOLID))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                                units = new ArrayList<>();
                                units.addAll(unitMapper.transformAll(output.getUnitList()));
                                getAllDefaultUnitsUseCase.get().execute(new GetAllDefaultUnitsUseCase.Input(TYPE_LIQUID))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(output2 -> {
                                                    units.addAll(unitMapper.transformAll(output.getUnitList()));
                                                    view.openEditManualIngredient(id, (ManualIngredientDisplayModel) recipe.getIngredients().get(id), units);
                                                }
                                        );
                            }
                    ));
        } else {
            view.openEditManualIngredient(id, (ManualIngredientDisplayModel) recipe.getIngredients().get(id), units);
        }
    }

    @Override
    public void onSaveRecipeClicked() {
        view.showLoading();
        boolean validRecipe = true;
        if (recipe.getTitle().equals("")) {
            view.showMissingTitleError();
            validRecipe = false;
        }

        if (recipe.getIngredients().size() == 0) {
            view.showMissingIngredientsError();
            validRecipe = false;
        }

        if (validRecipe) {
            view.navigateToAutomatedIngredientSearch(recipe);
            view.hideLoading();
        }
    }

    @Override
    public void onDeleteRecipeClicked() {
        view.showConfirmDeletionDialog();
    }

    @Override
    public void onDeleteRecipeConfirmed() {
        view.showLoading();
        compositeDisposable.add(deleteRecipeByIdUseCase.get().execute(new DeleteRecipeByIdUseCase.Input(recipe.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    view.navigateToCookbook();
                }));
    }
}
