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

import static de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsContract.MODE_ADD_INGREDIENT;
import static de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsContract.MODE_SEARCH_RESULT;

/**
 * Created by MarjanaKarzek on 08.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 08.06.2018
 */
public class BarcodeScannerActivity extends BaseActivity implements BarcodeScannerContract.View {

    @Inject
    BarcodeScannerContract.Presenter presenter;

    @BindView(R.id.loading_view)
    FrameLayout loadingView;
    @BindView(R.id.barcode_scanner_view)
    ZXingScannerView scannerView;

    private String selectedDate;
    private int selectedMeal;
    private int mode;

    public static Intent newIntent(Context context, String selectedDate, int selectedMeal, int mode) {
        Intent intent = new Intent(context, BarcodeScannerActivity.class);
        intent.putExtra("mode", mode);

        if (mode != MODE_ADD_INGREDIENT) {
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("selectedMeal", selectedMeal);
        }

        return intent;
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        ButterKnife.bind(this);

        mode = getIntent().getExtras().getInt("mode");
        if (mode != MODE_ADD_INGREDIENT) {
            selectedDate = getIntent().getExtras().getString("selectedDate");
            selectedMeal = getIntent().getExtras().getInt("selectedMeal");
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
        if (mode == MODE_ADD_INGREDIENT)
            startActivityForResult(GroceryDetailsActivity.newIntent(this, id, selectedDate, selectedMeal, null, MODE_ADD_INGREDIENT), Constants.ADD_INGREDIENT_INTENT_RESULT);
        else
            startActivity(GroceryDetailsActivity.newIntent(this, id, selectedDate, selectedMeal, null, MODE_SEARCH_RESULT));
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
        presenter.finish();
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.ADD_INGREDIENT_INTENT_RESULT) {
            Intent intent = new Intent();
            intent.putExtra("groceryId", data.getIntExtra("groceryId", 0));
            intent.putExtra("amount", data.getFloatExtra("amount", 0.0f));
            intent.putExtra("unitId", data.getIntExtra("unitId", 0));
            setResult(Constants.ADD_INGREDIENT_INTENT_RESULT, intent);
            finish();
        }
    }
}
