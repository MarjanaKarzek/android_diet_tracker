package de.karzek.diettracker.presentation.main.cookbook;

import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetAllRecipesUseCase;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
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

    private RecipeUIMapper mapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CookbookPresenter(GetAllRecipesUseCase getAllRecipesUseCase,
                             RecipeUIMapper mapper){
        this.getAllRecipesUseCase = getAllRecipesUseCase;

        this.mapper = mapper;
    }

    @Override
    public void start() {
        view.showLoading();
        compositeDisposable.add(getAllRecipesUseCase.execute(new GetAllRecipesUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if(output.getRecipes().size() == 0){
                        view.hideRecyclerView();
                        view.showPlaceholder();
                        view.hideLoading();
                    } else {
                        view.hidePlaceholder();
                        view.updateRecyclerView(mapper.transformAll(output.getRecipes()));
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

    }

    @Override
    public void onItemClicked(int id) {

    }

    @Override
    public void onAddPortionClicked(int id) {

    }

    @Override
    public void onEditRecipeClicked(int id) {

    }

    @Override
    public void onDeleteRecipeClicked(int id) {

    }
}
