package de.karzek.diettracker.presentation.search.food.foodDetail;

import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetAllDefaultUnitsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_SETTING_NUTRITION_DETAILS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

/**
 * Created by MarjanaKarzek on 02.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 02.06.2018
 */
public class FoodDetailsPresenter implements FoodDetailsContract.Presenter {

    FoodDetailsContract.View view;
    private int groceryId;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private GetGroceryByIdUseCaseImpl getGroceryByIdUseCase;
    private GetAllDefaultUnitsUseCaseImpl getDefaultUnitsUseCase;
    private GroceryUIMapper groceryMapper;
    private UnitUIMapper unitMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FoodDetailsPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                GetGroceryByIdUseCaseImpl getGroceryByIdUseCase,
                                GetAllDefaultUnitsUseCaseImpl getDefaultUnitsUseCase,
                                GroceryUIMapper groceryMapper,
                                UnitUIMapper unitMapper){
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.getGroceryByIdUseCase = getGroceryByIdUseCase;
        this.getDefaultUnitsUseCase = getDefaultUnitsUseCase;
        this.groceryMapper = groceryMapper;
        this.unitMapper = unitMapper;
    }

    @Override
    public void start() {
        if(sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
        } else {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        }

        Disposable subs = getGroceryByIdUseCase.execute(new GetGroceryByIdUseCase.Input(groceryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    GroceryDisplayModel grocery = groceryMapper.transform(output.getGrocery());
                    view.fillGroceryDetails(grocery);

                    getDefaultUnitsUseCase.execute(new GetAllDefaultUnitsUseCase.Input(grocery.getUnit_type()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(output2 -> {
                                view.initializeServingsSpinner(unitMapper.transformAll(output2.getUnitList()), grocery.getServings());
                            });
                });
        compositeDisposable.add(subs);
    }

    @Override
    public void setView(FoodDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void setGroceryId(int groceryId) {
        this.groceryId = groceryId;
    }

}
