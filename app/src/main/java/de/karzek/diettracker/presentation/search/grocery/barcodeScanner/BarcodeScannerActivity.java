package de.karzek.diettracker.presentation.search.grocery.barcodeScanner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;

import com.google.zxing.Result;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsActivity;
import de.karzek.diettracker.presentation.util.Constants;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static de.karzek.diettracker.presentation.util.Constants.INVALID_ENTITY_ID;

/**
 * Created by MarjanaKarzek on 08.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 08.06.2018
 */
public class BarcodeScannerActivity extends BaseActivity implements BarcodeScannerContract.View {

    public static final String EXTRA_MODE = "EXTRA_MODE";
    public static final String EXTRA_SELECTED_DATE = "EXTRA_SELECTED_DATE";
    public static final String EXTRA_SELECTED_MEAL = "EXTRA_SELECTED_MEAL";
    public static final String EXTRA_INGREDIENT_INDEX = "EXTRA_INGREDIENT_ID";
    public static final String EXTRA_AMOUNT = "EXTRA_AMOUNT";
    public static final String EXTRA_GROCERY_ID = "EXTRA_GROCERY_ID";
    public static final String EXTRA_UNIT_ID = "EXTRA_UNIT_ID";

    @Inject
    BarcodeScannerContract.Presenter presenter;

    @BindView(R.id.loading_view)
    FrameLayout loadingView;
    @BindView(R.id.barcode_scanner_view)
    ZXingScannerView scannerView;

    private String selectedDate;
    private int selectedMeal;
    private int mode;
    private int ingredientIndex;

    public static Intent newGrocerySearchIntent(Context context, String selectedDate, int selectedMeal){
        Intent intent = new Intent(context, BarcodeScannerActivity.class);

        intent.putExtra(EXTRA_MODE, SearchMode.MODE_GROCERY_SEARCH);
        intent.putExtra(EXTRA_SELECTED_DATE, selectedDate);
        intent.putExtra(EXTRA_SELECTED_MEAL, selectedMeal);

        return intent;
    }

    public static Intent newIngredientSearchIntent(Context context){
        Intent intent = new Intent(context, BarcodeScannerActivity.class);
        intent.putExtra(EXTRA_MODE, SearchMode.MODE_INGREDIENT_SEARCH);
        return intent;
    }

    public static Intent newReplaceIngredientSearchIntent(Context context, int ingredientIndex){
        Intent intent = new Intent(context, BarcodeScannerActivity.class);
        intent.putExtra(EXTRA_MODE, SearchMode.MODE_REPLACE_INGREDIENT_SEARCH);
        intent.putExtra(EXTRA_INGREDIENT_INDEX, ingredientIndex);
        return intent;
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).createBarcodeScannerComponent().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        ButterKnife.bind(this);

        mode = getIntent().getExtras().getInt(EXTRA_MODE);
        switch (mode){
            case SearchMode.MODE_GROCERY_SEARCH:
                selectedDate = getIntent().getExtras().getString(EXTRA_SELECTED_DATE);
                selectedMeal = getIntent().getExtras().getInt(EXTRA_SELECTED_MEAL);
                break;
            case SearchMode.MODE_INGREDIENT_SEARCH:
                break;
            case SearchMode.MODE_REPLACE_INGREDIENT_SEARCH:
                ingredientIndex = getIntent().getExtras().getInt(EXTRA_INGREDIENT_INDEX);
                break;
        }

        scannerView.setResultHandler(this);
        scannerView.startCamera();

        presenter.setView(this);
        presenter.start();
    }

    @Override
    public void handleResult(Result result) {
        presenter.checkResult(result);
    }

    @Override
    public void startDetailsActivity(int id) {
        switch (mode){
            case SearchMode.MODE_GROCERY_SEARCH:
                startActivity(GroceryDetailsActivity.newGrocerySearchIntent(this, id, selectedDate, selectedMeal));
                break;
            case SearchMode.MODE_INGREDIENT_SEARCH:
                startActivityForResult(GroceryDetailsActivity.newIngredientSearchIntent(this, id), Constants.ADD_REPLACE_INGREDIENT_INTENT_RESULT);
                break;
            case SearchMode.MODE_REPLACE_INGREDIENT_SEARCH:
                startActivityForResult(GroceryDetailsActivity.newReplaceIngredientSearchIntent(this, id, ingredientIndex), Constants.ADD_REPLACE_INGREDIENT_INTENT_RESULT);
                break;
        }
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
    public void showNoResultsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.barcode_search_no_result));
        builder.setPositiveButton(getString(R.string.barcode_search_no_result_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
        presenter.finish();
        TrackerApplication.get(this).releaseBarcodeScannerComponent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.ADD_REPLACE_INGREDIENT_INTENT_RESULT) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_INGREDIENT_INDEX, data.getIntExtra(EXTRA_INGREDIENT_INDEX, Constants.INVALID_ENTITY_ID));
            intent.putExtra(EXTRA_GROCERY_ID, data.getIntExtra(EXTRA_GROCERY_ID, 0));
            intent.putExtra(EXTRA_AMOUNT, data.getFloatExtra(EXTRA_AMOUNT, 0.0f));
            intent.putExtra(EXTRA_UNIT_ID, data.getIntExtra(EXTRA_UNIT_ID, 0));
            setResult(Constants.ADD_REPLACE_INGREDIENT_INTENT_RESULT, intent);
            finish();
        }
    }
}
