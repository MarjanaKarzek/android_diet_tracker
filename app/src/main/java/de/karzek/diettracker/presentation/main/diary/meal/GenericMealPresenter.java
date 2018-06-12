package de.karzek.diettracker.presentation.main.diary.meal;

import java.util.ArrayList;
import java.util.List;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.UpdateMealOfDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealCountUseCase;
import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_SETTING_NUTRITION_DETAILS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class GenericMealPresenter implements GenericMealContract.Presenter {

    GenericMealContract.View view;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private GetAllDiaryEntriesMatchingUseCase getAllDiaryEntriesMatchingUseCase;
    private Lazy<GetAllMealsUseCase> getAllMealsUseCase;
    private GetMealCountUseCase getMealCountUseCase;
    private Lazy<DeleteDiaryEntryUseCase> deleteDiaryEntryUseCase;
    private Lazy<UpdateMealOfDiaryEntryUseCase> updateMealOfDiaryEntryUseCase;

    private NutritionManager nutritionManager;

    private MealUIMapper mealMapper;
    private DiaryEntryUIMapper diaryEntryMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String meal;

    public GenericMealPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                GetAllDiaryEntriesMatchingUseCase getAllDiaryEntriesMatchingUseCase,
                                Lazy<GetAllMealsUseCase> getAllMealsUseCase,
                                GetMealCountUseCase getMealCountUseCase,
                                Lazy<DeleteDiaryEntryUseCase> deleteDiaryEntryUseCase,
                                Lazy<UpdateMealOfDiaryEntryUseCase> updateMealOfDiaryEntryUseCase,
                                NutritionManager nutritionManager,
                                MealUIMapper mealMapper,
                                DiaryEntryUIMapper diaryEntryMapper) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.getAllDiaryEntriesMatchingUseCase = getAllDiaryEntriesMatchingUseCase;
        this.getAllMealsUseCase = getAllMealsUseCase;
        this.getMealCountUseCase = getMealCountUseCase;
        this.deleteDiaryEntryUseCase = deleteDiaryEntryUseCase;
        this.updateMealOfDiaryEntryUseCase = updateMealOfDiaryEntryUseCase;
        this.nutritionManager = nutritionManager;
        this.mealMapper = mealMapper;
        this.diaryEntryMapper = diaryEntryMapper;
    }

    @Override
    public void start() {
        view.showLoading();

        if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
        } else {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        }

        updateDiaryEntries(view.getSelectedDate());
    }

    @Override
    public void setView(GenericMealContract.View view) {
        this.view = view;
    }

    @Override
    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Override
    public void updateDiaryEntries(String date) {
        Disposable subs = getAllDiaryEntriesMatchingUseCase.execute(new GetAllDiaryEntriesMatchingUseCase.Input(meal, date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        if (output.getDiaryEntries().size() > 0) {
                            view.updateGroceryList(diaryEntryMapper.transformAll(output.getDiaryEntries()));
                            view.showRecyclerView();
                            view.hideGroceryListPlaceholder();
                            getNutritionValuesForDiaryEntries(output.getDiaryEntries());
                        } else {
                            view.hideRecyclerView();
                            view.showGroceryListPlaceholder();
                            getDefaultNutritionValues();
                        }
                        view.hideLoading();
                    }
                });
        compositeDisposable.add(subs);
    }

    private void getNutritionValuesForDiaryEntries(List<DiaryEntryDomainModel> diaryEntryDomainModels) {
        compositeDisposable.add(getMealCountUseCase.execute(new GetMealCountUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealOutput -> {
                    if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
                        compositeDisposable.add(Observable.just(nutritionManager.getCaloryMaxValueForMeal(mealOutput.getCount()))
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(output1 -> {
                                    view.setNutritionMaxValues(output1);
                                    Observable.just(nutritionManager.calculateTotalCalories(diaryEntryDomainModels))
                                            .subscribeOn(Schedulers.computation())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(output2 -> view.updateNutritionDetails(output2));
                                }));
                    } else {
                        compositeDisposable.add(Observable.just(nutritionManager.getNutritionMaxValuesForMeal(mealOutput.getCount()))
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(output1 -> {
                                    view.setNutritionMaxValues(output1);
                                    Observable.just(nutritionManager.calculateTotalNutrition(diaryEntryDomainModels))
                                            .subscribeOn(Schedulers.computation())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(output2 -> view.updateNutritionDetails(output2));
                                }));
                    }
                }));
    }

    private void getDefaultNutritionValues() {
        compositeDisposable.add(getMealCountUseCase.execute(new GetMealCountUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealOutput -> {
                    if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
                        compositeDisposable.add(Observable.just(nutritionManager.getCaloryMaxValueForMeal(mealOutput.getCount()))
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(output1 -> {
                                    view.setNutritionMaxValues(output1);
                                    Observable.just(nutritionManager.getDefaultValuesForTotalCalories())
                                            .subscribeOn(Schedulers.computation())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(output2 -> view.updateNutritionDetails(output2));
                                }));
                    } else {
                        compositeDisposable.add(Observable.just(nutritionManager.getNutritionMaxValuesForMeal(mealOutput.getCount()))
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(output1 -> {
                                    view.setNutritionMaxValues(output1);
                                    Observable.just(nutritionManager.getDefaultValuesForTotalNutrition())
                                            .subscribeOn(Schedulers.computation())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(output2 -> view.updateNutritionDetails(output2));
                                }));
                    }
                }));
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void onItemClicked(int id) {
        view.showLoading();
        view.startEditMode(id);
        view.hideLoading();
    }

    @Override
    public void onItemDelete(int id) {
        view.showLoading();
        compositeDisposable.add(deleteDiaryEntryUseCase.get().execute(new DeleteDiaryEntryUseCase.Input(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if(output.getStatus() == 0){
                        view.refreshRecyclerView();
                        view.hideLoading();
                    } else {
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void onItemMove(int id) {
        compositeDisposable.add(getAllMealsUseCase.get().execute(new GetAllMealsUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    ArrayList<MealDisplayModel> meals = mealMapper.transformAll(output.getMealList());
                    ArrayList<String> mealTitles = new ArrayList<>();

                    for (MealDisplayModel meal: meals)
                        mealTitles.add(meal.getName());

                    view.showMoveDiaryEntryDialog(id, meals, mealTitles);
                }));
    }

    @Override
    public void moveDiaryItemToMeal(int id, MealDisplayModel meal) {
        view.showLoading();
        compositeDisposable.add(updateMealOfDiaryEntryUseCase.get().execute(new UpdateMealOfDiaryEntryUseCase.Input(id, mealMapper.transformToDomain(meal)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if(output.getStatus() == 0){
                        view.hideLoading();
                    } else {
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void onItemEdit(int id) {
        view.showLoading();
        view.startEditMode(id);
        view.hideLoading();
    }
}
