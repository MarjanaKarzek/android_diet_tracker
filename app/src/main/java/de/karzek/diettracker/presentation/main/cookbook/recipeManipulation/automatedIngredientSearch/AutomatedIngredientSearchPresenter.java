package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetExactlyMatchingGroceryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.PutRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.UpdateRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByIdUseCase;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.ManualIngredientDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.RemoveFavoriteGroceryByNameUseCase.Output.SUCCESS;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class AutomatedIngredientSearchPresenter implements AutomatedIngredientSearchContract.Presenter {

    private AutomatedIngredientSearchContract.View view;

    private GetExactlyMatchingGroceryUseCase getExactlyMatchingGroceryUseCase;
    private GetAllDefaultUnitsUseCase getAllDefaultUnitsUseCase;
    private Lazy<PutRecipeUseCase> putRecipeUseCase;
    private Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase;
    private Lazy<GetUnitByIdUseCase> getUnitByIdUseCase;
    private Lazy<UpdateRecipeUseCase> updateRecipeUseCase;

    private GroceryUIMapper groceryMapper;
    private UnitUIMapper unitMapper;
    private RecipeUIMapper recipeMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private RecipeDisplayModel recipe;
    private HashMap<Integer, Integer> failReasons = new HashMap<>();
    private int checkCounter;
    private boolean editMode = false;
    private boolean initialized = false;

    public AutomatedIngredientSearchPresenter(GetExactlyMatchingGroceryUseCase getExactlyMatchingGroceryUseCase,
                                              GetAllDefaultUnitsUseCase getAllDefaultUnitsUseCase,
                                              Lazy<PutRecipeUseCase> putRecipeUseCase,
                                              Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase,
                                              Lazy<GetUnitByIdUseCase> getUnitByIdUseCase,
                                              Lazy<UpdateRecipeUseCase> updateRecipeUseCase,
                                              GroceryUIMapper groceryMapper,
                                              UnitUIMapper unitMapper,
                                              RecipeUIMapper recipeMapper) {
        this.getExactlyMatchingGroceryUseCase = getExactlyMatchingGroceryUseCase;
        this.getAllDefaultUnitsUseCase = getAllDefaultUnitsUseCase;
        this.putRecipeUseCase = putRecipeUseCase;
        this.getGroceryByIdUseCase = getGroceryByIdUseCase;
        this.getUnitByIdUseCase = getUnitByIdUseCase;
        this.updateRecipeUseCase = updateRecipeUseCase;

        this.groceryMapper = groceryMapper;
        this.unitMapper = unitMapper;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public void start() {
        view.showLoading();
        view.setupViewsInRecyclerView(recipe.getIngredients());

        searchForIngredients();
    }

    @Override
    public void startEdit() {
        editMode = true;
        start();
    }

    private void searchForIngredients() {
        checkCounter = 0;
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            if (recipe.getIngredients().get(i) instanceof ManualIngredientDisplayModel) {
                int currentIndex = i;
                ManualIngredientDisplayModel ingredient = (ManualIngredientDisplayModel) recipe.getIngredients().get(currentIndex);
                compositeDisposable.add(getExactlyMatchingGroceryUseCase.execute(new GetExactlyMatchingGroceryUseCase.Input(ingredient.getGroceryQuery()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(output -> {
                            GroceryDisplayModel grocery = groceryMapper.transform(output.getGrocery());
                            if (grocery != null) {
                                compositeDisposable.add(getAllDefaultUnitsUseCase.execute(new GetAllDefaultUnitsUseCase.Input(grocery.getUnit_type()))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(unitOutput -> {
                                            if (!unitMapper.transformAll(unitOutput.getUnitList()).contains(recipe.getIngredients().get(currentIndex).getUnit()))
                                                failReasons.put(currentIndex, AutomatedIngredientSearchContract.FailReasons.FAIL_REASON_WRONG_UNIT);
                                            else {
                                                recipe.getIngredients().add(currentIndex, new IngredientDisplayModel(ingredient.getId(), grocery, ingredient.getAmount(), ingredient.getUnit()));
                                                recipe.getIngredients().remove(currentIndex + 1);
                                            }
                                            checkCounter++;
                                            finishAutomatedSearch();
                                        }));
                            } else {
                                failReasons.put(currentIndex, AutomatedIngredientSearchContract.FailReasons.FAIL_REASON_GROCERY_NOT_FOUND);
                                checkCounter++;
                                finishAutomatedSearch();
                            }
                        }));
            } else {
                checkCounter++;
                finishAutomatedSearch();
            }
        }
    }

    private void finishAutomatedSearch() {
        if (checkCounter == recipe.getIngredients().size()) {
            if (failReasons.size() == 0) {
                view.setupViewsInRecyclerView(recipe.getIngredients());
                view.hideLoading();
                if(!initialized) {
                    view.showSuccessfulSearchDialog();
                    initialized = true;
                }
                else
                    view.enableSaveButton();
            } else {
                view.setupViewsInRecyclerViewForAdaption(recipe.getIngredients(), failReasons);
                view.showSaveButton();
                view.hideLoading();
                if(!initialized) {
                    view.showUnsuccessfulSearchDiaog();
                    initialized = true;
                }
            }
        }
    }

    @Override
    public void setView(AutomatedIngredientSearchContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void setRecipe(RecipeDisplayModel recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onSuccessfulSearchDialogOKClicked() {
        view.showLoading();
        saveRecipe();
    }

    private void saveRecipe() {
        compositeDisposable.add(putRecipeUseCase.get().execute(new PutRecipeUseCase.Input(recipeMapper.transformToDomain(recipe)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == SUCCESS) {
                        view.finishActivity();
                    } else {
                        view.hideLoading();
                        view.showErrorWhileSavingRecipe();
                    }
                })
        );
    }

    @Override
    public void replaceIngredient(int index, int groceryId, float amount, int unitId) {
        view.showLoading();
        ManualIngredientDisplayModel ingredient = (ManualIngredientDisplayModel) recipe.getIngredients().get(index);
        compositeDisposable.add(getGroceryByIdUseCase.get().execute(new GetGroceryByIdUseCase.Input(groceryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(groceryOutput -> {
                    getUnitByIdUseCase.get().execute(new GetUnitByIdUseCase.Input(unitId))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(unitOutput -> {
                                recipe.getIngredients().add(index, new IngredientDisplayModel(ingredient.getId(), groceryMapper.transform(groceryOutput.getGrocery()), amount, unitMapper.transform(unitOutput.getUnit())));
                                recipe.getIngredients().remove(index + 1);
                                failReasons.remove(index);

                                view.setupViewsInRecyclerViewForAdaption(recipe.getIngredients(), failReasons);
                                if(failReasons.keySet().size() == 0)
                                    view.enableSaveButton();

                                view.hideLoading();
                            });
                }));
    }

    @Override
    public void onSaveButtonClicked() {
        if (!editMode)
            saveRecipe();
        else {
            compositeDisposable.add(updateRecipeUseCase.get().execute(new UpdateRecipeUseCase.Input(recipeMapper.transformToDomain(recipe)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                        if (output.getStatus() == PutRecipeUseCase.Output.SUCCESS) {
                            view.finishActivity();
                        } else {
                            view.hideLoading();
                            view.showErrorWhileSavingRecipe();
                        }
                    })
            );
        }
    }

    @Override
    public void onStartGrocerySearchClicked(int index) {
        view.startGrocerySearch(index);
    }

    @Override
    public void onStartBarcodeScanClicked(int index) {
        view.startBarcodeScan(index);
    }

    @Override
    public void onDeleteIngredientClicked(int id) {
        view.showLoading();
        failReasons.clear();
        recipe.getIngredients().remove(id);

        searchForIngredients();
    }
}
