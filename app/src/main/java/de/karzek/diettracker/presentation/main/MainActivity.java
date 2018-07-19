package de.karzek.diettracker.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.main.cookbook.CookbookFragment;
import de.karzek.diettracker.presentation.custom.CustomBottomNavigationView;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealFragment;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.onboarding.OnboardingActivity;
import de.karzek.diettracker.presentation.util.ViewUtils;
import de.karzek.diettracker.presentation.main.diary.DiaryFragment;
import de.karzek.diettracker.presentation.main.home.HomeFragment;
import de.karzek.diettracker.presentation.main.settings.SettingsFragment;

public class MainActivity extends BaseActivity implements MainContract.View, DiaryFragment.OnDateSelectedListener, GenericMealFragment.OnRefreshViewPagerNeededListener {

    @BindView(R.id.random_quote_view)
    TextView randomQuoteView;
    @BindView(R.id.bottom_navigation_view)
    CustomBottomNavigationView navigationView;

    @Inject
    MainContract.Presenter presenter;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragmentId", FRAGMENT_HOME);
        return intent;
    }

    public static Intent newIntentToHome(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragmentId", FRAGMENT_HOME);
        return intent;
    }

    public static Intent newIntentToDiary(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragmentId", FRAGMENT_DIARY);
        return intent;
    }

    public static Intent newIntentToCookbook(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragmentId", FRAGMENT_COOKBOOK);
        return intent;
    }

    public static Intent newIntentToSettings(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("fragmentId", FRAGMENT_SETTINGS);
        return intent;
    }

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
        switch (getIntent().getIntExtra("fragmentId", 0)) {
            case FRAGMENT_HOME:
                navigateToFragment(new HomeFragment(), "HomeFragment");
                break;
            case FRAGMENT_DIARY:
                navigateToFragment(new DiaryFragment(), "DiaryFragment");
                break;
            case FRAGMENT_COOKBOOK:
                navigateToFragment(new CookbookFragment(), "CookbookFragment");
                break;
            case FRAGMENT_SETTINGS:
                navigateToFragment(new SettingsFragment(), "SettingsFragment");
                break;
        }

        presenter.setView(this);
        presenter.start();
    }

    private void setupNavigationListener() {
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    navigateToFragment(new HomeFragment(), "HomeFragment");
                    break;
                case R.id.action_diary:
                    navigateToFragment(new DiaryFragment(), "DiaryFragment");
                    break;
                case R.id.action_cookbook:
                    navigateToFragment(new CookbookFragment(), "CookbookFragment");
                    break;
                case R.id.action_settings:
                    navigateToFragment(new SettingsFragment(), "SettingsFragment");
                    break;
            }
            return true;
        });
    }

    private void navigateToFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit();
    }

    @Override
    protected void onDestroy() {
        presenter.finish();
        super.onDestroy();
    }

    @Override
    public void onDateSelected(String databaseDateFormat) {
        DiaryFragment fragment = (DiaryFragment)
                getSupportFragmentManager().findFragmentByTag("DiaryFragment");
        fragment.refreshViewPager();
    }

    @Override
    public void onRefreshViewPagerNeeded() {
        DiaryFragment fragment = (DiaryFragment)
                getSupportFragmentManager().findFragmentByTag("DiaryFragment");
        fragment.refreshViewPager();
    }


    @Override
    public void showOnboardingScreen(int onboardingTag) {
        startActivity(OnboardingActivity.newIntent(this, onboardingTag));
    }
}
