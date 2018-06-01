package de.karzek.diettracker;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        initializeRealmDatabase(application);
    }

    private static void initializeLeakCanary(Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        LeakCanary.install(application);
    }

    private static void initializeRealmDatabase(Application application) {
        Realm.init(application);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("database.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
