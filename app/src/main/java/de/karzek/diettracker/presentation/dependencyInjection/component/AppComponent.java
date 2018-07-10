package de.karzek.diettracker.presentation.dependencyInjection.component;

import javax.inject.Singleton;

import dagger.Component;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.EditAllergensDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.EditMealsDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.RecipeFilterOptionsDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.AndroidModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.AppModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.CookbookModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.DiaryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.GenericDrinkModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.GenericMealModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.HomeModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.BarcodeScannerModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.FoodDetailsModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.FoodSearchModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.MainModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.SettingsModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeDetailsModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeEditDetailsModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeManipulationModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.RecipeSearchModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.SplashModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.EditAllergensDialogModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.EditMealsDialogModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.RecipeFilterOptionsDialogModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.AllergenModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.DiaryEntryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.FavoriteRecipeModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.MealModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.RecipeModule;
import de.karzek.diettracker.presentation.main.MainActivity;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.RecipeDetailsActivity;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationActivity;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchActivity;
import de.karzek.diettracker.presentation.search.grocery.barcodeScanner.BarcodeScannerActivity;
import de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsActivity;
import de.karzek.diettracker.presentation.search.recipe.RecipeSearchActivity;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.RecipeEditDetailsActivity;
import de.karzek.diettracker.presentation.splash.SplashActivity;

/**
 * Created by MarjanaKarzek on 28.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.04.2018
 */
@Singleton
@Component(modules = {AppModule.class,
        AndroidModule.class,
        SplashModule.class,
        MainModule.class,
        FoodSearchModule.class,
        FoodDetailsModule.class,
        BarcodeScannerModule.class,
        RecipeManipulationModule.class,
        RecipeDetailsModule.class,
        RecipeSearchModule.class,
        RecipeEditDetailsModule.class,
        FavoriteRecipeModule.class,
        AllergenModule.class,
        RecipeModule.class,
        MealModule.class,
        DiaryEntryModule.class
})
public interface AppComponent {

    HomeComponent plus(HomeModule module);

    DiaryComponent plus(DiaryModule module);

    CookbookComponent plus(CookbookModule module);

    SettingsComponent plus(SettingsModule module);

    GenericMealComponent plus(GenericMealModule module);

    GenericDrinkComponent plus(GenericDrinkModule module);

    EditAllergensDialogComponent plus(EditAllergensDialogModule module);

    EditMealsDialogComponent plus(EditMealsDialogModule module);

    RecipeFilterOptionsDialogComponent plus(RecipeFilterOptionsDialogModule module);

    void inject(SplashActivity activity);

    void inject(MainActivity activity);

    void inject(GrocerySearchActivity activity);

    void inject(GroceryDetailsActivity activity);

    void inject(BarcodeScannerActivity activity);

    void inject(RecipeManipulationActivity activity);

    void inject(RecipeDetailsActivity activity);

    void inject(RecipeSearchActivity activity);

    void inject(RecipeEditDetailsActivity activity);

}
