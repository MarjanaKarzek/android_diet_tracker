package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetFavoriteStateForRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.PutFavoriteRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.RemoveFavoriteRecipeByTitleUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.model.FavoriteRecipeDomainModel;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class RecipeEditDetailsPresenter implements RecipeEditDetailsContract.Presenter {

    private RecipeEditDetailsContract.View view;

    private SharedPreferencesManager sharedPreferencesManager;
    private NutritionManager nutritionManager;
    private GetRecipeByIdUseCase getRecipeByIdUseCase;
    private Lazy<PutFavoriteRecipeUseCase> putFavoriteRecipeUseCase;
    private Lazy<RemoveFavoriteRecipeByTitleUseCase> removeFavoriteRecipeByTitleUseCase;
    private Lazy<GetFavoriteStateForRecipeByIdUseCase> getFavoriteStateForRecipeByIdUseCase;
    private GetAllMealsUseCase getAllMealsUseCase;
    private Lazy<GetMealByIdUseCase> getMealByIdUseCase;
    private Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase;

    private RecipeUIMapper recipeMapper;
    private MealUIMapper mealMapper;
    private DiaryEntryUIMapper diaryEntryMapper;

    private int recipeId;
    private RecipeDisplayModel recipe;
    private float selectedPortions = 1.0f;
    private int selectedMeal;
    private String selectedDate;

    private boolean detailsExpanded = false;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RecipeEditDetailsPresenter(GetRecipeByIdUseCase getRecipeByIdUseCase,
                                      SharedPreferencesManager sharedPreferencesManager,
                                      NutritionManager nutritionManager,
                                      Lazy<PutFavoriteRecipeUseCase> putFavoriteRecipeUseCase,
                                      Lazy<RemoveFavoriteRecipeByTitleUseCase> removeFavoriteRecipeByTitleUseCase,
                                      Lazy<GetFavoriteStateForRecipeByIdUseCase> getFavoriteStateForRecipeByIdUseCase,
                                      GetAllMealsUseCase getAllMealsUseCase,
                                      Lazy<GetMealByIdUseCase> getMealByIdUseCase,
                                      Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                                      RecipeUIMapper recipeMapper,
                                      MealUIMapper mealMapper,
                                      DiaryEntryUIMapper diaryEntryMapper) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.nutritionManager = nutritionManager;
        this.getRecipeByIdUseCase = getRecipeByIdUseCase;
        this.putFavoriteRecipeUseCase = putFavoriteRecipeUseCase;
        this.removeFavoriteRecipeByTitleUseCase = removeFavoriteRecipeByTitleUseCase;
        this.getFavoriteStateForRecipeByIdUseCase = getFavoriteStateForRecipeByIdUseCase;
        this.getAllMealsUseCase = getAllMealsUseCase;
        this.getMealByIdUseCase = getMealByIdUseCase;
        this.putDiaryEntryUseCase = putDiaryEntryUseCase;

        this.recipeMapper = recipeMapper;
        this.mealMapper = mealMapper;
        this.diaryEntryMapper = diaryEntryMapper;
    }

    @Override
    public void start() {
        view.showLoading();
        compositeDisposable.add(getRecipeByIdUseCase.execute(new GetRecipeByIdUseCase.Input(recipeId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    recipe = recipeMapper.transform(output.getRecipe());
                    setupView();
                })
        );
    }

    private void setupView() {
        compositeDisposable.add(getAllMealsUseCase.execute(new GetAllMealsUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealOutput -> {
                    if (sharedPreferencesManager.getNutritionDetailsSetting().equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
                        view.setupViewsInRecyclerView(recipe, selectedPortions, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY, detailsExpanded, nutritionManager.getCaloryMaxValueForDay(), nutritionManager.calculateTotalCaloriesForRecipe(recipe.getIngredients(), recipe.getPortions(), selectedPortions), mealMapper.transformAll(mealOutput.getMealList()));
                    } else {
                        view.setupViewsInRecyclerView(recipe, selectedPortions, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS, detailsExpanded, nutritionManager.getNutritionMaxValuesForDay(), nutritionManager.calculateTotalNutritionsForRecipe(recipe.getIngredients(), recipe.getPortions(), selectedPortions), mealMapper.transformAll(mealOutput.getMealList()));
                    }
                    view.hideLoading();
                }));
    }

    @Override
    public void setView(RecipeEditDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void checkFavoriteState(int recipeId) {
        compositeDisposable.add(getFavoriteStateForRecipeByIdUseCase.get().execute(new GetFavoriteStateForRecipeByIdUseCase.Input(recipeId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output2 -> {
                    view.setFavoriteIconCheckedState(output2.isState());
                }));
    }

    @Override
    public void setRecipeId(int id) {
        this.recipeId = id;
    }

    @Override
    public void onFavoriteRecipeClicked(boolean checked) {
        if (checked) {
            compositeDisposable.add(putFavoriteRecipeUseCase.get().execute(new PutFavoriteRecipeUseCase.Input(new FavoriteRecipeDomainModel(-1, recipeMapper.transformToDomain(recipe))))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                        //todo error handling
                    }));
        } else {
            compositeDisposable.add(removeFavoriteRecipeByTitleUseCase.get().execute(new RemoveFavoriteRecipeByTitleUseCase.Input(recipe.getTitle()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                        //todo error handling
                    }));
        }
    }

    @Override
    public void setSelectedMeal(int selectedMeal) {
        this.selectedMeal = selectedMeal;
    }

    @Override
    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    @Override
    public void setSelectedDateFromDialog(String selectedDate) {
        this.selectedDate = selectedDate;
        setupView();
    }

    @Override
    public void onExpandNutritionDetailsViewClicked() {
        detailsExpanded = !detailsExpanded;
        setupView();
    }

    @Override
    public void onPortionChanged(float portion) {
        this.selectedPortions = portion;
        setupView();
    }

    @Override
    public void onDeleteIngredientClicked(int id) {
        if (recipe.getIngredients().size() > 1) {
            recipe.getIngredients().remove(id);
            setupView();
        } else {
            view.showErrorNotEnoughIngredientsLeft();
        }
    }

    @Override
    public void onSaveRecipeClicked() {
        view.showLoading();
        compositeDisposable.add(getMealByIdUseCase.get().execute(new GetMealByIdUseCase.Input(selectedMeal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealOutput -> {
                    MealDisplayModel mealModel = mealMapper.transform(mealOutput.getMeal());

                    for (IngredientDisplayModel ingredient : recipe.getIngredients()) {
                        putDiaryEntryUseCase.get().execute(new PutDiaryEntryUseCase.Input(diaryEntryMapper.transformToDomain(new DiaryEntryDisplayModel(-1,
                                mealModel,
                                ingredient.getAmount() / recipe.getPortions(),
                                ingredient.getUnit(),
                                ingredient.getGrocery(),
                                selectedDate))));
                    }

                    view.navigateToDiary();
                })
        );
    }

    @Override
    public void onMealItemSelected(int id) {
        selectedMeal = id;
    }

    @Override
    public void onDateClicked() {
        view.openDateSelectorDialog();
    }
}
