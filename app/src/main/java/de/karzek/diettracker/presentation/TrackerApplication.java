package de.karzek.diettracker.presentation;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import de.karzek.diettracker.ConfigurationManager;
import de.karzek.diettracker.presentation.dependencyInjection.component.AppComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.AutomatedIngredientSearchComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.BarcodeScannerComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.GroceryDetailsComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.GrocerySearchComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.MainComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.OnboardingComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.RecipeDetailsComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.RecipeEditDetailsComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.RecipeManipulationComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.RecipeSearchComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent.SplashComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.CookbookComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.DaggerAppComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.DiaryComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.GenericDrinkComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.GenericMealComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.HomeComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.SettingsComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.EditAllergensDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.EditMealsDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.RecipeFilterOptionsDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.AndroidModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.AutomatedIngredientSearchModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.BarcodeScannerModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.GroceryDetailsModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.GrocerySearchModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.MainModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.OnboardingModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeDetailsModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeEditDetailsModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeManipulationModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeSearchModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.SplashModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.CookbookModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.DiaryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.GenericDrinkModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.GenericMealModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.HomeModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.SettingsModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.EditAllergensDialogModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.EditMealsDialogModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.RecipeFilterOptionsDialogModule;

/**
 * Created by MarjanaKarzek on 28.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.04.2018
 */
public class TrackerApplication extends Application {

    private AppComponent appComponent;

    //activities
    private SplashComponent splashComponent;
    private MainComponent mainComponent;
    private GrocerySearchComponent grocerySearchComponent;
    private GroceryDetailsComponent groceryDetailsComponent;
    private BarcodeScannerComponent barcodeScannerComponent;
    private RecipeManipulationComponent recipeManipulationComponent;
    private RecipeDetailsComponent recipeDetailsComponent;
    private RecipeSearchComponent recipeSearchComponent;
    private RecipeEditDetailsComponent recipeEditDetailsComponent;
    private AutomatedIngredientSearchComponent automatedIngredientSearchComponent;
    private OnboardingComponent onboardingComponent;

    //fragments
    private HomeComponent homeComponent;
    private DiaryComponent diaryComponent;
    private CookbookComponent cookbookComponent;
    private SettingsComponent settingsComponent;
    private GenericMealComponent genericMealComponent;
    private GenericDrinkComponent genericDrinkComponent;

    //dialogs
    private EditAllergensDialogComponent editAllergensDialogComponent;
    private EditMealsDialogComponent editMealsDialogComponent;
    private RecipeFilterOptionsDialogComponent recipeFilterOptionsDialogComponent;

    private RefWatcher refwatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        new ConfigurationManager(this);
        refwatcher = LeakCanary.install(this);

        appComponent = createAppComponent();
    }

    public static TrackerApplication get(Context context) {
        return (TrackerApplication) context.getApplicationContext();
    }

    public void watch(Fragment fragment) {
        refwatcher.watch(fragment);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    //Create Components
    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    //activities

    public SplashComponent createSplashComponent() {
        if (splashComponent != null) {
            return splashComponent;
        }
        splashComponent = appComponent.plus(new SplashModule());
        return splashComponent;
    }

    public MainComponent createMainComponent() {
        if (mainComponent != null) {
            return mainComponent;
        }
        mainComponent = appComponent.plus(new MainModule());
        return mainComponent;
    }

    public GrocerySearchComponent createGrocerySearchComponent() {
        if (grocerySearchComponent != null) {
            return grocerySearchComponent;
        }
        grocerySearchComponent = appComponent.plus(new GrocerySearchModule());
        return grocerySearchComponent;
    }

    public GroceryDetailsComponent createGroceryDetailsComponent() {
        if (groceryDetailsComponent != null) {
            return groceryDetailsComponent;
        }
        groceryDetailsComponent = appComponent.plus(new GroceryDetailsModule());
        return groceryDetailsComponent;
    }

    public BarcodeScannerComponent createBarcodeScannerComponent() {
        if (barcodeScannerComponent != null) {
            return barcodeScannerComponent;
        }
        barcodeScannerComponent = appComponent.plus(new BarcodeScannerModule());
        return barcodeScannerComponent;
    }

    public RecipeManipulationComponent createRecipeManipulationComponent() {
        if (recipeManipulationComponent != null) {
            return recipeManipulationComponent;
        }
        recipeManipulationComponent = appComponent.plus(new RecipeManipulationModule());
        return recipeManipulationComponent;
    }

    public RecipeDetailsComponent createRecipeDetailsComponent() {
        if (recipeDetailsComponent != null) {
            return recipeDetailsComponent;
        }
        recipeDetailsComponent = appComponent.plus(new RecipeDetailsModule());
        return recipeDetailsComponent;
    }

    public RecipeSearchComponent createRecipeSearchComponent() {
        if (recipeSearchComponent != null) {
            return recipeSearchComponent;
        }
        recipeSearchComponent = appComponent.plus(new RecipeSearchModule());
        return recipeSearchComponent;
    }

    public RecipeEditDetailsComponent createRecipeEditDetailsComponent() {
        if (recipeEditDetailsComponent != null) {
            return recipeEditDetailsComponent;
        }
        recipeEditDetailsComponent = appComponent.plus(new RecipeEditDetailsModule());
        return recipeEditDetailsComponent;
    }

    public AutomatedIngredientSearchComponent createAutomatedIngredientSearchComponent() {
        if (automatedIngredientSearchComponent != null) {
            return automatedIngredientSearchComponent;
        }
        automatedIngredientSearchComponent = appComponent.plus(new AutomatedIngredientSearchModule());
        return automatedIngredientSearchComponent;
    }

    public OnboardingComponent createOnboardingComponent() {
        if (onboardingComponent != null) {
            return onboardingComponent;
        }
        onboardingComponent = appComponent.plus(new OnboardingModule());
        return onboardingComponent;
    }

    //fragments

    public HomeComponent createHomeComponent() {
        if (homeComponent != null) {
            return homeComponent;
        }
        homeComponent = appComponent.plus(new HomeModule());
        return homeComponent;
    }

    public DiaryComponent createDiaryComponent() {
        if (diaryComponent != null) {
            return diaryComponent;
        }
        diaryComponent = appComponent.plus(new DiaryModule());
        return diaryComponent;
    }

    public CookbookComponent createCookbookComponent() {
        if (cookbookComponent != null) {
            return cookbookComponent;
        }
        cookbookComponent = appComponent.plus(new CookbookModule());
        return cookbookComponent;
    }

    public SettingsComponent createSettingsComponent() {
        if (settingsComponent != null) {
            return settingsComponent;
        }
        settingsComponent = appComponent.plus(new SettingsModule());
        return settingsComponent;
    }

    public GenericMealComponent createGenericMealComponent() {
        if (genericMealComponent != null) {
            return genericMealComponent;
        }
        genericMealComponent = appComponent.plus(new GenericMealModule());
        return genericMealComponent;
    }

    public GenericDrinkComponent createGenericDrinkComponent() {
        if (genericDrinkComponent != null) {
            return genericDrinkComponent;
        }
        genericDrinkComponent = appComponent.plus(new GenericDrinkModule());
        return genericDrinkComponent;
    }

    //dialogs

    public EditAllergensDialogComponent createEditAllergensDialogComponent() {
        if (editAllergensDialogComponent != null) {
            return editAllergensDialogComponent;
        }
        editAllergensDialogComponent = appComponent.plus(new EditAllergensDialogModule());
        return editAllergensDialogComponent;
    }

    public EditMealsDialogComponent createEditMealsDialogComponent() {
        if (editMealsDialogComponent != null) {
            return editMealsDialogComponent;
        }
        editMealsDialogComponent = appComponent.plus(new EditMealsDialogModule());
        return editMealsDialogComponent;
    }

    public RecipeFilterOptionsDialogComponent createRecipeFilterOptionsDialogComponent() {
        if (recipeFilterOptionsDialogComponent != null) {
            return recipeFilterOptionsDialogComponent;
        }
        recipeFilterOptionsDialogComponent = appComponent.plus(new RecipeFilterOptionsDialogModule());
        return recipeFilterOptionsDialogComponent;
    }

    //Release Components

    //activities

    public void releaseSplashComponent() {
        splashComponent = null;
    }

    public void releaseMainComponent() {
        mainComponent = null;
    }

    public void releaseGrocerySearchComponent() {
        grocerySearchComponent = null;
    }

    public void releaseGroceryDetailsComponent() {
        groceryDetailsComponent = null;
    }

    public void releaseBarcodeScannerComponent() {
        barcodeScannerComponent = null;
    }

    public void releaseRecipeManipulationComponent() {
        recipeManipulationComponent = null;
    }

    public void releaseRecipeDetailsComponent() {
        recipeDetailsComponent = null;
    }

    public void releaseRecipeSearchComponent() {
        recipeSearchComponent = null;
    }

    public void releaseRecipeEditDetailsComponent() {
        recipeEditDetailsComponent = null;
    }

    public void releaseAutomatedIngredientSearchComponent() {
        automatedIngredientSearchComponent = null;
    }

    public void releaseOnboardingComponent() {
        onboardingComponent = null;
    }

    //fragments

    public void releaseHomeComponent() {
        homeComponent = null;
    }

    public void releaseDiaryComponent() {
        diaryComponent = null;
    }

    public void releaseCookbookComponent() {
        cookbookComponent = null;
    }

    public void releaseSettingsComponent() {
        settingsComponent = null;
    }

    public void releaseGenericMealComponent() {
        genericMealComponent = null;
    }

    public void releaseGenericDrinkComponent() {
        genericDrinkComponent = null;
    }

    //dialogs

    public void releaseEditAllergensDialogComponent() {
        editAllergensDialogComponent = null;
    }

    public void releaseEditMealsDialogComponent() {
        editMealsDialogComponent = null;
    }

    public void releaseRecipeFilterOptionsDialogComponent() {
        recipeFilterOptionsDialogComponent = null;
    }

}
