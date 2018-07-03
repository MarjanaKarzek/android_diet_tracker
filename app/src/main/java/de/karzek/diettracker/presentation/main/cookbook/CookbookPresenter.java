package de.karzek.diettracker.presentation.main.cookbook;

import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.DeleteRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetAllRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetMatchingRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
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

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class CookbookPresenter implements CookbookContract.Presenter {

    private CookbookContract.View view;

    private GetAllRecipesUseCase getAllRecipesUseCase;
    private Lazy<DeleteRecipeByIdUseCase> deleteRecipeByIdUseCase;
    private Lazy<GetAllMealsUseCase> getAllMealsUseCase;
    private Lazy<GetMealByIdUseCase> getMealByIdUseCase;
    private Lazy<GetMatchingRecipesUseCase> getMatchingRecipesUseCase;
    private Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase;
    private Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase;

    private MealUIMapper mealMapper;
    private RecipeUIMapper recipeMapper;
    private DiaryEntryUIMapper diaryEntryMapper;

    private ArrayList<RecipeDisplayModel> currentRecipes = new ArrayList<>();

    private ArrayList<String> filterOptions = new ArrayList<>();
    private String sortOption = "title";
    private boolean asc = true;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CookbookPresenter(GetAllRecipesUseCase getAllRecipesUseCase,
                             Lazy<DeleteRecipeByIdUseCase> deleteRecipeByIdUseCase,
                             Lazy<GetAllMealsUseCase> getAllMealsUseCase,
                             Lazy<GetMealByIdUseCase> getMealByIdUseCase,
                             Lazy<GetMatchingRecipesUseCase> getMatchingRecipesUseCase,
                             Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase,
                             Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                             MealUIMapper mealMapper,
                             RecipeUIMapper recipeMapper,
                             DiaryEntryUIMapper diaryEntryMapper) {
        this.getAllRecipesUseCase = getAllRecipesUseCase;
        this.deleteRecipeByIdUseCase = deleteRecipeByIdUseCase;
        this.getAllMealsUseCase = getAllMealsUseCase;
        this.getMealByIdUseCase = getMealByIdUseCase;
        this.getMatchingRecipesUseCase = getMatchingRecipesUseCase;
        this.getRecipeByIdUseCase = getRecipeByIdUseCase;
        this.putDiaryEntryUseCase = putDiaryEntryUseCase;

        this.mealMapper = mealMapper;
        this.recipeMapper = recipeMapper;
        this.diaryEntryMapper = diaryEntryMapper;
    }

    @Override
    public void start() {
        view.showLoading();
        getAllRecipes();
    }

    private void getAllRecipes() {
        compositeDisposable.add(getAllRecipesUseCase.execute(new GetAllRecipesUseCase.Input(filterOptions, sortOption, asc))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getRecipes().size() == 0) {
                        currentRecipes = new ArrayList<>();
                        view.hideRecyclerView();
                        view.showPlaceholder();
                        view.hideLoading();
                    } else {
                        currentRecipes = recipeMapper.transformAll(output.getRecipes());
                        view.showRecyclerView();
                        view.updateRecyclerView(currentRecipes);
                        view.hidePlaceholder();
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void setView(CookbookContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void getRecipesMatchingQuery(String query) {
        view.showLoading();
        compositeDisposable.add(getMatchingRecipesUseCase.get().execute(new GetMatchingRecipesUseCase.Input(query, filterOptions, sortOption, asc))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getRecipes().size() > 0) {
                        view.hidePlaceholder();
                        view.hideQueryWithoutResultPlaceholder();
                        view.showRecyclerView();
                        view.updateRecyclerView(recipeMapper.transformAll(output.getRecipes()));
                    } else {
                        view.hideRecyclerView();
                        view.showQueryWithoutResultPlaceholder();
                    }
                    view.hideLoading();
                }));
    }

    @Override
    public void onItemClicked(int id) {
        view.showLoading();
        view.startRecipeDetailsActivity(id);
    }

    @Override
    public void onAddPortionClicked(int id) {
        compositeDisposable.add(getAllMealsUseCase.get().execute(new GetAllMealsUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {

                    ArrayList<String> meals = new ArrayList<>();
                    for (MealDisplayModel meal : mealMapper.transformAll(output.getMealList()))
                        meals.add(meal.getName());

                    view.showAddPortionForRecipeDialog(id, meals);
                })
        );
    }

    @Override
    public void onEditRecipeClicked(int id) {
        view.startEditRecipe(id);
    }

    @Override
    public void onDeleteRecipeClicked(int id) {
        view.showConfirmRecipeDeletionDialog(id);
    }

    @Override
    public void onDeleteRecipeConfirmed(int id) {
        view.showLoading();
        compositeDisposable.add(deleteRecipeByIdUseCase.get().execute(new DeleteRecipeByIdUseCase.Input(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    getAllRecipes();
                }));
    }

    @Override
    public void onFilterOptionSelected() {
        view.openFilterOptionsDialog(filterOptions);
    }

    @Override
    public void onSortOptionSelected() {
        view.openSortOptionsDialog(sortOption, asc);
    }

    @Override
    public void filterRecipesBy(ArrayList<String> filterOptions) {
        view.showLoading();
        this.filterOptions = filterOptions;
        getAllRecipes();
    }

    @Override
    public void sortRecipesBy(String sortOption, boolean asc) {
        view.showLoading();
        this.sortOption = sortOption;
        this.asc = asc;
        getAllRecipes();
    }

    @Override
    public void addPortionToDiary(int recipeId, int mealId, String date) {
        view.showLoading();
        compositeDisposable.add(getMealByIdUseCase.get().execute(new GetMealByIdUseCase.Input(mealId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealOutput -> {
                    MealDisplayModel mealModel = mealMapper.transform(mealOutput.getMeal());

                    getRecipeByIdUseCase.get().execute(new GetRecipeByIdUseCase.Input(recipeId))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(recipeOutput -> {
                                RecipeDisplayModel recipe = recipeMapper.transform(recipeOutput.getRecipe());

                                for (IngredientDisplayModel ingredient : recipe.getIngredients()) {
                                    putDiaryEntryUseCase.get().execute(new PutDiaryEntryUseCase.Input(diaryEntryMapper.transformToDomain(new DiaryEntryDisplayModel(-1,
                                            mealModel,
                                            ingredient.getAmount() / recipe.getPortions(),
                                            ingredient.getUnit(),
                                            ingredient.getGrocery(),
                                            date))));
                                }

                                view.hideLoading();
                                view.showRecipeAddedToast();
                            });
                }));
    }

}
