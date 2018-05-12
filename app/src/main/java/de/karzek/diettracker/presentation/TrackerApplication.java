package de.karzek.diettracker.presentation;

import android.app.Application;
import android.content.Context;

import de.karzek.diettracker.ConfigurationManager;
import de.karzek.diettracker.presentation.dependencyInjection.component.AppComponent;
import de.karzek.diettracker.presentation.dependencyInjection.component.DaggerAppComponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.AndroidModule;

/**
 * Created by MarjanaKarzek on 28.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.04.2018
 */
public class TrackerApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate(){
        super.onCreate();

        new ConfigurationManager(this);

        appComponent = createAppComponent();

    }

    public static TrackerApplication get(Context context) {
        return (TrackerApplication) context.getApplicationContext();
    }

    //Create Components

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    //Get Components

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
