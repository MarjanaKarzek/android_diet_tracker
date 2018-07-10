package de.karzek.diettracker.presentation.main.home;

import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.AddAmountOfWaterUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetWaterStatusUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.UpdateAmountOfWaterUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetAllFavoriteRecipesForMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import de.karzek.diettracker.presentation.util.StringUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_BOTTLE_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_GLASS_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_LIQUID_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_BOTTLE_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_GLASS_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_LIQUID_DAILY;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    private GetMealByIdUseCase getMealByIdUseCase;
    private GetAllFavoriteRecipesForMealUseCase getAllFavoriteRecipesForMealUseCase;
    private Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase;
    private GetAllDiaryEntriesMatchingUseCase getAllDiaryEntriesMatchingUseCase;
    private GetWaterStatusUseCase getWaterStatusUseCase;
    private Lazy<AddAmountOfWaterUseCase> addAmountOfWaterUseCase;
    private Lazy<UpdateAmountOfWaterUseCase> updateAmountOfWaterUseCase;
    private Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase;

    private NutritionManager nutritionManager;
    private SharedPreferencesManager sharedPreferencesManager;

    private MealUIMapper mealMapper;
    private RecipeUIMapper recipeMapper;
    private DiaryEntryUIMapper diaryEntryMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int currentMealId;
    private String currentDate;

    private float mlFromDiaryEntries = 0.0f;

    public HomePresenter(GetMealByIdUseCase getMealByIdUseCase,
                         GetAllFavoriteRecipesForMealUseCase getAllFavoriteRecipesForMealUseCase,
                         Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase,
                         GetAllDiaryEntriesMatchingUseCase getAllDiaryEntriesMatchingUseCase,
                         GetWaterStatusUseCase getWaterStatusUseCase,
                         Lazy<AddAmountOfWaterUseCase> addAmountOfWaterUseCase,
                         Lazy<UpdateAmountOfWaterUseCase> updateAmountOfWaterUseCase,
                         Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                         NutritionManager nutritionManager,
                         SharedPreferencesManager sharedPreferencesManager,
                         MealUIMapper mealMapper,
                         RecipeUIMapper recipeMapper,
                         DiaryEntryUIMapper diaryEntryMapper) {
        this.getMealByIdUseCase = getMealByIdUseCase;
        this.getAllFavoriteRecipesForMealUseCase = getAllFavoriteRecipesForMealUseCase;
        this.getRecipeByIdUseCase = getRecipeByIdUseCase;
        this.getAllDiaryEntriesMatchingUseCase = getAllDiaryEntriesMatchingUseCase;
        this.getWaterStatusUseCase = getWaterStatusUseCase;
        this.addAmountOfWaterUseCase = addAmountOfWaterUseCase;
        this.updateAmountOfWaterUseCase = updateAmountOfWaterUseCase;
        this.putDiaryEntryUseCase = putDiaryEntryUseCase;

        this.nutritionManager = nutritionManager;
        this.sharedPreferencesManager = sharedPreferencesManager;

        this.mealMapper = mealMapper;
        this.recipeMapper = recipeMapper;
        this.diaryEntryMapper = diaryEntryMapper;
    }

    @Override
    public void start() {
        compositeDisposable.add(getMealByIdUseCase.execute(new GetMealByIdUseCase.Input(currentMealId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealOutput -> {
                    MealDisplayModel mealModel = mealMapper.transform(mealOutput.getMeal());

                    if (sharedPreferencesManager.isStartScreenWithRecipesSet())
                        getAllFavoriteRecipesForMealUseCase.execute(new GetAllFavoriteRecipesForMealUseCase.Input(mealModel.getName()))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(output -> {
                                    if (output.getFavoriteRecipes().size() > 0) {
                                        view.showFavoriteText(mealModel.getName());
                                        view.updateRecyclerView(recipeMapper.transformAll(output.getFavoriteRecipes()));
                                        view.showRecyclerView();
                                    } else {
                                        view.hideFavoriteText();
                                        view.hideRecyclerView();
                                    }
                                });
                    else
                        view.hideFavoriteText();

                    getAllDiaryEntriesMatchingUseCase.execute(new GetAllDiaryEntriesMatchingUseCase.Input(Constants.ALL_MEALS, currentDate))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(output -> {
                                for(DiaryEntryDisplayModel entry: diaryEntryMapper.transformAll(output.getDiaryEntries()))
                                    if(entry.getMeal() == null)
                                        mlFromDiaryEntries += entry.getAmount() * entry.getUnit().getMultiplier();

                                if (sharedPreferencesManager.getNutritionDetailsSetting().equals(SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
                                    view.setCaloryState(nutritionManager.getCaloriesSumForDiaryEntries(output.getDiaryEntries()), sharedPreferencesManager.getCaloriesGoal());
                                    view.hideNutritionState();
                                } else
                                    view.setNutritionState(nutritionManager.getNutritionSumsForDiaryEntries(output.getDiaryEntries()), sharedPreferencesManager.getCaloriesGoal(), sharedPreferencesManager.getProteinsGoal(), sharedPreferencesManager.getCarbsGoal(), sharedPreferencesManager.getFatsGoal());
                            });

                    if (sharedPreferencesManager.isStartScreenWithDrinksSet()){
                        updateLiquidStatus();
                    } else {
                        view.hideDrinksSection();
                    }

                    view.hideLoading();
                }));
    }

    private void updateLiquidStatus() {
        compositeDisposable.add(getWaterStatusUseCase.execute(new GetWaterStatusUseCase.Input(currentDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        view.setLiquidStatus(diaryEntryMapper.transform(output.getWaterDiaryEntry()).getAmount() + mlFromDiaryEntries, sharedPreferencesManager.getLiquidGoal());
                    }
                }));
    }

    @Override
    public void setView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void setCurrentMealId(int currentMealId) {
        this.currentMealId = currentMealId;
    }

    @Override
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void onAddFoodClicked() {
        view.startFoodSearchActivity();
    }

    @Override
    public void onAddDrinkClicked() {
        view.startDrinkSearchActivity();
    }

    @Override
    public void onAddRecipeClicked() {
        view.startRecipeSearchActivity();
    }

    @Override
    public void onFabOverlayClicked() {
        view.closeFabMenu();
    }

    @Override
    public void addBottleWaterClicked() {
        view.showLoading();
        addAmountOfWater(sharedPreferencesManager.getVolumeForBottle());
    }

    @Override
    public void addGlassWaterClicked() {
        view.showLoading();
        addAmountOfWater(sharedPreferencesManager.getVolumeForGlass());
    }

    public void addAmountOfWater(float amount) {
        compositeDisposable.add(addAmountOfWaterUseCase.get().execute(new AddAmountOfWaterUseCase.Input(amount, currentDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        updateLiquidStatus();
                        view.hideLoading();
                    } else {
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void updateAmountOfWater(float amount) {
        view.showLoading();
        compositeDisposable.add(updateAmountOfWaterUseCase.get().execute(new UpdateAmountOfWaterUseCase.Input(amount, currentDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        updateLiquidStatus();
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void onFavoriteRecipeItemClicked(int id) {
        view.showLoading();
        compositeDisposable.add(getMealByIdUseCase.execute(new GetMealByIdUseCase.Input(currentMealId))
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
                                            currentDate))));
                                }

                                view.showRecipeAddedInfo();
                            });
                })
        );
    }
}
