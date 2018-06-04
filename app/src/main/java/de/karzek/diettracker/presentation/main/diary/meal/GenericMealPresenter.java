package de.karzek.diettracker.presentation.main.diary.meal;

import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetAllDiaryEntriesMatchingUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_SETTING_NUTRITION_DETAILS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class GenericMealPresenter implements GenericMealContract.Presenter {

    GenericMealContract.View view;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private GetAllDiaryEntriesMatchingUseCaseImpl getAllDiaryEntriesMatchingUseCase;
    private DiaryEntryUIMapper diaryEntryMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String meal;

    public GenericMealPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                GetAllDiaryEntriesMatchingUseCaseImpl getAllDiaryEntriesMatchingUseCase,
                                DiaryEntryUIMapper diaryEntryMapper){
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.getAllDiaryEntriesMatchingUseCase = getAllDiaryEntriesMatchingUseCase;
        this.diaryEntryMapper = diaryEntryMapper;
    }

    @Override
    public void start() {
        view.showLoading();

        if(sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
        } else {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        }

        updateDiaryEntries(view.getSelectedDate());

    }

    @Override
    public void setView(GenericMealContract.View view) {
        this.view = view;
    }

    @Override
    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Override
    public void updateDiaryEntries(String date) {
        Disposable subs = getAllDiaryEntriesMatchingUseCase.execute(new GetAllDiaryEntriesMatchingUseCaseImpl.Input(meal, date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if(output.getStatus() == 0) {
                        if(output.getDiaryEntries().size() > 0) {
                            view.updateGroceryList(diaryEntryMapper.transformAll(output.getDiaryEntries()));
                            view.showRecyclerView();
                            view.hideGroceryListPlaceholder();
                        } else {
                            view.hideRecyclerView();
                            view.showGroceryListPlaceholder();
                        }
                        view.hideLoading();
                    }
                });
        compositeDisposable.add(subs);
    }

    @Override
    public void finish() {

    }

    @Override
    public void onItemClicked(int id) {
        //start edit
    }
}
