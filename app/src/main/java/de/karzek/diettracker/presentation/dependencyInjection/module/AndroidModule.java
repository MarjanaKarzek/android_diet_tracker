package de.karzek.diettracker.presentation.dependencyInjection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MarjanaKarzek on 28.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.04.2018
 */
@Module
public class AndroidModule {

    private final Context context;

    public AndroidModule(Application application) {
        this.context = application;
    }

    public AndroidModule(Context content) {
        this.context = content;
    }

    @Provides
    @Singleton
    public Context getContext() {
        return this.context;
    }

}
