package de.karzek.diettracker.presentation.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.main.MainActivity;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class SplashActivity extends BaseActivity implements SplashContract.View{

    @Inject SplashContract.Presenter presenter;

    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter.setView(this);

        presenter.init();
        presenter.start();
    }

    @Override
    public void startMainActivity() {
        startActivity(MainActivity.newIntent(this));
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
