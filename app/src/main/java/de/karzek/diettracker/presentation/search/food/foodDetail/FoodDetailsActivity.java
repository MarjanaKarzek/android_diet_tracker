package de.karzek.diettracker.presentation.search.food.foodDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.main.diary.meal.viewStub.CaloryDetailsView;
import de.karzek.diettracker.presentation.main.diary.meal.viewStub.CaloryMacroDetailsView;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;
import io.reactivex.disposables.CompositeDisposable;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

/**
 * Created by MarjanaKarzek on 02.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 02.06.2018
 */
public class FoodDetailsActivity extends BaseActivity implements FoodDetailsContract.View {

    @Inject
    FoodDetailsContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewstub_calory_details)
    ViewStub caloryDetails;
    @BindView(R.id.viewstub_calory_makro_details)
    ViewStub caloryMacroDetails;

    @BindView(R.id.spinner_serving)
    Spinner spinnerServing;

    private int groceryId;
    private CaloryDetailsView detailsView;

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, FoodDetailsActivity.class);
        intent.putExtra("id", id);

        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_details, menu);
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        ButterKnife.bind(this);

        groceryId = getIntent().getExtras().getInt("id");

        setupSupportActionBar();

        presenter.setView(this);
        presenter.setGroceryId(groceryId);
        presenter.start();
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    public void setPresenter(FoodDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showNutritionDetails(String value) {
        if (value.equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            detailsView = new CaloryDetailsView(caloryDetails.inflate());
        } else {
            detailsView = new CaloryMacroDetailsView(caloryMacroDetails.inflate());
        }
    }

    @Override
    public void fillGroceryDetails(GroceryDisplayModel grocery) {
        getSupportActionBar().setTitle(grocery.getName());
    }

    @Override
    public void initializeServingsSpinner(ArrayList<UnitDisplayModel> defaultUnits, ArrayList<ServingDisplayModel> servings) {

        ArrayList<String> servingLabels = new ArrayList<>();
        for (UnitDisplayModel unit : defaultUnits)
            servingLabels.add(unit.getName());

        for (ServingDisplayModel serving : servings)
            servingLabels.add(formatServing(serving));

        ArrayAdapter<String> servingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, servingLabels);
        servingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerServing.setAdapter(servingAdapter);
    }

    private String formatServing(ServingDisplayModel serving) {
        return serving.getDescription() + " (" + serving.getAmount() + " " + serving.getUnit().getName() + ")";
    }

    private void setupSupportActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow_white, null));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
