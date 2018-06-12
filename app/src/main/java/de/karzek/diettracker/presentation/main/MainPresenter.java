package de.karzek.diettracker.presentation.main;

import android.content.Context;
import android.content.SharedPreferences;

import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    private GetAllMealsUseCase getAllMealsUseCase;
    private MealUIMapper mealMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainPresenter(GetAllMealsUseCase getAllMealsUseCase,
                         MealUIMapper mealMapper) {
        this.getAllMealsUseCase = getAllMealsUseCase;
        this.mealMapper = mealMapper;
    }

    @Override
    public void start() {
        Disposable subs = getAllMealsUseCase.execute(new GetAllMealsUseCase.Input())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(output -> {
                            view.setMeals(mealMapper.transformAll(output.getMealList()));
                        });

        compositeDisposable.add(subs);
    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

}
