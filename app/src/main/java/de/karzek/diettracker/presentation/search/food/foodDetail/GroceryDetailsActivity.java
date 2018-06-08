package de.karzek.diettracker.presentation.search.food.foodDetail;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.main.MainActivity;
import de.karzek.diettracker.presentation.main.diary.meal.viewStub.CaloryDetailsView;
import de.karzek.diettracker.presentation.main.diary.meal.viewStub.CaloryMacroDetailsView;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.StringUtils;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_DRINK;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

/**
 * Created by MarjanaKarzek on 02.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 02.06.2018
 */
public class FoodDetailsActivity extends BaseActivity implements FoodDetailsContract.View {

    @Inject FoodDetailsContract.Presenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.viewstub_calory_details) ViewStub caloryDetails;
    @BindView(R.id.viewstub_calory_makro_details) ViewStub caloryMacroDetails;

    @BindView(R.id.spinner_serving) Spinner spinnerServing;
    @BindView(R.id.spinner_meal) Spinner spinnerMeal;
    @BindView(R.id.edittext_amount) EditText editTextAmount;
    @BindView(R.id.date_label) TextView selectedDateLabel;

    @BindView(R.id.loading_view) FrameLayout loadingView;

    private CaloryDetailsView detailsView;

    private int groceryId;
    private Calendar selectedDate = Calendar.getInstance();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d. MMM yyyy", Locale.GERMANY);
    private SimpleDateFormat databaseDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private GroceryDisplayModel groceryDisplayModel;
    private ArrayList<UnitDisplayModel> defaultUnits;
    private ArrayList<ServingDisplayModel> servings;
    private ArrayList<MealDisplayModel> meals;
    private HashMap<String, Long> maxValues;

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, FoodDetailsActivity.class);
        intent.putExtra("id", id);

        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery_details, menu);
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_details);
        ButterKnife.bind(this);

        groceryId = getIntent().getExtras().getInt("id");

        setupSupportActionBar();
        selectedDateLabel.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));

        editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                refreshNutritionDetails();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                refreshNutritionDetails();
            }
        });

        presenter.setView(this);
        presenter.setGroceryId(groceryId);
        presenter.start();
    }

    @Override
    public void refreshNutritionDetails() {
        float amount = 1;
        int servingPosition = spinnerServing.getSelectedItemPosition();
        int multiplier = 0;
        if(servingPosition >= defaultUnits.size()){
            amount = servings.get(servingPosition-defaultUnits.size()).getAmount();
            multiplier = servings.get(servingPosition-defaultUnits.size()).getUnit().getMultiplier();
        } else {
            multiplier = defaultUnits.get(servingPosition).getMultiplier();
        }
        float editTextValue = 0.0f;
        if(!editTextAmount.getText().toString().equals(""))
            editTextValue = Float.valueOf(editTextAmount.getText().toString());
        else
            editTextValue = Float.valueOf(editTextAmount.getHint().toString());
        amount *= multiplier * editTextValue;

        presenter.updateNutritionDetails(groceryDisplayModel,amount);
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
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
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
    public void setNutritionMaxValues(HashMap<String, Long> values) {
        maxValues = values;
        detailsView.getCaloryProgressBarMaxValue().setText("" + values.get(Constants.calories));

        if (detailsView instanceof CaloryMacroDetailsView){
            ((CaloryMacroDetailsView) detailsView).getProteinProgressBarMaxValue().setText("von\n" + values.get(Constants.proteins) + "g");
            ((CaloryMacroDetailsView) detailsView).getCarbsProgressBarMaxValue().setText("von\n" + values.get(Constants.carbs) + "g");
            ((CaloryMacroDetailsView) detailsView).getFatsProgressBarMaxValue().setText("von\n" + values.get(Constants.fats) + "g");
        }
    }

    @Override
    public void updateNutritionDetails(HashMap<String, Float> values) {
        detailsView.getCaloryProgressBar().setProgress(100.0f / maxValues.get(Constants.calories) * values.get(Constants.calories));
        detailsView.getCaloryProgressBarValue().setText("" + (int)values.get(Constants.calories).floatValue());

        if (detailsView instanceof CaloryMacroDetailsView){
            ((CaloryMacroDetailsView) detailsView).getProteinProgressBar().setProgress(100.0f / maxValues.get(Constants.proteins) * values.get(Constants.proteins));
            ((CaloryMacroDetailsView) detailsView).getProteinProgressBarValue().setText("" + StringUtils.formatFloat(values.get(Constants.proteins)));

            ((CaloryMacroDetailsView) detailsView).getCarbsProgressBar().setProgress(100.0f / maxValues.get(Constants.carbs) * values.get(Constants.carbs));
            ((CaloryMacroDetailsView) detailsView).getCarbsProgressBarValue().setText("" + StringUtils.formatFloat(values.get(Constants.carbs)));

            ((CaloryMacroDetailsView) detailsView).getFatsProgressBar().setProgress(100.0f / maxValues.get(Constants.fats) * values.get(Constants.fats));
            ((CaloryMacroDetailsView) detailsView).getFatsProgressBarValue().setText("" + StringUtils.formatFloat(values.get(Constants.fats)));
        }
    }

    @Override
    public void fillGroceryDetails(GroceryDisplayModel grocery) {
        groceryDisplayModel = grocery;
        getSupportActionBar().setTitle(grocery.getName());
    }

    @Override
    public void initializeServingsSpinner(ArrayList<UnitDisplayModel> defaultUnits, ArrayList<ServingDisplayModel> servings) {
        this.defaultUnits = defaultUnits;
        ArrayList<String> servingLabels = new ArrayList<>();
        for (UnitDisplayModel unit : defaultUnits)
            servingLabels.add(unit.getName());

        this.servings = servings;
        for (ServingDisplayModel serving : servings)
            servingLabels.add(formatServing(serving));

        ArrayAdapter<String> servingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, servingLabels);
        servingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerServing.setAdapter(servingAdapter);
        spinnerServing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i >= defaultUnits.size())
                    editTextAmount.setHint("1");
                else
                    editTextAmount.setHint("100");

                refreshNutritionDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    @Override
    public void initializeMealSpinner(ArrayList<MealDisplayModel> mealDisplayModels) {
        this.meals = mealDisplayModels;

        ArrayList<String> mealNames = new ArrayList<>();
        for (MealDisplayModel meal : mealDisplayModels)
            mealNames.add(meal.getName());

        ArrayAdapter<String> mealAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mealNames);
        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMeal.setAdapter(mealAdapter);
    }

    @Override
    public void navigateToDiaryFragment() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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

    @OnClick(R.id.date_label) public void onDateLabelClicked(){
        presenter.onDateLabelClicked();
    }

    @Override
    public void openDatePicker(){
        if(dateSetListener == null) {
            dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, monthOfYear);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDateLabel.setText(simpleDateFormat.format(selectedDate.getTime()));
            };
        }

        DatePickerDialog dialog = new DatePickerDialog(this, dateSetListener, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTime().getTime() + Constants.weekInMilliS);
        dialog.show();
    }

    @OnClick(R.id.add_grocery) public void onAddFoodClicked(){
        float amount = 1;
        int servingPosition = spinnerServing.getSelectedItemPosition();
        int multiplier = 0;
        if(servingPosition >= defaultUnits.size()){
            amount = servings.get(servingPosition-defaultUnits.size()).getAmount();
            multiplier = servings.get(servingPosition-defaultUnits.size()).getUnit().getMultiplier();
        } else {
            multiplier = defaultUnits.get(servingPosition).getMultiplier();
        }
        float editTextValue = 0.0f;
        if(!editTextAmount.getText().toString().equals(""))
            editTextValue = Float.valueOf(editTextAmount.getText().toString());
        else
            editTextValue = Float.valueOf(editTextAmount.getHint().toString());
        amount *= multiplier * editTextValue;

        presenter.addFood(new DiaryEntryDisplayModel(-1,
                meals.get(spinnerMeal.getSelectedItemPosition()),
                amount,
                defaultUnits.get(0),
                groceryDisplayModel,
                databaseDateFormat.format(selectedDate.getTime())));
    }
}
