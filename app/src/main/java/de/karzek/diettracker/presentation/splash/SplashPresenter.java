package de.karzek.diettracker.presentation.splash;

import java.util.ArrayList;

import de.karzek.diettracker.domain.interactor.manager.InitializeSharedPreferencesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.PutAllGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.InitializeSharedPreferencesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.meal.PutAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.serving.PutAllServingsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.unit.PutAllUnitsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.PutAllGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.PutAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.serving.PutAllServingsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.PutAllUnitsUseCase;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.ServingUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_SOLID;
import static de.karzek.diettracker.data.cache.model.UnitEntity.UNIT_TYPE_LIQUID;
import static de.karzek.diettracker.data.cache.model.UnitEntity.UNIT_TYPE_SOLID;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_APP_INITIALIZED;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View view;

    private SharedPreferencesUtil sharedPreferencesUtil;

    private PutAllUnitsUseCaseImpl putAllUnitsUseCase;
    private PutAllServingsUseCaseImpl putAllServingsUseCase;
    private PutAllGroceriesUseCaseImpl putAllGroceriesUseCase;
    private PutAllMealsUseCaseImpl putAllMealsUseCase;
    private InitializeSharedPreferencesUseCaseImpl initializeSharedPreferencesUseCase;

    private UnitUIMapper unitMapper;
    private ServingUIMapper servingMapper;
    private GroceryUIMapper groceryMapper;
    private MealUIMapper mealMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ArrayList<GroceryDisplayModel> groceries = new ArrayList<>();
    private ArrayList<UnitDisplayModel> units = new ArrayList<>();
    private ArrayList<ServingDisplayModel> servings = new ArrayList<>();
    private ArrayList<MealDisplayModel> meals = new ArrayList<>();

    public SplashPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                           PutAllUnitsUseCaseImpl putAllUnitsUseCase,
                           PutAllServingsUseCaseImpl putAllServingsUseCase,
                           PutAllGroceriesUseCaseImpl putAllGroceriesUseCase,
                           PutAllMealsUseCaseImpl putAllMealsUseCase,
                           InitializeSharedPreferencesUseCaseImpl initializeSharedPreferencesUseCase,
                           UnitUIMapper unitMapper,
                           ServingUIMapper servingMapper,
                           GroceryUIMapper groceryMapper,
                           MealUIMapper mealMapper) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;

        this.putAllUnitsUseCase = putAllUnitsUseCase;
        this.putAllServingsUseCase = putAllServingsUseCase;
        this.putAllGroceriesUseCase = putAllGroceriesUseCase;
        this.putAllMealsUseCase = putAllMealsUseCase;
        this.initializeSharedPreferencesUseCase = initializeSharedPreferencesUseCase;

        this.unitMapper = unitMapper;
        this.servingMapper = servingMapper;
        this.groceryMapper = groceryMapper;
        this.mealMapper = mealMapper;
    }

    public void init() {

        // units
        UnitDisplayModel unit_g = new UnitDisplayModel(0, "g", 1, UNIT_TYPE_SOLID);
        UnitDisplayModel unit_kg = new UnitDisplayModel(1, "kg", 1000, UNIT_TYPE_SOLID);

        UnitDisplayModel unit_ml = new UnitDisplayModel(2, "ml", 1, UNIT_TYPE_LIQUID);
        UnitDisplayModel unit_l = new UnitDisplayModel(3, "l", 1000, UNIT_TYPE_LIQUID);

        units.add(unit_g);
        units.add(unit_kg);
        units.add(unit_ml);
        units.add(unit_l);

        //servings

        ServingDisplayModel brokkoli_0 = new ServingDisplayModel(0, "kleiner Kopf", 150, unit_g);
        ServingDisplayModel paprika_0 = new ServingDisplayModel(1, "kleine Paprika", 100, unit_g);

        servings.add(brokkoli_0);
        servings.add(paprika_0);

        ArrayList<ServingDisplayModel> brokkoliServings = new ArrayList<>();
        brokkoliServings.add(brokkoli_0);

        ArrayList<ServingDisplayModel> paprikaServings = new ArrayList<>();
        brokkoliServings.add(paprika_0);

        //groceries

        groceries.add(new GroceryDisplayModel(0, 0, "Brokkoli", 0.34f, 0.038f, 0.027f, 0.002f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), brokkoliServings));
        groceries.add(new GroceryDisplayModel(1, 0, "Rote Paprika", 0.43f, 0.013f, 0.064f, 0.005f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), paprikaServings));
        groceries.add(new GroceryDisplayModel(2, 0, "Gelbe Paprika", 0.30f, 0.01f, 0.05f, 0.005f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), paprikaServings));

        // meals
        meals.add(new MealDisplayModel(0, "Frühstück", "", ""));
        meals.add(new MealDisplayModel(1, "Mittagessen", "", ""));
        meals.add(new MealDisplayModel(2, "Abendessen", "", ""));
        meals.add(new MealDisplayModel(3, "Snack", "", ""));
    }

    @Override
    public void start() {
        if (!sharedPreferencesUtil.getBoolean(KEY_APP_INITIALIZED, false)) {
            Disposable subs = Observable.zip(
                    initializeSharedPreferencesUseCase.execute(new InitializeSharedPreferencesUseCase.Input()),
                    putAllUnitsUseCase.execute(new PutAllUnitsUseCase.Input(unitMapper.transformAllToDomain(units))),
                    putAllServingsUseCase.execute(new PutAllServingsUseCase.Input(servingMapper.transformAllToDomain(servings))),
                    putAllGroceriesUseCase.execute(new PutAllGroceriesUseCase.Input(groceryMapper.transformAllToDomain(groceries))),
                    putAllMealsUseCase.execute(new PutAllMealsUseCase.Input(mealMapper.transformAllToDomain(meals))),
                    (output1, output2, output3, output4, output5) -> (output1.getStatus() + output2.getStatus() + output3.getStatus() + output4.getStatus() + output5.getStatus()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                                if (output == 0)
                                    view.startMainActivity();
                                //else
                                //todo handle error
                            }
                    );
            compositeDisposable.add(subs);
        }
    }

    @Override
    public void setView(SplashContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }
}
