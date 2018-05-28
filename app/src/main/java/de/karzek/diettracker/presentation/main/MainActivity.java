package de.karzek.diettracker.presentation.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.cookbook.CookbookFragment;
import de.karzek.diettracker.presentation.custom.CustomBottomNavigationView;
import de.karzek.diettracker.presentation.custom.ViewUtils;
import de.karzek.diettracker.presentation.diary.DiaryFragment;
import de.karzek.diettracker.presentation.home.HomeFragment;
import de.karzek.diettracker.presentation.model.RandomQuoteDisplayModel;
import de.karzek.diettracker.presentation.settings.SettingsFragment;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.random_quote_view) TextView randomQuoteView;
    @BindView(R.id.bottom_navigation_view) CustomBottomNavigationView navigationView;

    @Inject
    MainContract.Presenter presenter;

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ViewUtils.customizeBottomNavigation(navigationView);

        setupNavigationListener();
        navigateToFragment(new HomeFragment());

        presenter.setView(this);
        setPresenter(presenter);
        presenter.start();
    }

    private void setupNavigationListener() {
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.action_home:
                    navigateToFragment(new HomeFragment());
                    break;
                case R.id.action_diary:
                    navigateToFragment(new DiaryFragment());
                    break;
                case R.id.action_cookbook:
                    navigateToFragment(new CookbookFragment());
                    break;
                case R.id.action_settings:
                    navigateToFragment(new SettingsFragment());
                    break;
            }
            return true;
        });
    }

    private void navigateToFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        presenter.finish();
        super.onDestroy();
    }
}
