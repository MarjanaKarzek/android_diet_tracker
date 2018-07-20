package de.karzek.diettracker;

import android.app.Application;

import de.karzek.diettracker.util.NotLoggingTree;
import timber.log.Timber;

/**
 * Created by MarjanaKarzek on 20.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 20.04.2018
 */

public class ConfigurationManager {

    public ConfigurationManager(Application application){
        initTimber();
    }

    private static void initTimber(){
        Timber.plant(new NotLoggingTree());
    }
}
