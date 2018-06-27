package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByIdUseCase;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.ManualIngredientDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_LIQUID;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_SOLID;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
public class RecipeManipulationPresenter implements RecipeManipulationContract.Presenter {

    private RecipeManipulationContract.View view;

    private Lazy<GetAllDefaultUnitsUseCase> getAllDefaultUnitsUseCase;
    private Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase;
    private Lazy<GetUnitByIdUseCase> getUnitByIdUseCase;

    private UnitUIMapper unitMapper;
    private GroceryUIMapper groceryMapper;

    private boolean editMode = false;
    private RecipeDisplayModel displayModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ArrayList<UnitDisplayModel> units;

    public RecipeManipulationPresenter(Lazy<GetAllDefaultUnitsUseCase> getAllDefaultUnitsUseCase,
                                       Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase,
                                       Lazy<GetUnitByIdUseCase> getUnitByIdUseCase,
                                       UnitUIMapper unitMapper,
                                       GroceryUIMapper groceryMapper) {
        this.getAllDefaultUnitsUseCase = getAllDefaultUnitsUseCase;
        this.getGroceryByIdUseCase = getGroceryByIdUseCase;
        this.getUnitByIdUseCase = getUnitByIdUseCase;
        this.unitMapper = unitMapper;
        this.groceryMapper = groceryMapper;
    }

    @Override
    public void start() {
        displayModel = new RecipeDisplayModel(-1, "", null, 1.0f, new ArrayList<>(), new ArrayList<>());

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
        if (!editMode) {
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
    public void addManualIngredient(ManualIngredientDisplayModel manualIngredientDisplayModel) {
        displayModel.getIngredients().add(manualIngredientDisplayModel);
        view.setupViewsInRecyclerView(displayModel);
    }

    @Override
    public void addIngredient(int groceryId, float amount, int unitId) {
        view.showLoading();

        compositeDisposable.add(getGroceryByIdUseCase.get().execute(new GetGroceryByIdUseCase.Input(groceryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(groceryOutput-> {
                    getUnitByIdUseCase.get().execute(new GetUnitByIdUseCase.Input(unitId))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(unitOutput -> {
                                displayModel.getIngredients().add(new IngredientDisplayModel(-1, groceryMapper.transform(groceryOutput.getGrocery()), amount, unitMapper.transform(unitOutput.getUnit())));
                                view.setupViewsInRecyclerView(displayModel);
                                view.hideLoading();
                            });
                }));


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

    @Override
    public void onDeleteIngredientClicked() {

    }

    @Override
    public void onDeleteManualIngredientClicked() {

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
}