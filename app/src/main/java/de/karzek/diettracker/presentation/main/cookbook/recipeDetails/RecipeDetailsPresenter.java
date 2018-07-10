package de.karzek.diettracker.presentation.main.cookbook.recipeDetails;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.NutritionManager;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetFavoriteStateForRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.PutFavoriteRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.RemoveFavoriteRecipeByTitleUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.model.FavoriteRecipeDomainModel;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter {

    private RecipeDetailsContract.View view;

    private SharedPreferencesManager sharedPreferencesManager;
    private NutritionManager nutritionManager;
    private GetRecipeByIdUseCase getRecipeByIdUseCase;
    private Lazy<PutFavoriteRecipeUseCase> putFavoriteRecipeUseCase;
    private Lazy<RemoveFavoriteRecipeByTitleUseCase> removeFavoriteRecipeByTitleUseCase;
    private Lazy<GetFavoriteStateForRecipeByIdUseCase> getFavoriteStateForRecipeByIdUseCase;
    private RecipeUIMapper recipeMapper;

    private int recipeId;
    private RecipeDisplayModel recipe;
    private boolean detailsExpanded = false;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RecipeDetailsPresenter(GetRecipeByIdUseCase getRecipeByIdUseCase,
                                  SharedPreferencesManager sharedPreferencesManager,
                                  NutritionManager nutritionManager,
                                  Lazy<PutFavoriteRecipeUseCase> putFavoriteRecipeUseCase,
                                  Lazy<RemoveFavoriteRecipeByTitleUseCase> removeFavoriteRecipeByTitleUseCase,
                                  Lazy<GetFavoriteStateForRecipeByIdUseCase> getFavoriteStateForRecipeByIdUseCase,
                                  RecipeUIMapper recipeMapper){
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.nutritionManager = nutritionManager;
        this.getRecipeByIdUseCase = getRecipeByIdUseCase;
        this.putFavoriteRecipeUseCase = putFavoriteRecipeUseCase;
        this.removeFavoriteRecipeByTitleUseCase = removeFavoriteRecipeByTitleUseCase;
        this.getFavoriteStateForRecipeByIdUseCase = getFavoriteStateForRecipeByIdUseCase;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public void start() {
        view.showLoading();
        compositeDisposable.add(getRecipeByIdUseCase.execute(new GetRecipeByIdUseCase.Input(recipeId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    recipe = recipeMapper.transform(output.getRecipe());
                    if (sharedPreferencesManager.getNutritionDetailsSetting().equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
                        view.setupViewsInRecyclerView(recipe, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY, detailsExpanded, nutritionManager.getCaloryMaxValueForDay(), nutritionManager.calculateTotalCaloriesForRecipe(recipe.getIngredients(), recipe.getPortions(), 1.0f));
                    } else {
                        view.setupViewsInRecyclerView(recipe, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS, detailsExpanded, nutritionManager.getNutritionMaxValuesForDay(), nutritionManager.calculateTotalNutritionsForRecipe(recipe.getIngredients(), recipe.getPortions(), 1.0f));
                    }
                    view.hideLoading();
                })
        );
    }

    @Override
    public void setView(RecipeDetailsContract.View view) {
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
    public void setRecipeId(int id){
        this.recipeId = id;
    }

    @Override
    public void onFavoriteRecipeClicked(boolean checked) {
        if(checked){
            Disposable subs = putFavoriteRecipeUseCase.get().execute(new PutFavoriteRecipeUseCase.Input(new FavoriteRecipeDomainModel(Constants.INVALID_ENTITY_ID, recipeMapper.transformToDomain(recipe))))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                        //todo error handling
                    });
            compositeDisposable.add(subs);
        } else {
            Disposable subs = removeFavoriteRecipeByTitleUseCase.get().execute(new RemoveFavoriteRecipeByTitleUseCase.Input(recipe.getTitle()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                        //todo error handling
                    });
            compositeDisposable.add(subs);
        }
    }

    @Override
    public void onExpandNutritionDetailsViewClicked() {
        detailsExpanded = !detailsExpanded;
        if (sharedPreferencesManager.getNutritionDetailsSetting().equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.setupViewsInRecyclerView(recipe, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY, detailsExpanded, nutritionManager.getCaloryMaxValueForDay(), nutritionManager.calculateTotalCaloriesForRecipe(recipe.getIngredients(), recipe.getPortions(), 1.0f));
        } else {
            view.setupViewsInRecyclerView(recipe, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS, detailsExpanded, nutritionManager.getNutritionMaxValuesForDay(), nutritionManager.calculateTotalNutritionsForRecipe(recipe.getIngredients(), recipe.getPortions(), 1.0f));
        }
    }
}
