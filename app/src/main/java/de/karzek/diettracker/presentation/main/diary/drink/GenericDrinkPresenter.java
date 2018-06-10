package de.karzek.diettracker.presentation.main.diary.drink;

import java.util.List;

import dagger.Lazy;
import de.karzek.diettracker.domain.interactor.manager.NutritionManagerImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.AddAmountOfWaterUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.DeleteDiaryEntryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetAllDiaryEntriesMatchingUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.GetWaterStatusUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.diaryEntry.UpdateAmountOfWaterUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.AddAmountOfWaterUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetWaterStatusUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.UpdateAmountOfWaterUseCase;
import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;
import de.karzek.diettracker.presentation.mapper.DiaryEntryUIMapper;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_BOTTLE_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_GLASS_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_LIQUID_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_SETTING_NUTRITION_DETAILS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_BOTTLE_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_GLASS_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_REQUIREMENT_LIQUID_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class GenericDrinkPresenter implements GenericDrinkContract.Presenter {

    GenericDrinkContract.View view;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private Lazy<DeleteDiaryEntryUseCaseImpl> deleteDiaryEntryUseCase;
    private Lazy<AddAmountOfWaterUseCaseImpl> addAmountOfWaterUseCase;
    private Lazy<UpdateAmountOfWaterUseCaseImpl> updateAmountOfWaterUseCase;
    private GetAllDiaryEntriesMatchingUseCaseImpl getAllDiaryEntriesMatchingUseCase;
    private GetWaterStatusUseCaseImpl getWaterStatusUseCase;
    private DiaryEntryUIMapper diaryEntryMapper;
    private NutritionManagerImpl nutritionManager;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public GenericDrinkPresenter(SharedPreferencesUtil sharedPreferencesUtil,
                                 Lazy<DeleteDiaryEntryUseCaseImpl> deleteDiaryEntryUseCase,
                                 Lazy<AddAmountOfWaterUseCaseImpl> addAmountOfWaterUseCase,
                                 Lazy<UpdateAmountOfWaterUseCaseImpl> updateAmountOfWaterUseCase,
                                 GetAllDiaryEntriesMatchingUseCaseImpl getAllDiaryEntriesMatchingUseCase,
                                 GetWaterStatusUseCaseImpl getWaterStatusUseCase,
                                 DiaryEntryUIMapper diaryEntryMapper,
                                 NutritionManagerImpl nutritionManager) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.deleteDiaryEntryUseCase = deleteDiaryEntryUseCase;
        this.addAmountOfWaterUseCase = addAmountOfWaterUseCase;
        this.updateAmountOfWaterUseCase = updateAmountOfWaterUseCase;
        this.getAllDiaryEntriesMatchingUseCase = getAllDiaryEntriesMatchingUseCase;
        this.getWaterStatusUseCase = getWaterStatusUseCase;
        this.diaryEntryMapper = diaryEntryMapper;
        this.nutritionManager = nutritionManager;
    }

    @Override
    public void start() {
        view.showLoading();

        if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY);
        } else {
            view.showNutritionDetails(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS);
        }

        updateLiquidStatus(view.getSelectedDate());
    }

    @Override
    public void updateLiquidStatus(String selectedDate) {
        compositeDisposable.add(getWaterStatusUseCase.execute(new GetWaterStatusUseCase.Input(selectedDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        view.setLiquidStatus(diaryEntryMapper.transform(output.getWaterDiaryEntry()), sharedPreferencesUtil.getFloat(KEY_REQUIREMENT_LIQUID_DAILY, VALUE_REQUIREMENT_LIQUID_DAILY));
                        updateDiaryEntries(view.getSelectedDate());
                    }
                }));
    }

    private void updateDiaryEntries(String selectedDate) {
        compositeDisposable.add(getAllDiaryEntriesMatchingUseCase.execute(new GetAllDiaryEntriesMatchingUseCase.Input(null, selectedDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        if (output.getDiaryEntries().size() > 0) {
                            view.updateGroceryList(diaryEntryMapper.transformAll(output.getDiaryEntries()));
                            view.addToLiquidStatus(diaryEntryMapper.transformAll(output.getDiaryEntries()), sharedPreferencesUtil.getFloat(KEY_REQUIREMENT_LIQUID_DAILY, VALUE_REQUIREMENT_LIQUID_DAILY));
                            view.showRecyclerView();
                            view.hideGroceryListPlaceholder();
                            view.showExpandOption();
                            getNutritionValuesForDiaryEntries(output.getDiaryEntries());
                        } else {
                            view.hideRecyclerView();
                            view.showGroceryListPlaceholder();
                            view.hideExpandOption();
                            getDefaultNutritionValues();
                        }
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void addFavoriteDrinkClicked(String selectedDate) {
    }

    @Override
    public void addGlassWaterClicked(String selectedDate) {
        view.showLoading();
        addAmountOfWater(sharedPreferencesUtil.getFloat(KEY_GLASS_VOLUME, VALUE_GLASS_VOLUME), selectedDate);
    }

    @Override
    public void addBottleWaterClicked(String selectedDate) {
        view.showLoading();
        addAmountOfWater(sharedPreferencesUtil.getFloat(KEY_BOTTLE_VOLUME, VALUE_BOTTLE_VOLUME), selectedDate);
    }

    @Override
    public void addAmountOfWater(Float amount, String selectedDate) {
        compositeDisposable.add(addAmountOfWaterUseCase.get().execute(new AddAmountOfWaterUseCase.Input(amount, selectedDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        updateLiquidStatus(selectedDate);
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void updateAmountOfWater(Float amount, String selectedDate) {
        view.showLoading();
        compositeDisposable.add(updateAmountOfWaterUseCase.get().execute(new UpdateAmountOfWaterUseCase.Input(amount, selectedDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        updateLiquidStatus(selectedDate);
                        view.hideLoading();
                    }
                }));
    }

    private void getNutritionValuesForDiaryEntries(List<DiaryEntryDomainModel> diaryEntries) {
        if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            compositeDisposable.add(Observable.just(nutritionManager.getCaloryMaxValueForDay())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output1 -> {
                        view.setNutritionMaxValues(output1);
                        Observable.just(nutritionManager.calculateTotalCalories(diaryEntries))
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(output2 -> view.updateNutritionDetails(output2));
                    }));
        } else {
            compositeDisposable.add(Observable.just(nutritionManager.getNutritionMaxValuesForDay())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output1 -> {
                        view.setNutritionMaxValues(output1);
                        Observable.just(nutritionManager.calculateTotalNutrition(diaryEntries))
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(output2 -> view.updateNutritionDetails(output2));
                    }));
        }
    }

    private void getDefaultNutritionValues() {
        if (sharedPreferencesUtil.getString(KEY_SETTING_NUTRITION_DETAILS, VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY).equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            compositeDisposable.add(Observable.just(nutritionManager.getCaloryMaxValueForDay())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output1 -> {
                        view.setNutritionMaxValues(output1);
                        Observable.just(nutritionManager.getDefaultValuesForTotalCalories())
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(output2 -> view.updateNutritionDetails(output2));
                    }));
        } else {
            compositeDisposable.add(Observable.just(nutritionManager.getNutritionMaxValuesForDay())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output1 -> {
                        view.setNutritionMaxValues(output1);
                        Observable.just(nutritionManager.getDefaultValuesForTotalNutrition())
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(output2 -> view.updateNutritionDetails(output2));
                    }));
        }
    }

    @Override
    public void setView(GenericDrinkContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void onItemClicked(int id) {
        //start edit
    }

    @Override
    public void onItemDelete(int id) {
        view.showLoading();
        compositeDisposable.add(deleteDiaryEntryUseCase.get().execute(new DeleteDiaryEntryUseCase.Input(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    if (output.getStatus() == 0) {
                        view.refreshLiquidStatus();
                        view.hideLoading();
                    } else {
                        //todo error handling
                        view.hideLoading();
                    }
                }));
    }

    @Override
    public void onItemEdit(int id) {

    }
}
