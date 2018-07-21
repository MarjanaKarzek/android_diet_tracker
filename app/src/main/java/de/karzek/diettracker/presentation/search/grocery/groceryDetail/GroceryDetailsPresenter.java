package de.karzek.diettracker.presentation.search.grocery.groceryDetail;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.NutritionManagerImpl;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.DeleteDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetDiaryEntryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.PutDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.GetFavoriteStateForGroceryIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.PutFavoriteGroceryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.RemoveFavoriteGroceryByNameUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetMealCountUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetAllDefaultUnitsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetMatchingAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetDiaryEntryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteStateForGroceryIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.PutFavoriteGroceryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.RemoveFavoriteGroceryByNameUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealCountUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.model.FavoriteGroceryDomainModel;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;
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
public class GroceryDetailsPresenter implements GroceryDetailsContract.Presenter {

    GroceryDetailsContract.View view;
    private int groceryId;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private GetGroceryByIdUseCase getGroceryByIdUseCase;
    private GetAllDefaultUnitsUseCase getDefaultUnitsUseCase;
    private GetAllMealsUseCase getAllMealsUseCase;
    private GetMealCountUseCase getMealCountUseCase;
    private GetFavoriteStateForGroceryIdUseCase getFavoriteStateForGroceryId;
    private Lazy<GetDiaryEntryByIdUseCase> getDiaryEntryByIdUseCase;
    private Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase;
    private Lazy<PutFavoriteGroceryUseCase> putFavoriteGroceryUseCase;
    private Lazy<DeleteDiaryEntryUseCase> deleteDiaryEntryUseCase;
    private Lazy<RemoveFavoriteGroceryByNameUseCase> removeFavoriteGroceryByNameUseCase;
    private Lazy<GetMatchingAllergensUseCase> getMatchingAllergensUseCase;

    private GroceryUIMapper groceryMapper;
    private UnitUIMapper unitMapper;
    private MealUIMapper mealMapper;
    private DiaryEntryUIMapper diaryEntryMapper;
    private AllergenUIMapper allergenMapper;

    private NutritionManager nutritionManager;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public GroceryDetailsPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                   GetGroceryByIdUseCase getGroceryByIdUseCase,
                                   GetAllDefaultUnitsUseCase getDefaultUnitsUseCase,
                                   GetAllMealsUseCase getAllMealsUseCase,
                                   GetMealCountUseCase getMealCountUseCase,
                                   GetFavoriteStateForGroceryIdUseCase getFavoriteStateForGroceryId,
                                   Lazy<GetDiaryEntryByIdUseCase> getDiaryEntryByIdUseCase,
                                   Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                                   Lazy<PutFavoriteGroceryUseCase> putFavoriteGroceryUseCase,
                                   Lazy<DeleteDiaryEntryUseCase> deleteDiaryEntryUseCase,
                                   Lazy<RemoveFavoriteGroceryByNameUseCase> removeFavoriteGroceryByNameUseCase,
                                   Lazy<GetMatchingAllergensUseCase> getMatchingAllergensUseCase,
                                   GroceryUIMapper groceryMapper,
                                   UnitUIMapper unitMapper,
                                   MealUIMapper mealMapper,
                                   DiaryEntryUIMapper diaryEntryMapper,
                                   AllergenUIMapper allergenMapper,
                                   NutritionManager nutritionManager) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.getGroceryByIdUseCase = getGroceryByIdUseCase;
        this.getDefaultUnitsUseCase = getDefaultUnitsUseCase;
        this.getAllMealsUseCase = getAllMealsUseCase;
        this.getMealCountUseCase = getMealCountUseCase;
        this.getFavoriteStateForGroceryId = getFavoriteStateForGroceryId;
        this.getDiaryEntryByIdUseCase = getDiaryEntryByIdUseCase;
        this.putDiaryEntryUseCase = putDiaryEntryUseCase;
        this.putFavoriteGroceryUseCase = putFavoriteGroceryUseCase;
        this.deleteDiaryEntryUseCase = deleteDiaryEntryUseCase;
        this.removeFavoriteGroceryByNameUseCase = removeFavoriteGroceryByNameUseCase;
        this.getMatchingAllergensUseCase = getMatchingAllergensUseCase;

        this.groceryMapper = groceryMapper;
        this.unitMapper = unitMapper;
        this.mealMapper = mealMapper;
        this.diaryEntryMapper = diaryEntryMapper;
        this.allergenMapper = allergenMapper;

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

                    if (grocery.getAllergens().size() > 0)
                        getMatchingAllergensUseCase.get().execute(new GetMatchingAllergensUseCase.Input(allergenMapper.transformAllToDomain(grocery.getAllergens()))).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(output3 -> {
                                    if(output3.getAllergens().size() > 0)
                                        view.setupAllergenWarning(allergenMapper.transformAll(output3.getAllergens()));
                                });

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
    public void startEditDiaryEntryMode(int diaryEntryId) {
        if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
        } else {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        }

        compositeDisposable.add(getDiaryEntryByIdUseCase.get().execute(new GetDiaryEntryByIdUseCase.Input(diaryEntryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    DiaryEntryDisplayModel diaryEntry = diaryEntryMapper.transform(output.getDiaryEntry());
                    GroceryDisplayModel grocery = diaryEntry.getGrocery();
                    view.fillGroceryDetails(grocery);

                    if (grocery.getAllergens().size() > 0)
                        view.setupAllergenWarning(grocery.getAllergens());

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
                                            view.prepareEditMode(diaryEntry);
                                        });
                            });
                }));
    }

    @Override
    public void startEditIngredientMode(float amount) {
        if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
        } else {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        }

        compositeDisposable.add(getGroceryByIdUseCase.execute(new GetGroceryByIdUseCase.Input(groceryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    GroceryDisplayModel grocery = groceryMapper.transform(output.getGrocery());
                    view.fillGroceryDetails(grocery);

                    if (grocery.getAllergens().size() > 0)
                        view.setupAllergenWarning(grocery.getAllergens());

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
                                            view.prepareEditIngredientMode(amount);
                                        });
                            });
                }));
    }

    @Override
    public void startAddIngredientMode(int groceryId) {
        if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
        } else {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        }

        compositeDisposable.add(getGroceryByIdUseCase.execute(new GetGroceryByIdUseCase.Input(groceryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    GroceryDisplayModel grocery = groceryMapper.transform(output.getGrocery());
                    view.fillGroceryDetails(grocery);

                    if (grocery.getAllergens().size() > 0)
                        view.setupAllergenWarning(grocery.getAllergens());

                    getDefaultUnitsUseCase.execute(new GetAllDefaultUnitsUseCase.Input(grocery.getUnit_type()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(output2 -> {
                                view.initializeServingsSpinner(unitMapper.transformAll(output2.getUnitList()), grocery.getServings());
                                view.refreshNutritionDetails();
                                view.prepareAddIngredientMode();
                            });
                }));
    }

    @Override
    public void onDeleteDiaryEntryClicked(int diaryEntryId) {
        compositeDisposable.add(deleteDiaryEntryUseCase.get().execute(new DeleteDiaryEntryUseCase.Input(groceryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    view.finishView();
                }));
    }

    @Override
    public void updateNutritionDetails(GroceryDisplayModel grocery, float amount) {
        compositeDisposable.add(getMealCountUseCase.execute(new GetMealCountUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                            if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
                                if (grocery.getType() == TYPE_FOOD) {
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
                                    compositeDisposable.add(Observable.just(nutritionManager.getCaloryMaxValueForDay())
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
                                }
                            } else {
                                if (grocery.getType() == TYPE_FOOD) {
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
                                } else {
                                    compositeDisposable.add(Observable.just(nutritionManager.getNutritionMaxValuesForDay())
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
                        }

                ));
    }

    @Override
    public void setView(GroceryDetailsContract.View view) {
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
        putDiaryEntry(diaryEntry);
    }

    @Override
    public void onDateLabelClicked() {
        view.openDatePicker();
    }

    @Override
    public void addDrink(DiaryEntryDisplayModel diaryEntryDisplayModel) {
        putDiaryEntry(diaryEntryDisplayModel);
    }

    @Override
    public void onFavoriteGroceryClicked(boolean checked, GroceryDisplayModel grocery) {
        if (checked) {
            Disposable subs = putFavoriteGroceryUseCase.get().execute(new PutFavoriteGroceryUseCase.Input(new FavoriteGroceryDomainModel(-1, groceryMapper.transformToDomain(grocery))))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                        //todo error handling
                    });
            compositeDisposable.add(subs);
        } else {
            Disposable subs = removeFavoriteGroceryByNameUseCase.get().execute(new RemoveFavoriteGroceryByNameUseCase.Input(grocery.getName()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                        //todo error handling
                    });
            compositeDisposable.add(subs);
        }
    }

    @Override
    public void checkFavoriteState(int groceryId) {
        compositeDisposable.add(getFavoriteStateForGroceryId.execute(new GetFavoriteStateForGroceryIdUseCase.Input(groceryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output2 -> {
                    view.setFavoriteIconCheckedState(output2.isState());
                }));
    }


    private void putDiaryEntry(DiaryEntryDisplayModel diaryEntry) {
        view.showLoading();
        compositeDisposable.add(putDiaryEntryUseCase.get().execute(new PutDiaryEntryUseCase.Input(diaryEntryMapper.transformToDomain(diaryEntry)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        view.navigateToDiaryFragment();
                    }
                }));
    }

}
