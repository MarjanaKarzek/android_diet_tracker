package de.karzek.diettracker.presentation.search.grocery.barcodeScanner;

import com.google.zxing.Result;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByBarcodeUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByBarcodeUseCase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MarjanaKarzek on 08.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 08.06.2018
 */
public class BarcodeScannerPresenter implements BarcodeScannerContract.Presenter {

    private BarcodeScannerContract.View view;

    private Lazy<GetGroceryByBarcodeUseCaseImpl> getGroceryByBarcodeUseCase;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BarcodeScannerPresenter(Lazy<GetGroceryByBarcodeUseCaseImpl> getGroceryByBarcodeUseCase){
        this.getGroceryByBarcodeUseCase = getGroceryByBarcodeUseCase;
    }

    @Override
    public void start() {
    }

    @Override
    public void setView(BarcodeScannerContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void checkResult(Result result) {
        view.showLoading();
        compositeDisposable.add(getGroceryByBarcodeUseCase.get().execute(new GetGroceryByBarcodeUseCase.Input(result.getText()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == GetGroceryByBarcodeUseCase.Output.SUCCESS) {
                        view.startDetailsActivity(0);
                        view.hideLoading();
                    } else {
                        view.showNoResultsDialog();
                    }
                }));
    }

}
