package de.karzek.diettracker.presentation.main.cookbook;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetMatchingGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.DeleteRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetAllRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetMatchingRecipesUseCase;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
    private Lazy<GetMatchingRecipesUseCase> getMatchingRecipesUseCase;

    private RecipeUIMapper mapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CookbookPresenter(GetAllRecipesUseCase getAllRecipesUseCase,
                             Lazy<DeleteRecipeByIdUseCase> deleteRecipeByIdUseCase,
                             Lazy<GetMatchingRecipesUseCase> getMatchingRecipesUseCase,
                             RecipeUIMapper mapper){
        this.getAllRecipesUseCase = getAllRecipesUseCase;
        this.deleteRecipeByIdUseCase = deleteRecipeByIdUseCase;
        this.getMatchingRecipesUseCase = getMatchingRecipesUseCase;

        this.mapper = mapper;
    }

    @Override
    public void start() {
        view.showLoading();
        getAllRecipes();
    }

    private void getAllRecipes() {
        compositeDisposable.add(getAllRecipesUseCase.execute(new GetAllRecipesUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if(output.getRecipes().size() == 0){
                        view.hideRecyclerView();
                        view.showPlaceholder();
                        view.hideLoading();
                    } else {
                        view.showRecyclerView();
                        view.updateRecyclerView(mapper.transformAll(output.getRecipes()));
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
        compositeDisposable.add(getMatchingRecipesUseCase.get().execute(new GetMatchingRecipesUseCase.Input(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getRecipes().size() > 0) {
                        view.hidePlaceholder();
                        view.hideQueryWithoutResultPlaceholder();
                        view.showRecyclerView();
                        view.updateRecyclerView(mapper.transformAll(output.getRecipes()));
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
    public void onDeleteRecipeConfirmed(int id){
        view.showLoading();
        compositeDisposable.add(deleteRecipeByIdUseCase.get().execute(new DeleteRecipeByIdUseCase.Input(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    getAllRecipes();
                }));
    }
}
