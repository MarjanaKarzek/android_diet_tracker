package de.karzek.diettracker.presentation.splash;

import java.util.ArrayList;

import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.PutAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.PutAllGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.PutAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.serving.PutAllServingsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.PutAllUnitsUseCase;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.mapper.ServingUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
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

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_DRINK;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_LIQUID;
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

    private PutAllUnitsUseCase putAllUnitsUseCase;
    private PutAllServingsUseCase putAllServingsUseCase;
    private PutAllAllergensUseCase putAllAllergensUseCase;
    private PutAllGroceriesUseCase putAllGroceriesUseCase;
    private PutAllMealsUseCase putAllMealsUseCase;
    private SharedPreferencesManager sharedPreferencesManager;

    private UnitUIMapper unitMapper;
    private ServingUIMapper servingMapper;
    private AllergenUIMapper allergenMapper;
    private GroceryUIMapper groceryMapper;
    private MealUIMapper mealMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ArrayList<GroceryDisplayModel> groceries = new ArrayList<>();
    private ArrayList<UnitDisplayModel> units = new ArrayList<>();
    private ArrayList<ServingDisplayModel> servings = new ArrayList<>();
    private ArrayList<MealDisplayModel> meals = new ArrayList<>();
    private ArrayList<AllergenDisplayModel> allergens = new ArrayList<>();

    public SplashPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                           PutAllUnitsUseCase putAllUnitsUseCase,
                           PutAllServingsUseCase putAllServingsUseCase,
                           PutAllAllergensUseCase putAllAllergensUseCase,
                           PutAllGroceriesUseCase putAllGroceriesUseCase,
                           PutAllMealsUseCase putAllMealsUseCase,
                           SharedPreferencesManager sharedPreferencesManager,
                           UnitUIMapper unitMapper,
                           ServingUIMapper servingMapper,
                           AllergenUIMapper allergenMapper,
                           GroceryUIMapper groceryMapper,
                           MealUIMapper mealMapper) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;

        this.putAllUnitsUseCase = putAllUnitsUseCase;
        this.putAllServingsUseCase = putAllServingsUseCase;
        this.putAllAllergensUseCase = putAllAllergensUseCase;
        this.putAllGroceriesUseCase = putAllGroceriesUseCase;
        this.putAllMealsUseCase = putAllMealsUseCase;
        this.sharedPreferencesManager = sharedPreferencesManager;

        this.unitMapper = unitMapper;
        this.servingMapper = servingMapper;
        this.allergenMapper = allergenMapper;
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
        paprikaServings.add(paprika_0);

        //allergens
        AllergenDisplayModel eggs = new AllergenDisplayModel(0, "Eier");
        AllergenDisplayModel peanuts = new AllergenDisplayModel(1, "Erdnüsse");
        AllergenDisplayModel fish = new AllergenDisplayModel(2, "Fisch");
        AllergenDisplayModel fructose = new AllergenDisplayModel(3, "Fruktose");
        AllergenDisplayModel gluten = new AllergenDisplayModel(4, "Gluten");
        AllergenDisplayModel hazelnut = new AllergenDisplayModel(5, "Haselnüsse");
        AllergenDisplayModel crustaceans = new AllergenDisplayModel(6, "Krebstiere");
        AllergenDisplayModel lactose = new AllergenDisplayModel(7, "Laktose");
        AllergenDisplayModel nuts = new AllergenDisplayModel(8, "Schalenfrüchte");
        AllergenDisplayModel celery = new AllergenDisplayModel(9, "Sellerie");
        AllergenDisplayModel mustard = new AllergenDisplayModel(10, "Senf");
        AllergenDisplayModel sesame = new AllergenDisplayModel(11, "Sesamsamen");
        AllergenDisplayModel soy = new AllergenDisplayModel(12, "Soja");
        AllergenDisplayModel sulfites = new AllergenDisplayModel(13, "Sulfite");
        AllergenDisplayModel walnuts = new AllergenDisplayModel(14, "Walnüsse");

        allergens.add(eggs);
        allergens.add(peanuts);
        allergens.add(fish);
        allergens.add(fructose);
        allergens.add(gluten);
        allergens.add(hazelnut);
        allergens.add(crustaceans);
        allergens.add(lactose);
        allergens.add(nuts);
        allergens.add(celery);
        allergens.add(mustard);
        allergens.add(sesame);
        allergens.add(soy);
        allergens.add(sulfites);
        allergens.add(walnuts);

        ArrayList<AllergenDisplayModel> colaAllergens = new ArrayList<>();
        colaAllergens.add(fructose);

        ArrayList<AllergenDisplayModel> tilsiterAllergens = new ArrayList<>();
        tilsiterAllergens.add(lactose);

        //groceries
        groceries.add(new GroceryDisplayModel(-1, "0", "Placeholder", 0.0f, 0.0f, 0.0f, 0.0f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(0, "0", "Wasser", 0.0f, 0.0f, 0.0f, 0.0f, TYPE_DRINK, TYPE_LIQUID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(1, "0", "Brokkoli", 0.34f, 0.038f, 0.027f, 0.002f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), brokkoliServings));
        groceries.add(new GroceryDisplayModel(2, "0", "Rote Paprika", 0.43f, 0.013f, 0.064f, 0.005f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), paprikaServings));
        groceries.add(new GroceryDisplayModel(3, "0", "Gelbe Paprika", 0.30f, 0.01f, 0.05f, 0.005f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), paprikaServings));
        groceries.add(new GroceryDisplayModel(4, "0", "Coca Cola", 0.42f, 0.0f, 0.106f, 0.0f, TYPE_DRINK, TYPE_LIQUID, colaAllergens, new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(5, "29065806", "Tilsiter (Hofburger)", 3.52f, 0.25f, 0.001f, 0.28f, TYPE_FOOD, TYPE_SOLID, tilsiterAllergens, new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(6, "20462321", "Tex Mex (EL TEQUITO)", 1.31f, 0.04f, 0.15f, 0.05f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(7, "0", "Apfel", 0.54f, 0.003f, 0.144f, 0.001f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(8, "0", "Butter", 7.41f, 0.007f, 0.006f, 0.83f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(9, "0", "Zucker", 4.0f, 0.f, 1.f, 0.f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(10, "0", "Vanillezucker", 4.05f, 0.f, 0.998f, 0.f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(11, "0", "Salz", 0.f, 0.f, 0.f, 0.f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(12, "0", "Zitronen Aroma", 7.7f, 0.f, 0.f, 0.855f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(13, "0", "Ei (Huhn)", 1.37f, 0.119f, 0.015f, 0.093f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(14, "0", "Weizenmehl", 3.48f, 0.1f, 0.723f, 0.001f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(15, "0", "Backpulver", 1.f, 0.001f, 0.25f, 0.f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(16, "0", "Milch (1,5% Fett)", 0.47f, 0.034f, 0.049f, 0.015f, TYPE_DRINK, TYPE_LIQUID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(17, "0", "Haferflocken (kernig)", 3.7f, 0.135f, 0.587f, 0.07f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(18, "0", "Cashewkerne", 5.71f, 0.172f, 0.305f, 0.422f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(19, "0", "Mandeln", 6.11f, 0.24f, 0.057f, 0.53f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(20, "0", "Ahornsirup", 2.66f, 0.f, 0.664f, 0.f, TYPE_FOOD, TYPE_LIQUID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(21, "0", "Joghurt (3,5% Fett)", 0.7f, 0.041f, 0.048f, 0.036f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(22, "0", "Himbeeren", 0.43f, 0.013f, 0.048f, 0.003f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(23, "0", "Weizenbrötchen", 0.24f, 0.074f, 0.486f, 0.013f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(24, "0", "Salami", 3.97f, 0.203f, 0.04f, 0.356f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(), new ArrayList<>()));

        // meals
        meals.add(new MealDisplayModel(0, "Frühstück", "08:00:00", "10:00:00"));
        meals.add(new MealDisplayModel(1, "Mittagessen", "12:00:00", "13:00:00"));
        meals.add(new MealDisplayModel(2, "Abendessen", "18:00:00", "20:00:00"));
        meals.add(new MealDisplayModel(3, "Snack", "16:00:00", "18:00:00"));
    }

    @Override
    public void start() {
        if (!sharedPreferencesUtil.getBoolean(KEY_APP_INITIALIZED, false)) {
            sharedPreferencesManager.initializeStandardValues();
            compositeDisposable.add(Observable.zip(
                    putAllUnitsUseCase.execute(new PutAllUnitsUseCase.Input(unitMapper.transformAllToDomain(units))),
                    putAllServingsUseCase.execute(new PutAllServingsUseCase.Input(servingMapper.transformAllToDomain(servings))),
                    putAllAllergensUseCase.execute(new PutAllAllergensUseCase.Input(allergenMapper.transformAllToDomain(allergens))),
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
                    ));
        } else
            view.startMainActivity();
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
