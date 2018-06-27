package de.karzek.diettracker.presentation.search.grocery.groceryDetail;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;
import de.karzek.diettracker.presentation.search.grocery.groceryDetail.viewStub.AllergenView;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.StringUtils;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_DRINK;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;
import static de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsContract.MODE_ADD_INGREDIENT;
import static de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsContract.MODE_EDIT_DIARY_ENTRY;
import static de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsContract.MODE_SEARCH_RESULT;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

/**
 * Created by MarjanaKarzek on 02.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 02.06.2018
 */
public class GroceryDetailsActivity extends BaseActivity implements GroceryDetailsContract.View {

    @Inject
    GroceryDetailsContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewstub_allergen_info)
    ViewStub allergenViewStub;
    @BindView(R.id.viewstub_calory_details)
    ViewStub caloryDetails;
    @BindView(R.id.viewstub_calory_makro_details)
    ViewStub caloryMacroDetails;

    @BindView(R.id.spinner_serving)
    Spinner spinnerServing;
    @BindView(R.id.spinner_meal)
    Spinner spinnerMeal;
    @BindView(R.id.edittext_amount)
    EditText editTextAmount;
    @BindView(R.id.date_label)
    TextView selectedDateLabel;
    @BindView(R.id.add_grocery)
    Button addButton;
    @BindView(R.id.delete_diary_entry)
    Button deleteButton;

    @BindView(R.id.loading_view)
    FrameLayout loadingView;

    private AllergenView allergenView;
    private CaloryDetailsView detailsView;

    private int groceryId;
    private String selectedDate;
    private int selectedMeal;

    private Menu menu;

    private int diaryEntryId;
    private int mode;

    private Calendar selectedDateCalendar = Calendar.getInstance();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d. MMM yyyy", Locale.GERMANY);
    private SimpleDateFormat databaseDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private GroceryDisplayModel groceryDisplayModel;
    private ArrayList<UnitDisplayModel> defaultUnits;
    private ArrayList<ServingDisplayModel> servings;
    private ArrayList<MealDisplayModel> meals;
    private HashMap<String, Long> maxValues;

    public static Intent newIntent(Context context, @Nullable Integer id, String selectedDate, @Nullable Integer selectedMeal, @Nullable Integer diaryEntryId, int mode) {
        Intent intent = new Intent(context, GroceryDetailsActivity.class);
        intent.putExtra("mode", mode);

        switch (mode) {
            case MODE_SEARCH_RESULT:
                intent.putExtra("id", id.intValue());
                intent.putExtra("selectedDate", selectedDate);
                intent.putExtra("selectedMeal", selectedMeal.intValue());
                break;
            case MODE_EDIT_DIARY_ENTRY:
                intent.putExtra("diaryEntryId", diaryEntryId.intValue());
                break;
            case MODE_ADD_INGREDIENT:
                intent.putExtra("id", id.intValue());
                break;
        }

        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery_details, menu);

        this.menu = menu;

        presenter.checkFavoriteState(groceryId);

        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_details);
        ButterKnife.bind(this);

        presenter.setView(this);
        setupSupportActionBar();
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

        switch (getIntent().getExtras().getInt("mode")) {
            case MODE_SEARCH_RESULT:
                mode = MODE_SEARCH_RESULT;
                groceryId = getIntent().getExtras().getInt("id");
                selectedDate = getIntent().getExtras().getString("selectedDate");
                selectedMeal = getIntent().getExtras().getInt("selectedMeal");

                try {
                    Date currentSelectedDate = databaseDateFormat.parse(selectedDate);
                    selectedDateLabel.setText(simpleDateFormat.format(currentSelectedDate));
                    selectedDateCalendar.setTime(currentSelectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    selectedDateLabel.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));
                }

                presenter.setGroceryId(groceryId);
                presenter.start();
                break;
            case MODE_EDIT_DIARY_ENTRY:
                mode = MODE_EDIT_DIARY_ENTRY;
                diaryEntryId = getIntent().getExtras().getInt("diaryEntryId");
                presenter.startEditMode(diaryEntryId);
                break;
            case MODE_ADD_INGREDIENT:
                mode = MODE_ADD_INGREDIENT;
                groceryId = getIntent().getExtras().getInt("id");
                presenter.startAddIngredientMode(groceryId);
                break;
        }
    }

    @Override
    public void refreshNutritionDetails() {
        float amount = 1;
        int servingPosition = spinnerServing.getSelectedItemPosition();
        int multiplier = 0;
        if (servingPosition >= defaultUnits.size()) {
            amount = servings.get(servingPosition - defaultUnits.size()).getAmount();
            multiplier = servings.get(servingPosition - defaultUnits.size()).getUnit().getMultiplier();
        } else {
            multiplier = defaultUnits.get(servingPosition).getMultiplier();
        }
        float editTextValue = 0.0f;
        if (!editTextAmount.getText().toString().equals(""))
            editTextValue = Float.valueOf(editTextAmount.getText().toString());
        else
            editTextValue = Float.valueOf(editTextAmount.getHint().toString());
        amount *= multiplier * editTextValue;

        presenter.updateNutritionDetails(groceryDisplayModel, amount);
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    public void setPresenter(GroceryDetailsContract.Presenter presenter) {
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
    public void setupAllergenWarning(ArrayList<AllergenDisplayModel> allergenDisplayModels) {
        allergenView = new AllergenView(allergenViewStub.inflate());

        String warning = getString(R.string.allergen_warning) + " ";

        for (int i = 0; i < allergenDisplayModels.size(); i++) {
            warning += allergenDisplayModels.get(i).getName();
            if (i < allergenDisplayModels.size() - 1)
                warning += ", ";
        }

        allergenView.getAllergenWarning().setText(warning);
    }

    @Override
    public void setNutritionMaxValues(HashMap<String, Long> values) {
        maxValues = values;
        detailsView.getCaloryProgressBarMaxValue().setText("" + values.get(Constants.calories));

        if (detailsView instanceof CaloryMacroDetailsView) {
            ((CaloryMacroDetailsView) detailsView).getProteinProgressBarMaxValue().setText("von\n" + values.get(Constants.proteins) + "g");
            ((CaloryMacroDetailsView) detailsView).getCarbsProgressBarMaxValue().setText("von\n" + values.get(Constants.carbs) + "g");
            ((CaloryMacroDetailsView) detailsView).getFatsProgressBarMaxValue().setText("von\n" + values.get(Constants.fats) + "g");
        }
    }

    @Override
    public void updateNutritionDetails(HashMap<String, Float> values) {
        detailsView.getCaloryProgressBar().setProgress(100.0f / maxValues.get(Constants.calories) * values.get(Constants.calories));
        detailsView.getCaloryProgressBarValue().setText("" + (int) values.get(Constants.calories).floatValue());

        if (detailsView instanceof CaloryMacroDetailsView) {
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
                if (i >= defaultUnits.size())
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
        if (groceryDisplayModel.getType() == TYPE_DRINK) {
            spinnerMeal.setVisibility(View.GONE);
            return;
        }
        this.meals = mealDisplayModels;

        ArrayList<String> mealNames = new ArrayList<>();
        for (MealDisplayModel meal : mealDisplayModels)
            mealNames.add(meal.getName());

        ArrayAdapter<String> mealAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mealNames);
        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMeal.setAdapter(mealAdapter);
        spinnerMeal.setSelection(selectedMeal);
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
        if (item.getItemId() == android.R.id.home)
            finish();
        else if (item.getItemId() == R.id.food_detail_favorite) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                item.setIcon(getDrawable(R.drawable.ic_star_filled_white));
            } else {
                item.setIcon(getDrawable(R.drawable.ic_star_white));
            }
            presenter.onFavoriteGroceryClicked(item.isChecked(), groceryDisplayModel);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.date_label)
    public void onDateLabelClicked() {
        presenter.onDateLabelClicked();
    }

    @Override
    public void openDatePicker() {
        if (dateSetListener == null) {
            dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
                selectedDateCalendar.set(Calendar.YEAR, year);
                selectedDateCalendar.set(Calendar.MONTH, monthOfYear);
                selectedDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDateLabel.setText(simpleDateFormat.format(selectedDateCalendar.getTime()));
            };
        }

        DatePickerDialog dialog = new DatePickerDialog(this, dateSetListener, selectedDateCalendar.get(Calendar.YEAR), selectedDateCalendar.get(Calendar.MONTH), selectedDateCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTime().getTime() + Constants.weekInMilliS);
        dialog.show();
    }

    @Override
    public void setFavoriteIconCheckedState(boolean checked) {
        MenuItem item = menu.findItem(R.id.food_detail_favorite).setChecked(checked);
        if (item.isChecked()) {
            item.setIcon(getDrawable(R.drawable.ic_star_filled_white));
        } else {
            item.setIcon(getDrawable(R.drawable.ic_star_white));
        }
    }

    @Override
    public void prepareEditMode(DiaryEntryDisplayModel diaryEntry) {
        selectedDate = diaryEntry.getDate();
        try {
            Date currentSelectedDate = databaseDateFormat.parse(selectedDate);
            selectedDateLabel.setText(simpleDateFormat.format(currentSelectedDate));
            selectedDateCalendar.setTime(currentSelectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            selectedDateLabel.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));
        }

        editTextAmount.setText(StringUtils.formatFloat(diaryEntry.getAmount()));

        if (diaryEntry.getGrocery().getType() == TYPE_FOOD)
            spinnerMeal.setSelection(meals.indexOf(diaryEntry.getMeal()));
        spinnerServing.setSelection(defaultUnits.indexOf(diaryEntry.getUnit()));

        addButton.setText(getString(R.string.save_button));
        deleteButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void prepareAddIngredientMode() {
        selectedDateLabel.setVisibility(View.GONE);
        spinnerMeal.setVisibility(View.GONE);
    }

    @Override
    public void finishView() {
        this.finish();
    }

    @OnClick(R.id.add_grocery)
    public void onAddGroceryClicked() {
        float amount = 1;
        int servingPosition = spinnerServing.getSelectedItemPosition();

        int multiplier;
        if (servingPosition >= defaultUnits.size()) {
            amount = servings.get(servingPosition - defaultUnits.size()).getAmount();
            multiplier = servings.get(servingPosition - defaultUnits.size()).getUnit().getMultiplier();
        } else {
            multiplier = defaultUnits.get(servingPosition).getMultiplier();
        }

        float editTextValue;
        if (!editTextAmount.getText().toString().equals(""))
            editTextValue = Float.valueOf(editTextAmount.getText().toString());
        else
            editTextValue = Float.valueOf(editTextAmount.getHint().toString());
        amount *= multiplier * editTextValue;

        int id = -1;
        if (mode == MODE_EDIT_DIARY_ENTRY)
            id = diaryEntryId;

        if (mode == MODE_ADD_INGREDIENT) {
            Intent intent = new Intent();
            intent.putExtra("groceryId", groceryDisplayModel.getId());
            intent.putExtra("amount", amount);
            intent.putExtra("unitId", defaultUnits.get(0).getId());
            setResult(Constants.ADD_INGREDIENT_INTENT_RESULT, intent);
            finish();
            return;
        }

        if (groceryDisplayModel.getType() == TYPE_FOOD) {
            presenter.addFood(new DiaryEntryDisplayModel(id,
                    meals.get(spinnerMeal.getSelectedItemPosition()),
                    amount,
                    defaultUnits.get(0),
                    groceryDisplayModel,
                    databaseDateFormat.format(selectedDateCalendar.getTime())));
        } else {
            presenter.addDrink(new DiaryEntryDisplayModel(id,
                    amount,
                    defaultUnits.get(0),
                    groceryDisplayModel,
                    databaseDateFormat.format(selectedDateCalendar.getTime())));
        }
    }

    @OnClick(R.id.delete_diary_entry)
    public void onDeleteDiaryEntryClicked() {
        showLoading();
        presenter.onDeleteDiaryEntryClicked(diaryEntryId);
    }
}
