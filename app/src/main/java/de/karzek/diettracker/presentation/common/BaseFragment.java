package de.karzek.diettracker.presentation.common;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.karzek.diettracker.presentation.TrackerApplication;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public abstract class BaseFragment extends android.support.v4.app.Fragment {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentComponent();
    }

    protected abstract void setupFragmentComponent();

    @Override public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }

        TrackerApplication.get(getActivity()).watch(this);
    }
}
