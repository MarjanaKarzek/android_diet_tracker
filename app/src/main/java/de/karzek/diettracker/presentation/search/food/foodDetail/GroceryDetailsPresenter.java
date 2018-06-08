package de.karzek.diettracker.presentation.search.food.foodDetail;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.NutritionManagerImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.PutDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealCountUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetAllDefaultUnitsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealCountUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;
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
 * Created by MarjanaKarzek on 02.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 02.06.2018
 */
public class FoodDetailsPresenter implements FoodDetailsContract.Presenter {

    FoodDetailsContract.View view;
    private int groceryId;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private GetGroceryByIdUseCaseImpl getGroceryByIdUseCase;
    private GetAllDefaultUnitsUseCaseImpl getDefaultUnitsUseCase;
    private GetAllMealsUseCaseImpl getAllMealsUseCase;
    private GetMealCountUseCaseImpl getMealCountUseCase;
    private Lazy<PutDiaryEntryUseCaseImpl> putDiaryEntryUseCase;

    private GroceryUIMapper groceryMapper;
    private UnitUIMapper unitMapper;
    private MealUIMapper mealMapper;
    private DiaryEntryUIMapper diaryEntryMapper;

    private NutritionManagerImpl nutritionManager;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FoodDetailsPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                GetGroceryByIdUseCaseImpl getGroceryByIdUseCase,
                                GetAllDefaultUnitsUseCaseImpl getDefaultUnitsUseCase,
                                GetAllMealsUseCaseImpl getAllMealsUseCase,
                                GetMealCountUseCaseImpl getMealCountUseCase,
                                Lazy<PutDiaryEntryUseCaseImpl> putDiaryEntryUseCase,
                                GroceryUIMapper groceryMapper,
                                UnitUIMapper unitMapper,
                                MealUIMapper mealMapper,
                                DiaryEntryUIMapper diaryEntryMapper,
                                NutritionManagerImpl nutritionManager) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.getGroceryByIdUseCase = getGroceryByIdUseCase;
        this.getDefaultUnitsUseCase = getDefaultUnitsUseCase;
        this.getAllMealsUseCase = getAllMealsUseCase;
        this.getMealCountUseCase = getMealCountUseCase;
        this.putDiaryEntryUseCase = putDiaryEntryUseCase;

        this.groceryMapper = groceryMapper;
        this.unitMapper = unitMapper;
        this.mealMapper = mealMapper;
        this.diaryEntryMapper = diaryEntryMapper;

        this.nutritionManager = nutritionManager;
    }

    @Override
    public void start() {
        if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
        } else {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        }

        Disposable subs = getGroceryByIdUseCase.execute(new GetGroceryByIdUseCase.Input(groceryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    GroceryDisplayModel grocery = groceryMapper.transform(output.getGrocery());
                    view.fillGroceryDetails(grocery);

                    getDefaultUnitsUseCase.execute(new GetAllDefaultUnitsUseCase.Input(grocery.getUnit_type()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(output2 -> {
                                view.initializeServingsSpinner(unitMapper.transformAll(output2.getUnitList()), grocery.getServings());
                                getAllMealsUseCase.execute(new GetAllMealsUseCase.Input())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(output3 -> {
                                            view.initializeMealSpinner(mealMapper.transformAll(output3.getMealList()));
                                            view.refreshNutritionDetails();
                                        });
                            });
                });
        compositeDisposable.add(subs);
    }

    @Override
    public void updateNutritionDetails(GroceryDisplayModel grocery, float amount) {
        compositeDisposable.add(getMealCountUseCase.execute(new GetMealCountUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                            if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
                                compositeDisposable.add(Observable.just(nutritionManager.getCaloryMaxValueForMeal(output.getCount()))
                                        .subscribeOn(Schedulers.computation())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(output2 -> {
                                                    view.setNutritionMaxValues(output2);
                                                    Observable.just(nutritionManager.calculateTotalCaloriesForGrocery(groceryMapper.transformToDomain(grocery), amount))
                                                            .subscribeOn(Schedulers.computation())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(output3 -> {
                                                                view.updateNutritionDetails(output3);
                                                            });
                                                }
                                        ));
                            } else {
                                compositeDisposable.add(Observable.just(nutritionManager.getNutritionMaxValuesForMeal(output.getCount()))
                                        .subscribeOn(Schedulers.computation())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(output2 -> {
                                                    view.setNutritionMaxValues(output2);
                                                    Observable.just(nutritionManager.calculateTotalNutritionForGrocery(groceryMapper.transformToDomain(grocery), amount))
                                                            .subscribeOn(Schedulers.computation())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(output3 -> {
                                                                view.updateNutritionDetails(output3);
                                                            });
                                                }
                                        ));
                            }
                        }

                ));
    }

    @Override
    public void setView(FoodDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void setGroceryId(int groceryId) {
        this.groceryId = groceryId;
    }

    @Override
    public void addFood(DiaryEntryDisplayModel diaryEntry) {
        view.showLoading();
        Disposable subs = putDiaryEntryUseCase.get().execute(new PutDiaryEntryUseCase.Input(diaryEntryMapper.transformToDomain(diaryEntry)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        view.navigateToDiaryFragment();
                    }
                });
        compositeDisposable.add(subs);
    }

    @Override
    public void onDateLabelClicked() {
        view.openDatePicker();
    }

}
