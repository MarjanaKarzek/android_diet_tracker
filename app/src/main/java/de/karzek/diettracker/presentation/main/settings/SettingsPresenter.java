package de.karzek.diettracker.presentation.main.settings;

import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllergenByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.DeleteMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.PutMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.UpdateMealUseCase;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View view;

    private GetAllMealsUseCase getAllMealsUseCase;
    private GetAllergenByIdUseCase getAllergenByIdUseCase;
    private SharedPreferencesManager sharedPreferencesManager;
    private Lazy<GetMealByIdUseCase> getMealByIdUseCase;
    private Lazy<UpdateMealUseCase> updateMealUseCase;
    private Lazy<PutMealUseCase> putMealUseCase;
    private Lazy<DeleteMealByIdUseCase> deleteMealByIdUseCase;

    private MealUIMapper mealMapper;
    private AllergenUIMapper allergenMapper;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SettingsPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                             GetAllMealsUseCase getAllMealsUseCase,
                             GetAllergenByIdUseCase getAllergenByIdUseCase,
                             SharedPreferencesManager sharedPreferencesManager,
                             Lazy<GetMealByIdUseCase> getMealByIdUseCase,
                             Lazy<UpdateMealUseCase> updateMealUseCase,
                             Lazy<PutMealUseCase> putMealUseCase,
                             Lazy<DeleteMealByIdUseCase> deleteMealByIdUseCase,
                             MealUIMapper mealMapper,
                             AllergenUIMapper allergenMapper) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;

        this.getAllMealsUseCase = getAllMealsUseCase;
        this.getAllergenByIdUseCase = getAllergenByIdUseCase;
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.getMealByIdUseCase = getMealByIdUseCase;
        this.updateMealUseCase = updateMealUseCase;
        this.putMealUseCase = putMealUseCase;
        this.deleteMealByIdUseCase = deleteMealByIdUseCase;

        this.mealMapper = mealMapper;
        this.allergenMapper = allergenMapper;
    }

    @Override
    public void start() {
        view.fillSettingsOptions(sharedPreferencesUtil);
        view.setupCheckboxListeners();

        getAllMeals();
        updateAllergens();
    }

    private void getAllMeals(){
        compositeDisposable.add(getAllMealsUseCase.execute(new GetAllMealsUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output ->
                        view.setupMealRecyclerView(mealMapper.transformAll(output.getMealList()))
                ));
    }

    @Override
    public void setView(SettingsContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void updateSharedPreferenceIntValue(String key, Integer value) {
        sharedPreferencesUtil.setInt(key, value);
    }

    @Override
    public void updateSharedPreferenceFloatValue(String key, Float value) {
        sharedPreferencesUtil.setFloat(key, value);
    }

    @Override
    public void onEditAllergensClicked() {
        view.showEditAllergenDialog();
    }

    @Override
    public void updateAllergens() {
        compositeDisposable.add(Observable.fromArray(sharedPreferencesManager.getAllergenIds().toArray())
                .concatMap(item -> getAllergenByIdUseCase.execute(new GetAllergenByIdUseCase.Input((int) item))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                )
                .toList()
                .subscribe(outputs -> {
                    ArrayList<AllergenDisplayModel> displayModels = new ArrayList<>();

                    for (GetAllergenByIdUseCase.Output item : outputs) {
                        displayModels.add(allergenMapper.transform(item.getAllergen()));
                    }

                    view.setupAllergenTextView(displayModels);
                }));
    }

    @Override
    public void setNutritionDetailsSetting(boolean checked) {
        sharedPreferencesManager.setNutritionDetailsSetting(checked);
    }

    @Override
    public void setStartScreenRecipesSetting(boolean checked) {
        sharedPreferencesManager.setStartScreenRecipesSetting(checked);
    }

    @Override
    public void setStartScreenLiquidsSetting(boolean checked) {
        sharedPreferencesManager.setStartScreenLiquidsSetting(checked);
    }

    @Override
    public void onAddMealInDialogClicked(String name, String startTime, String endTime) {
        compositeDisposable.add(putMealUseCase.get().execute(new PutMealUseCase.Input(mealMapper.transformToDomain(new MealDisplayModel(Constants.INVALID_ENTITY_ID, name, startTime, endTime))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    getAllMeals();
                }));
    }

    @Override
    public void onSaveMealInDialogClicked(int id, String name, String startTime, String endTime) {
        compositeDisposable.add(updateMealUseCase.get().execute(new UpdateMealUseCase.Input(mealMapper.transformToDomain(new MealDisplayModel(id, name, startTime, endTime))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    getAllMeals();
                }));
    }

    @Override
    public void onEditMealItemClicked(int id) {
        compositeDisposable.add(getMealByIdUseCase.get().execute(new GetMealByIdUseCase.Input(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    view.showEditMealDialog(mealMapper.transform(output.getMeal()));
                }));
    }

    @Override
    public void onMealItemDelete(int id) {
        compositeDisposable.add(deleteMealByIdUseCase.get().execute(new DeleteMealByIdUseCase.Input(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    getAllMeals();
                }));
    }
}
