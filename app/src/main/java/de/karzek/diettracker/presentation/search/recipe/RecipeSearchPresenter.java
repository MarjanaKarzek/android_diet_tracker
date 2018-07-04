package de.karzek.diettracker.presentation.search.recipe;

import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetAllFavoriteRecipesForMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetFavoriteRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetMatchingGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByNameUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetMatchingRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipesMatchingQueryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByNameUseCase;
import de.karzek.diettracker.domain.model.FavoriteRecipeDomainModel;
import de.karzek.diettracker.domain.model.RecipeDomainModel;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.FavoriteGroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.FavoriteGroceryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_BOTTLE_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_GLASS_VOLUME;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class RecipeSearchPresenter implements RecipeSearchContract.Presenter {

    private RecipeSearchContract.View view;

    private GetMealByIdUseCase getMealByIdUseCase;
    private GetAllFavoriteRecipesForMealUseCase getFavoriteRecipesForMealUseCase;
    private Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase;
    private Lazy<GetRecipesMatchingQueryUseCase> getRecipesMatchingQueryUseCase;
    private Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase;

    private RecipeUIMapper recipeMapper;
    private MealUIMapper mealMapper;
    private DiaryEntryUIMapper diaryEntryMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RecipeSearchPresenter(GetMealByIdUseCase getMealByIdUseCase,
                                 GetAllFavoriteRecipesForMealUseCase getFavoriteRecipesForMealUseCase,
                                 Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase,
                                 Lazy<GetRecipesMatchingQueryUseCase> getRecipesMatchingQueryUseCase,
                                 Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                                 RecipeUIMapper recipeMapper,
                                 MealUIMapper mealMapper,
                                 DiaryEntryUIMapper diaryEntryMapper) {
        this.getMealByIdUseCase = getMealByIdUseCase;
        this.getFavoriteRecipesForMealUseCase = getFavoriteRecipesForMealUseCase;
        this.getRecipeByIdUseCase = getRecipeByIdUseCase;
        this.getRecipesMatchingQueryUseCase = getRecipesMatchingQueryUseCase;
        this.putDiaryEntryUseCase = putDiaryEntryUseCase;

        this.recipeMapper = recipeMapper;
        this.mealMapper = mealMapper;
        this.diaryEntryMapper = diaryEntryMapper;
    }

    @Override
    public void start() {
        view.showLoading();
    }

    @Override
    public void setView(RecipeSearchContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void getFavoriteRecipes(int selectedMeal) {
        compositeDisposable.add(getMealByIdUseCase.execute(new GetMealByIdUseCase.Input(selectedMeal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealOutput -> {
                    getFavoriteRecipesForMealUseCase.execute(new GetAllFavoriteRecipesForMealUseCase.Input(mealOutput.getMeal().getName()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(recipeOutput -> {
                                if (recipeOutput.getFavoriteRecipes().size() > 0){
                                    view.showRecyclerView();
                                    view.updateRecipeSearchResultList(recipeMapper.transformAll(recipeOutput.getFavoriteRecipes()));
                                    view.hidePlaceholder();
                                    view.hideLoading();
                                } else{
                                    view.hideRecyclerView();
                                    view.showPlaceholder();
                                    view.hideLoading();
                                }
                            });
                })
        );
    }

    @Override
    public void getRecipesMatchingQuery(String query) {
        view.showLoading();
        compositeDisposable.add(getRecipesMatchingQueryUseCase.get().execute(new GetRecipesMatchingQueryUseCase.Input(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipeOutput -> {
                    if(recipeOutput.getRecipes().size() > 0) {
                        view.showRecyclerView();
                        view.updateRecipeSearchResultList(recipeMapper.transformAll(recipeOutput.getRecipes()));
                        view.hidePlaceholder();
                        view.hideLoading();
                    } else {
                        view.hideRecyclerView();
                        view.showQueryWithoutResultPlaceholder();
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void onItemClicked(int id) {
        view.showRecipeDetails(id);
    }

    @Override
    public void onAddPortionClicked(int id) {
        view.showLoading();
        compositeDisposable.add(getMealByIdUseCase.execute(new GetMealByIdUseCase.Input(view.getSelectedMeal()))
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
                                    putDiaryEntryUseCase.get().execute(new PutDiaryEntryUseCase.Input(diaryEntryMapper.transformToDomain(new DiaryEntryDisplayModel(-1,
                                            mealModel,
                                            ingredient.getAmount() / recipe.getPortions(),
                                            ingredient.getUnit(),
                                            ingredient.getGrocery(),
                                            view.getSelectedDate()))));
                                }

                                view.hideLoading();
                                view.finishActivity();
                            });
                })
        );
    }
}
