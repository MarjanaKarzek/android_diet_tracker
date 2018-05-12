package de.karzek.diettracker.presentation.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MarjanaKarzek on 28.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.04.2018
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponents();
    }

    protected abstract void setupActivityComponents();

    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

}
