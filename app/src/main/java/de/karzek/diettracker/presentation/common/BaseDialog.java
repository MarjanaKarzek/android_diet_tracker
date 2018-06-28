package de.karzek.diettracker.presentation.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;

import de.karzek.diettracker.presentation.TrackerApplication;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public abstract class BaseDialog extends AppCompatDialogFragment {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDialogComponent();
    }

    protected abstract void setupDialogComponent();

    @Override public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }

        TrackerApplication.get(getActivity()).watch(this);
    }
}
