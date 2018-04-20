package de.karzek.diettracker;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by MarjanaKarzek on 20.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 20.04.2018
 */

public class ConfigurationManager {

    public ConfigurationManager(Application application) {
        initializeLeakCanary(application);
    }

    private static void initializeLeakCanary(Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        LeakCanary.install(application);
    }
}
