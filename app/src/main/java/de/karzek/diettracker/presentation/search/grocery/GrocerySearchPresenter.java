package de.karzek.diettracker.presentation.search.grocery;

import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.GetFavoriteGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetMatchingGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetUnitByNameUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetMatchingGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByNameUseCase;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.FavoriteGroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.FavoriteGroceryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
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
public class GrocerySearchPresenter implements GrocerySearchContract.Presenter {

    private GrocerySearchContract.View view;

    private FavoriteGroceryUIMapper favoriteGroceryMapper;
    private GroceryUIMapper groceryMapper;
    private UnitUIMapper unitMapper;
    private DiaryEntryUIMapper diaryEntryMapper;

    private GetFavoriteGroceriesUseCase getFavoriteGroceriesUseCase;
    private Lazy<GetMatchingGroceriesUseCase> getMatchingGroceriesUseCase;
    private Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase;
    private Lazy<GetUnitByNameUseCase> getUnitByNameUseCase;
    private Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SharedPreferencesUtil sharedPreferencesUtil;

    public GrocerySearchPresenter(GetFavoriteGroceriesUseCase getFavoriteGroceriesUseCase,
                                  Lazy<GetMatchingGroceriesUseCase> getMatchingGroceriesUseCase,
                                  Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase,
                                  Lazy<GetUnitByNameUseCase> getUnitByNameUseCase,
                                  Lazy<PutDiaryEntryUseCase> putDiaryEntryUseCase,
                                  FavoriteGroceryUIMapper favoriteGroceryMapper,
                                  GroceryUIMapper groceryMapper,
                                  UnitUIMapper unitMapper,
                                  DiaryEntryUIMapper diaryEntryMapper,
                                  SharedPreferencesUtil sharedPreferencesUtil) {
        this.getFavoriteGroceriesUseCase = getFavoriteGroceriesUseCase;
        this.getMatchingGroceriesUseCase = getMatchingGroceriesUseCase;
        this.getGroceryByIdUseCase = getGroceryByIdUseCase;
        this.getUnitByNameUseCase = getUnitByNameUseCase;
        this.putDiaryEntryUseCase = putDiaryEntryUseCase;
        this.favoriteGroceryMapper = favoriteGroceryMapper;
        this.groceryMapper = groceryMapper;
        this.unitMapper = unitMapper;
        this.diaryEntryMapper = diaryEntryMapper;
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void start() {
        view.showLoading();
    }

    @Override
    public void getFavoriteGroceries(int type){
        view.showLoading();
        Disposable subs = getFavoriteGroceriesUseCase.execute(new GetFavoriteGroceriesUseCase.Input(type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getFavoriteFoodsList().size() > 0) {
                        ArrayList<GroceryDisplayModel> groceries = extractGroceriesFromFavorites(favoriteGroceryMapper.transformAll(output.getFavoriteFoodsList()));
                        view.updateFoodSearchResultList(groceries);
                        view.showRecyclerView();
                        view.hidePlaceholder();
                    } else {
                        view.hideRecyclerView();
                        view.showPlaceholder();
                    }
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
    public void setView(GrocerySearchContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void getGroceriesMatchingQuery(String query, int type) {
        view.showLoading();
        Disposable subs = getMatchingGroceriesUseCase.get().execute(new GetMatchingGroceriesUseCase.Input(type, query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getGroceryList().size() > 0) {
                        view.hidePlaceholder();
                        view.hideQueryWithoutResultPlaceholder();
                        view.showRecyclerView();
                        view.updateFoodSearchResultList(groceryMapper.transformAll(output.getGroceryList()));
                    } else {
                        view.hideRecyclerView();
                        view.showQueryWithoutResultPlaceholder();
                    }
                    view.hideLoading();
                });
        compositeDisposable.add(subs);
    }

    @Override
    public void onBarcodeScannerClicked() {
        view.startBarcodeScannerActivity();
    }

    @Override
    public void onItemClicked(int id) {
        view.showGroceryDetails(id);
    }

    @Override
    public void onAddBottleClicked(int id) {
        view.showLoading();

        compositeDisposable.add(getGroceryByIdUseCase.get().execute(new GetGroceryByIdUseCase.Input(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        output -> {
                            getUnitByNameUseCase.get().execute(new GetUnitByNameUseCase.Input("ml"))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            output2 -> {
                                                putDiaryEntryUseCase.get().execute(new PutDiaryEntryUseCase.Input(diaryEntryMapper.transformToDomain(new DiaryEntryDisplayModel(Constants.INVALID_ENTITY_ID,
                                                        sharedPreferencesUtil.getFloat(KEY_BOTTLE_VOLUME, 0.0f),
                                                        unitMapper.transform(output2.getUnit()),
                                                        groceryMapper.transform(output.getGrocery()),
                                                        view.getSelectedDate()))))
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread());
                                            }
                                    );
                        }
                )
        );
        view.finishActivity();
    }

    @Override
    public void onAddGlassClicked(int id) {
        view.showLoading();

        compositeDisposable.add(getGroceryByIdUseCase.get().execute(new GetGroceryByIdUseCase.Input(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        output -> {
                            getUnitByNameUseCase.get().execute(new GetUnitByNameUseCase.Input("ml"))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            output2 -> {
                                                putDiaryEntryUseCase.get().execute(new PutDiaryEntryUseCase.Input(diaryEntryMapper.transformToDomain(new DiaryEntryDisplayModel(-1,
                                                        sharedPreferencesUtil.getFloat(KEY_GLASS_VOLUME, 0.0f),
                                                        unitMapper.transform(output2.getUnit()),
                                                        groceryMapper.transform(output.getGrocery()),
                                                        view.getSelectedDate()))))
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread());
                                            }
                                    );
                        }
                )
        );

        view.finishActivity();
    }
}
