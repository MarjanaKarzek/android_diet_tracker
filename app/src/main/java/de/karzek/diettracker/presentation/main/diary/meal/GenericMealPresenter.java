package de.karzek.diettracker.presentation.main.diary.meal;

import java.util.ArrayList;
import java.util.List;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.UpdateMealOfDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetAllFavoriteRecipesForMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByNameUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealCountUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
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
    private GetAllFavoriteRecipesForMealUseCase getAllFavoriteRecipesForMealUseCase;
    private GetMealCountUseCase getMealCountUseCase;
    private Lazy<GetMealByNameUseCase> getMealByNameUseCase;
    private Lazy<GetAllMealsUseCase> getAllMealsUseCase;
    private Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase;
    private Lazy<DeleteDiaryEntryUseCase> deleteDiaryEntryUseCase;
    private Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase;
    private Lazy<UpdateMealOfDiaryEntryUseCase> updateMealOfDiaryEntryUseCase;

    private NutritionManager nutritionManager;

    private MealUIMapper mealMapper;
    private DiaryEntryUIMapper diaryEntryMapper;
    private RecipeUIMapper recipeMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String meal;
    private String date;

    public GenericMealPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                GetAllDiaryEntriesMatchingUseCase getAllDiaryEntriesMatchingUseCase,
                                GetAllFavoriteRecipesForMealUseCase getAllFavoriteRecipesForMealUseCase,
                                GetMealCountUseCase getMealCountUseCase,
                                Lazy<GetAllMealsUseCase> getAllMealsUseCase,
                                Lazy<GetMealByNameUseCase> getMealByNameUseCase,
                                Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase,
                                Lazy<DeleteDiaryEntryUseCase> deleteDiaryEntryUseCase,
                                Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                                Lazy<UpdateMealOfDiaryEntryUseCase> updateMealOfDiaryEntryUseCase,
                                NutritionManager nutritionManager,
                                MealUIMapper mealMapper,
                                DiaryEntryUIMapper diaryEntryMapper,
                                RecipeUIMapper recipeMapper) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.getAllDiaryEntriesMatchingUseCase = getAllDiaryEntriesMatchingUseCase;
        this.getAllFavoriteRecipesForMealUseCase = getAllFavoriteRecipesForMealUseCase;
        this.getMealByNameUseCase = getMealByNameUseCase;
        this.getAllMealsUseCase = getAllMealsUseCase;
        this.getMealCountUseCase = getMealCountUseCase;
        this.getRecipeByIdUseCase = getRecipeByIdUseCase;
        this.deleteDiaryEntryUseCase = deleteDiaryEntryUseCase;
        this.putDiaryEntryUseCase = putDiaryEntryUseCase;
        this.updateMealOfDiaryEntryUseCase = updateMealOfDiaryEntryUseCase;

        this.nutritionManager = nutritionManager;
        this.mealMapper = mealMapper;
        this.diaryEntryMapper = diaryEntryMapper;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public void start() {
        view.showLoading();

        if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
        } else {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        }

        updateListItems(view.getSelectedDate());
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
    public void updateListItems(String date) {
        this.date = date;
        compositeDisposable.add(getAllDiaryEntriesMatchingUseCase.execute(new GetAllDiaryEntriesMatchingUseCase.Input(meal, date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        if (output.getDiaryEntries().size() > 0) {

                            view.updateGroceryList(diaryEntryMapper.transformAll(output.getDiaryEntries()));

                            view.showGroceryRecyclerView();
                            view.hideRecipeRecyclerView();
                            view.hideGroceryListPlaceholder();

                            getNutritionValuesForDiaryEntries(output.getDiaryEntries());

                        } else {
                            compositeDisposable.add(getAllFavoriteRecipesForMealUseCase.execute(new GetAllFavoriteRecipesForMealUseCase.Input(meal))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(output2 -> {
                                        getDefaultNutritionValues();
                                        if (output2.getFavoriteRecipes().size() > 0) {
                                            view.updateRecipeList(recipeMapper.transformAll(output2.getFavoriteRecipes()));
                                            view.showRecipeRecyclerView();
                                            view.hideGroceryRecyclerView();
                                            view.hideGroceryListPlaceholder();

                                        } else {

                                            view.hideGroceryRecyclerView();
                                            view.hideRecipeRecyclerView();
                                            view.showGroceryListPlaceholder();

                                        }
                                    }));
                        }
                        view.hideLoading();
                    }
                }));
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
                                    Observable.just(nutritionManager.calculateTotalCaloriesForDiaryEntry(diaryEntryDomainModels))
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
                                    Observable.just(nutritionManager.calculateTotalNutritionForDiaryEntry(diaryEntryDomainModels))
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
    public void onDiaryEntryClicked(int id) {
        view.showLoading();
        view.startEditMode(id);
        view.hideLoading();
    }

    @Override
    public void onDiaryEntryDeleteClicked(int id) {
        view.showLoading();
        compositeDisposable.add(deleteDiaryEntryUseCase.get().execute(new DeleteDiaryEntryUseCase.Input(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        view.refreshRecyclerView();
                        view.hideLoading();
                    } else {
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void onDiaryEntryMoveClicked(int id) {
        compositeDisposable.add(getAllMealsUseCase.get().execute(new GetAllMealsUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    ArrayList<MealDisplayModel> meals = mealMapper.transformAll(output.getMealList());
                    ArrayList<String> mealTitles = new ArrayList<>();

                    for (MealDisplayModel meal : meals)
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
                    if (output.getStatus() == 0) {
                        view.hideLoading();
                    } else {
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void onDiaryEntryEditClicked(int id) {
        view.showLoading();
        view.startEditMode(id);
        view.hideLoading();
    }

    @Override
    public void onFavoriteRecipeItemClicked(int id) {
        view.showLoading();
        compositeDisposable.add(getMealByNameUseCase.get().execute(new GetMealByNameUseCase.Input(meal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealOutput -> {
                    MealDisplayModel mealModel = mealMapper.transform(mealOutput.getMeal());

                    getRecipeByIdUseCase.get().execute(new GetRecipeByIdUseCase.Input(id))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(recipeOutput -> {
                                RecipeDisplayModel recipe = recipeMapper.transform(recipeOutput.getRecipe());

                                for (IngredientDisplayModel ingredient : recipe.getIngredients()) {
                                    putDiaryEntryUseCase.get().execute(new PutDiaryEntryUseCase.Input(diaryEntryMapper.transformToDomain(new DiaryEntryDisplayModel(Constants.INVALID_ENTITY_ID,
                                            mealModel,
                                            ingredient.getAmount() / recipe.getPortions(),
                                            ingredient.getUnit(),
                                            ingredient.getGrocery(),
                                            date))));
                                }

                                updateListItems(date);
                            });
                })
        );
    }

}
