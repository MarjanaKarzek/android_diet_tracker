package de.karzek.diettracker.presentation.main.home;

import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
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
    private Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase;

    private NutritionManager nutritionManager;
    private SharedPreferencesManager sharedPreferencesManager;

    private MealUIMapper mealMapper;
    private RecipeUIMapper recipeMapper;
    private DiaryEntryUIMapper diaryEntryMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int currentMealId;
    private String currentDate;

    public HomePresenter(GetMealByIdUseCase getMealByIdUseCase,
                         GetAllFavoriteRecipesForMealUseCase getAllFavoriteRecipesForMealUseCase,
                         Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase,
                         GetAllDiaryEntriesMatchingUseCase getAllDiaryEntriesMatchingUseCase,
                         Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                         NutritionManager nutritionManager,
                         SharedPreferencesManager sharedPreferencesManager,
                         MealUIMapper mealMapper,
                         RecipeUIMapper recipeMapper,
                         DiaryEntryUIMapper diaryEntryMapper){
        this.getMealByIdUseCase = getMealByIdUseCase;
        this.getAllFavoriteRecipesForMealUseCase = getAllFavoriteRecipesForMealUseCase;
        this.getRecipeByIdUseCase = getRecipeByIdUseCase;
        this.getAllDiaryEntriesMatchingUseCase = getAllDiaryEntriesMatchingUseCase;
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
                                view.hideLoading();
                            });

                    getAllDiaryEntriesMatchingUseCase.execute(new GetAllDiaryEntriesMatchingUseCase.Input(null, currentDate))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(output -> {
                                if(sharedPreferencesManager.getNutritionDetailsSetting().equals(SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY))
                                    view.setCaloryState(nutritionManager.getCaloriesSumForDiaryEntries(output.getDiaryEntries()), sharedPreferencesManager.getCaloriesGoal());
                                else
                                    view.setNutritionState(nutritionManager.getNutritionSumsForDiaryEntries(output.getDiaryEntries()), sharedPreferencesManager.getCaloriesGoal(), sharedPreferencesManager.getProteinsGoal(), sharedPreferencesManager.getCarbsGoal(), sharedPreferencesManager.getFatsGoal());
                            });
                }));
    }

    @Override
    public void setView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void setCurrentMealId(int currentMealId){
        this.currentMealId = currentMealId;
    }

    @Override
    public void setCurrentDate(String currentDate){
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

                                for(IngredientDisplayModel ingredient : recipe.getIngredients()) {
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
