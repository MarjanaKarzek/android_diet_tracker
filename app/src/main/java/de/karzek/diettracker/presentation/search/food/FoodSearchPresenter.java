package de.karzek.diettracker.presentation.search.food;

import java.util.ArrayList;

import de.karzek.diettracker.domain.interactor.useCase.GetFavoriteFoodsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.GetFavoriteFoodsUseCase;
import de.karzek.diettracker.presentation.mapper.FavoriteGroceryUIMapper;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.FavoriteGroceryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    private FavoriteGroceryUIMapper mapper;

    private GetFavoriteFoodsUseCaseImpl getFavoriteFoodsUseCase;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FoodSearchPresenter(GetFavoriteFoodsUseCaseImpl getFavoriteFoodsUseCase, FavoriteGroceryUIMapper mapper) {
        this.getFavoriteFoodsUseCase = getFavoriteFoodsUseCase;
        this.mapper = mapper;
    }

    @Override
    public void start() {
        view.showLoading();
        Disposable subs = getFavoriteFoodsUseCase.execute(new GetFavoriteFoodsUseCase.Input(FAVORITE_TYPE_FOOD))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getFavoriteFoodsList().size() > 0) {
                        ArrayList<GroceryDisplayModel> groceries = extractGroceriesFromFavorites(mapper.transformAll(output.getFavoriteFoodsList()));
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
    public void getFoodsMatchingName(String query) {
        GroceryDisplayModel model1 = new GroceryDisplayModel(0, 1, "Paprika", 1, 1, 1, 1, 1, new ArrayList<AllergenDisplayModel>(), new ArrayList<ServingDisplayModel>());
        GroceryDisplayModel model2 = new GroceryDisplayModel(1, 1, "Fleisch", 1, 1, 1, 1, 1, new ArrayList<AllergenDisplayModel>(), new ArrayList<ServingDisplayModel>());
        GroceryDisplayModel model3 = new GroceryDisplayModel(2, 1, "Milch", 1, 1, 1, 1, 1, new ArrayList<AllergenDisplayModel>(), new ArrayList<ServingDisplayModel>());
        GroceryDisplayModel model4 = new GroceryDisplayModel(3, 1, "Ei", 1, 1, 1, 1, 1, new ArrayList<AllergenDisplayModel>(), new ArrayList<ServingDisplayModel>());

        ArrayList<GroceryDisplayModel> foods = new ArrayList<>();
        foods.add(model1);
        foods.add(model2);
        foods.add(model3);
        foods.add(model4);

        view.updateFoodSearchResultList(foods);
    }

    @Override
    public void onItemClicked(int id) {
        view.showFoodDetails(id);
    }
}
