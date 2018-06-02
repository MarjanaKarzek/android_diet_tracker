package de.karzek.diettracker.presentation.search.food;

import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.useCase.GetFavoriteFoodsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.GetMatchingGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.GetFavoriteFoodsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.GetMatchingGroceriesUseCase;
import de.karzek.diettracker.presentation.mapper.FavoriteGroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.FavoriteGroceryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;
import static de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.GetFavoriteFoodsUseCase.Input.FAVORITE_TYPE_FOOD;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class FoodSearchPresenter implements FoodSearchContract.Presenter {

    private FoodSearchContract.View view;

    private FavoriteGroceryUIMapper favoriteGroceryMapper;
    private GroceryUIMapper groceryMapper;

    private GetFavoriteFoodsUseCaseImpl getFavoriteFoodsUseCase;
    private GetMatchingGroceriesUseCaseImpl getMatchingGroceriesUseCase;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FoodSearchPresenter(GetFavoriteFoodsUseCaseImpl getFavoriteFoodsUseCase,
                               GetMatchingGroceriesUseCaseImpl getMatchingGroceriesUseCase,
                               FavoriteGroceryUIMapper favoriteGroceryMapper,
                               GroceryUIMapper groceryMapper) {
        this.getFavoriteFoodsUseCase = getFavoriteFoodsUseCase;
        this.getMatchingGroceriesUseCase = getMatchingGroceriesUseCase;
        this.favoriteGroceryMapper = favoriteGroceryMapper;
        this.groceryMapper = groceryMapper;
    }

    @Override
    public void start() {
        view.showLoading();
        Disposable subs = getFavoriteFoodsUseCase.execute(new GetFavoriteFoodsUseCase.Input(FAVORITE_TYPE_FOOD))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getFavoriteFoodsList().size() > 0) {
                        ArrayList<GroceryDisplayModel> groceries = extractGroceriesFromFavorites(favoriteGroceryMapper.transformAll(output.getFavoriteFoodsList()));
                        view.updateFoodSearchResultList(groceries);
                    } else
                        view.showPlaceholder();
                    view.hideLoading();
                });
        compositeDisposable.add(subs);
    }

    private ArrayList<GroceryDisplayModel> extractGroceriesFromFavorites(ArrayList<FavoriteGroceryDisplayModel> favoriteGroceryDisplayModels) {
        ArrayList<GroceryDisplayModel> groceries = new ArrayList<>();
        for (FavoriteGroceryDisplayModel item : favoriteGroceryDisplayModels) {
            groceries.add(item.getGrocery());
        }
        return groceries;
    }

    @Override
    public void setView(FoodSearchContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }


    @Override
    public void getFoodsMatchingQuery(String query) {
        view.showLoading();
        Disposable subs = getMatchingGroceriesUseCase.execute(new GetMatchingGroceriesUseCase.Input(TYPE_FOOD, query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getGroceryList().size() > 0) {
                        view.hidePlaceholder();
                        view.updateFoodSearchResultList(groceryMapper.transformAll(output.getGroceryList()));
                    } else
                        view.showQueryWithoutResultPlaceholder();
                    view.hideLoading();
                });
        compositeDisposable.add(subs);
    }

    @Override
    public void onItemClicked(int id) {
        view.showFoodDetails(id);
    }
}
