package de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class RecipeFilterOptionsDialogPresenter implements RecipeFilterOptionsDialogContract.Presenter {

    private RecipeFilterOptionsDialogContract.View view;

    private GetAllMealsUseCase getAllMealsUseCase;
    private MealUIMapper mapper;

    private ArrayList<String> meals = new ArrayList<>();
    private ArrayList<String> preSelectedFilterOptions = new ArrayList<>();
    private HashMap<String, Boolean> optionStatus = new HashMap<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RecipeFilterOptionsDialogPresenter(GetAllMealsUseCase getAllMealsUseCase,
                                              MealUIMapper mapper) {
        this.getAllMealsUseCase = getAllMealsUseCase;
        this.mapper = mapper;
    }

    @Override
    public void start() {
        view.showLoading();
        compositeDisposable.add(getAllMealsUseCase.execute(new GetAllMealsUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    ArrayList<MealDisplayModel> displayModels = mapper.transformAll(output.getMealList());

                    for (MealDisplayModel meal : displayModels) {
                        if (preSelectedFilterOptions.contains(meal.getName()))
                            optionStatus.put(meal.getName(), true);
                        else
                            optionStatus.put(meal.getName(), false);
                        meals.add(meal.getName());
                    }

                    view.updateRecyclerView(meals, optionStatus);
                    view.hideLoading();
                })
        );
    }

    @Override
    public void setView(RecipeFilterOptionsDialogContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void onItemCheckChanged(String filterOption, boolean checked) {
        optionStatus.put(filterOption, checked);
        view.updateRecyclerView(meals, optionStatus);
    }

    @Override
    public ArrayList<String> getSelectedFilterOptions() {
        ArrayList<String> selectedOptions = new ArrayList<>();

        for (String key : optionStatus.keySet()) {
            if (optionStatus.get(key))
                selectedOptions.add(key);
        }

        return selectedOptions;
    }

    @Override
    public void onResetSelectionClicked() {
        for (String key : optionStatus.keySet()) {
            optionStatus.put(key, false);
        }
        view.updateRecyclerView(meals, optionStatus);
    }

    @Override
    public void setFilterOptions(ArrayList<String> filterOptions) {
        preSelectedFilterOptions = filterOptions;
    }
}
