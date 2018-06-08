package de.karzek.diettracker.presentation.search.grocery.barcodeScanner;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public interface BarcodeScannerContract {

    interface View extends BaseView<Presenter>,
            ZXingScannerView.ResultHandler{

        void startDetailsActivity(int id);

        void showLoading();

        void hideLoading();

        void showNoResultsDialog();
    }

    interface Presenter extends BasePresenter<View> {

        void checkResult(Result result);

    }
}
