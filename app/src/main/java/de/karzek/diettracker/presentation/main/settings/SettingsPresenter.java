package de.karzek.diettracker.presentation.main.settings;

import android.widget.EditText;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.UpdateMealTimeUseCase;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View view;

    private GetAllMealsUseCase getAllMealsUseCase;
    private Lazy<GetMealByIdUseCase> getMealByIdUseCase;
    private Lazy<UpdateMealTimeUseCase> updateMealTimeUseCase;
    private MealUIMapper mealMapper;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SettingsPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                             GetAllMealsUseCase getAllMealsUseCase,
                             Lazy<GetMealByIdUseCase> getMealByIdUseCase,
                             Lazy<UpdateMealTimeUseCase> updateMealTimeUseCase,
                             MealUIMapper mealMapper) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;

        this.getAllMealsUseCase = getAllMealsUseCase;
        this.getMealByIdUseCase = getMealByIdUseCase;
        this.updateMealTimeUseCase = updateMealTimeUseCase;
        this.mealMapper = mealMapper;
    }

    @Override
    public void start() {
        view.fillSettingsOptions(sharedPreferencesUtil);

        compositeDisposable.add(getAllMealsUseCase.execute(new GetAllMealsUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    view.setupMealRecyclerView(mealMapper.transformAll(output.getMealList()));
                }));
    }

    @Override
    public void setView(SettingsContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void updateSharedPreferenceIntValue(String key, Integer value) {
        sharedPreferencesUtil.setInt(key, value);
    }

    @Override
    public void updateSharedPreferenceFloatValue(String key, Float value) {
        sharedPreferencesUtil.setFloat(key, value);
    }

    @Override
    public void updateMealTime(int id, String startTime, String endTime) {
        compositeDisposable.add(updateMealTimeUseCase.get().execute(new UpdateMealTimeUseCase.Input(id, startTime, endTime))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if(output.getStatus() != 0){
                        //todo error handling
                    }
                }));
    }

    @Override
    public void onItemNameChanged(int id, EditText editTextView) {

        view.clearFocusOfView(editTextView);
    }

    @Override
    public void onItemEditTimeClicked(int id) {
        compositeDisposable.add(getMealByIdUseCase.get().execute(new GetMealByIdUseCase.Input(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    view.showMealEditTimeDialog(mealMapper.transform(output.getMeal()));
                }));
    }

    @Override
    public void onItemDelete(int id) {

        view.updateRecyclerView();
    }
}
