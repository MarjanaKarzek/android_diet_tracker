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
import de.karzek.diettracker.presentation.util.ViewUtils;
import de.karzek.diettracker.presentation.main.diary.DiaryFragment;
import de.karzek.diettracker.presentation.main.home.HomeFragment;
import de.karzek.diettracker.presentation.main.settings.SettingsFragment;

public class MainActivity extends BaseActivity implements MainContract.View, DiaryFragment.OnDateSelectedListener{

    @BindView(R.id.random_quote_view) TextView randomQuoteView;
    @BindView(R.id.bottom_navigation_view) CustomBottomNavigationView navigationView;

    @Inject
    MainContract.Presenter presenter;

    private ArrayList<MealDisplayModel> meals;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
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

    @Override
    public void onDateSelected(String databaseDateFormat) {
        for(int i=0; i < meals.size(); i++) {
            GenericMealFragment fragment = (GenericMealFragment)
                    getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + i);
            if (fragment != null)
                fragment.setSelectedDate(databaseDateFormat);
        }
    }

    @Override
    public void setMeals(ArrayList<MealDisplayModel> meals) {
        this.meals = meals;
    }
}
