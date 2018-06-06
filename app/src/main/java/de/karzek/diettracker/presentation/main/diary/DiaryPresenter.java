package de.karzek.diettracker.presentation.main.diary;

import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class DiaryPresenter implements DiaryContract.Presenter {

    private DiaryContract.View view;

    private GetAllMealsUseCaseImpl getAllMealsUseCase;
    private MealUIMapper mapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DiaryPresenter(GetAllMealsUseCaseImpl getAllMealsUseCase,
                          MealUIMapper mapper) {
        this.getAllMealsUseCase = getAllMealsUseCase;
        this.mapper = mapper;
    }

    @Override
    public void start() {
        view.showLoading();
        Disposable subs = getAllMealsUseCase.execute(new GetAllMealsUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    view.setupViewPager(mapper.transformAll(output.getMealList()));
                    view.hideLoading();
                });
        compositeDisposable.add(subs);
    }

    @Override
    public void setView(DiaryContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
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
    public void onDateSelected(String selectedDate) {
        view.onDateSelected(selectedDate);
    }

    @Override
    public void onPreviousDateClicked() {
        view.showPreviousDate();
    }

    @Override
    public void onNextDateClicked() {
        view.showNextDate();
    }


}
