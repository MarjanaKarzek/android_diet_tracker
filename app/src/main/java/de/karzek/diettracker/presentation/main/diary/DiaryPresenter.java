package de.karzek.diettracker.presentation.main.diary;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class DiaryPresenter implements DiaryContract.Presenter {

    private DiaryContract.View view;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void start() {
    }

    @Override
    public void setView(DiaryContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {

    }

    @Override
    public void onAddFoodClicked() {
        view.startFoodSearchActivity();
    }

    @Override
    public void onAddDrinkClicked() {
        view.startDrinkSearchActivity();
    }

    @Override
    public void onAddRecipeClicked() {
        view.startRecipeSearchActivity();
    }

    @Override
    public void onFabOverlayClicked() {
        view.closeFabMenu();
    }

    @Override
    public void onDateLabelClicked() {
        view.openDatePicker();
    }

    @Override
    public void onDateSelected(){
        view.showLoading();

        view.hideLoading();
    }


}
