package de.karzek.diettracker.presentation;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import de.karzek.diettracker.ConfigurationManager;
import de.karzek.diettracker.presentation.dependencyInjection.component.AppComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.CookbookComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.DaggerAppComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.DiaryComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.GenericDrinkComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.GenericMealComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.HomeComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.SettingsComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.EditAllergensDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.EditMealsDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.dialogComponent.RecipeFilterOptionsDialogComponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.AndroidModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.CookbookModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.DiaryModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.GenericDrinkModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.GenericMealModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.HomeModule;
import de.karzek.diettracker.presentation.dependencyInjection.module.SettingsModule;
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

    private HomeComponent homeComponent;
    private DiaryComponent diaryComponent;
    private CookbookComponent cookbookComponent;
    private SettingsComponent settingsComponent;
    private GenericMealComponent genericMealComponent;
    private GenericDrinkComponent genericDrinkComponent;
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
