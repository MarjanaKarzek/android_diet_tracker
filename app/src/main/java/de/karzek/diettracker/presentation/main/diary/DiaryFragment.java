package de.karzek.diettracker.presentation.main.diary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseFragment;
import de.karzek.diettracker.presentation.main.diary.adapter.DiaryViewPagerAdapter;
import de.karzek.diettracker.presentation.main.diary.drink.GenericDrinkFragment;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealFragment;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.search.food.GrocerySearchActivity;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.ViewUtils;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_DRINK;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.1
 * @date 29.05.2018
 */
public class DiaryFragment extends BaseFragment implements DiaryContract.View {

    @Inject
    DiaryContract.Presenter presenter;

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.floating_action_button_menu) FloatingActionsMenu floatingActionsMenu;
    @BindView(R.id.fab_overlay) FrameLayout overlay;
    @BindView(R.id.loading_view) FrameLayout loadingView;
    @BindView(R.id.date_label) TextView selectedDate;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d. MMM yyyy", Locale.GERMANY);
    private SimpleDateFormat databaseDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar datePickerCalendar = Calendar.getInstance();

    OnDateSelectedListener callback;

    public interface OnDateSelectedListener {
        public void onDateSelected(String databaseDateFormat);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (OnDateSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showLoading();
        tabLayout.setupWithViewPager(viewPager);
        hideLoading();

        selectedDate.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));

        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                overlay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                overlay.setVisibility(View.GONE);
            }
        });
        ViewUtils.addElevationToFABMenuLabels(getContext(), floatingActionsMenu);

        presenter.setView(this);
        presenter.start();
    }

    @Override
    protected void setupFragmentComponent() {
        TrackerApplication.get(getContext()).createDiaryComponent().inject(this);
    }

    @Override
    public void setPresenter(DiaryContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(getContext()).releaseDiaryComponent();
    }

    @OnClick(R.id.add_food) public void onAddFoodClicked() {
        presenter.onAddFoodClicked();
    }

    @OnClick(R.id.add_drink) public void onAddDrinkClicked() {
        presenter.onAddDrinkClicked();
    }

    @OnClick(R.id.add_recipe) public void onAddRecipeClicked() {
        presenter.onAddRecipeClicked();
    }

    @OnClick(R.id.fab_overlay) public void onFabOverlayClicked() {
        presenter.onFabOverlayClicked();
    }

    @OnClick(R.id.date_label) public void onDateLabelClicked() {
        presenter.onDateLabelClicked();
    }

    @OnClick(R.id.previous_date) public void onPreviousDateClicked() {
        presenter.onPreviousDateClicked();
    }

    @OnClick(R.id.next_date) public void onNextDateClicked() {
        presenter.onNextDateClicked();
    }

    @Override
    public void setupViewPager(ArrayList<MealDisplayModel> meals) {
        DiaryViewPagerAdapter adapter = new DiaryViewPagerAdapter(getFragmentManager());

        for (MealDisplayModel meal : meals) {
            Bundle bundle = new Bundle();
            bundle.putString("meal", meal.getName());
            GenericMealFragment fragment = new GenericMealFragment();
            fragment.setArguments(bundle);
            adapter.addFragment(fragment, meal.getName());
        }
        adapter.addFragment(new GenericDrinkFragment(), "Getränke");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDateSelected(String selectedDate) {
        callback.onDateSelected(selectedDate);
    }

    @Override
    public String getSelectedDate(){
        return databaseDateFormat.format(datePickerCalendar.getTime());
    }

    @Override
    public void startFoodSearchActivity() {
        startActivity(GrocerySearchActivity.newIntent(getContext(), TYPE_FOOD, databaseDateFormat.format(datePickerCalendar.getTime()),viewPager.getCurrentItem()));
    }

    @Override
    public void startDrinkSearchActivity() {
        startActivity(GrocerySearchActivity.newIntent(getContext(), TYPE_DRINK, databaseDateFormat.format(datePickerCalendar.getTime()),viewPager.getCurrentItem()));
    }

    @Override
    public void startRecipeSearchActivity() {

    }

    @Override
    public void closeFabMenu() {
        floatingActionsMenu.collapse();
    }

    @Override
    public void openDatePicker() {
        if (dateSetListener == null) {
            dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
                datePickerCalendar.set(Calendar.YEAR, year);
                datePickerCalendar.set(Calendar.MONTH, monthOfYear);
                datePickerCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateSelectedDate(datePickerCalendar.getTime());
            };
        }

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateSetListener, datePickerCalendar.get(Calendar.YEAR), datePickerCalendar.get(Calendar.MONTH), datePickerCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTime().getTime() + Constants.weekInMilliS);
        dialog.show();
    }

    @Override
    public void showPreviousDate() {
        datePickerCalendar.set(Calendar.DAY_OF_MONTH, datePickerCalendar.get(Calendar.DAY_OF_MONTH) - 1);
        updateSelectedDate(datePickerCalendar.getTime());
    }

    @Override
    public void showNextDate() {
        datePickerCalendar.set(Calendar.DAY_OF_MONTH, datePickerCalendar.get(Calendar.DAY_OF_MONTH) + 1);
        updateSelectedDate(datePickerCalendar.getTime());
    }

    private void updateSelectedDate(Date date) {
        selectedDate.setText(simpleDateFormat.format(date));
        presenter.onDateSelected(databaseDateFormat.format(date));
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void refreshViewPager(){
        viewPager.getAdapter().notifyDataSetChanged();
    }
}
