package de.karzek.diettracker.presentation.main.cookbook.recipeDetails;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe.GetFavoriteStateForRecipeByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetFavoriteStateForRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.PutFavoriteRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.RemoveFavoriteRecipeByTitleUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.model.FavoriteRecipeDomainModel;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter {

    private RecipeDetailsContract.View view;

    private GetRecipeByIdUseCase getRecipeByIdUseCase;
    private Lazy<PutFavoriteRecipeUseCase> putFavoriteRecipeUseCase;
    private Lazy<RemoveFavoriteRecipeByTitleUseCase> removeFavoriteRecipeByTitleUseCase;
    private Lazy<GetFavoriteStateForRecipeByIdUseCase> getFavoriteStateForRecipeByIdUseCase;
    private RecipeUIMapper recipeMapper;

    private int recipeId;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RecipeDetailsPresenter(GetRecipeByIdUseCase getRecipeByIdUseCase,
                                  Lazy<PutFavoriteRecipeUseCase> putFavoriteRecipeUseCase,
                                  Lazy<RemoveFavoriteRecipeByTitleUseCase> removeFavoriteRecipeByTitleUseCase,
                                  Lazy<GetFavoriteStateForRecipeByIdUseCase> getFavoriteStateForRecipeByIdUseCase,
                                  RecipeUIMapper recipeMapper){
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
                    view.fillRecipeDetails(recipeMapper.transform(output.getRecipe()));
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
    public void onFavoriteGroceryClicked(boolean checked, RecipeDisplayModel recipe) {
        if(checked){
            Disposable subs = putFavoriteRecipeUseCase.get().execute(new PutFavoriteRecipeUseCase.Input(new FavoriteRecipeDomainModel(-1, recipeMapper.transformToDomain(recipe))))
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
}
