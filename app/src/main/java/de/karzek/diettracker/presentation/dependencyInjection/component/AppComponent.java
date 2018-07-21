package de.karzek.diettracker.presentation.dependencyInjection.component;

import javax.inject.Singleton;

import dagger.Component;
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
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.EditAllergensDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.EditMealsDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.RecipeFilterOptionsDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.CookbookComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.DiaryComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.GenericDrinkComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.GenericMealComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.HomeComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent.SettingsComponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.AndroidModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.AppModule;
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
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.EditAllergensDialogModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.EditMealsDialogModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.dialogModules.RecipeFilterOptionsDialogModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.AllergenModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.DiaryEntryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.FavoriteGroceryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.FavoriteRecipeModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.GroceryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.MealModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.RecipeModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.ServingModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.featureModule.UnitModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.CookbookModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.DiaryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.GenericDrinkModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.GenericMealModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.HomeModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.SettingsModule;

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
        AllergenModule.class,
        DiaryEntryModule.class,
        FavoriteGroceryModule.class,
        FavoriteRecipeModule.class,
        GroceryModule.class,
        MealModule.class,
        RecipeModule.class,
        ServingModule.class,
        UnitModule.class
})
public interface AppComponent {

    //activities

    SplashComponent plus(SplashModule module);

    MainComponent plus(MainModule module);

    GrocerySearchComponent plus(GrocerySearchModule module);

    GroceryDetailsComponent plus(GroceryDetailsModule module);

    BarcodeScannerComponent plus(BarcodeScannerModule module);

    RecipeManipulationComponent plus(RecipeManipulationModule module);

    RecipeDetailsComponent plus(RecipeDetailsModule module);

    RecipeSearchComponent plus(RecipeSearchModule module);

    RecipeEditDetailsComponent plus(RecipeEditDetailsModule module);

    AutomatedIngredientSearchComponent plus(AutomatedIngredientSearchModule module);

    OnboardingComponent plus(OnboardingModule module);

    //fragments

    HomeComponent plus(HomeModule module);

    DiaryComponent plus(DiaryModule module);

    CookbookComponent plus(CookbookModule module);

    SettingsComponent plus(SettingsModule module);

    GenericMealComponent plus(GenericMealModule module);

    GenericDrinkComponent plus(GenericDrinkModule module);

    //dialogs

    EditAllergensDialogComponent plus(EditAllergensDialogModule module);

    EditMealsDialogComponent plus(EditMealsDialogModule module);

    RecipeFilterOptionsDialogComponent plus(RecipeFilterOptionsDialogModule module);

}
