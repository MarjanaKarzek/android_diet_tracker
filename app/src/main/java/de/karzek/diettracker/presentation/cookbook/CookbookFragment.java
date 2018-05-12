package de.karzek.diettracker.presentation.cookbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseFragment;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class CookbookFragment extends BaseFragment implements CookbookContract.View{

    @Inject CookbookContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_cookbook, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        presenter.start();
    }

    @Override
    protected void setupFragmentComponent() {
        TrackerApplication.get(getContext()).createCookbookComponent().inject(this);
    }

    @Override
    public void setPresenter(CookbookContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(getContext()).releaseCookbookComponent();
    }
}
