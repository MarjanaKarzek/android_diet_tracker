package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
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

public class EditMealsDialogPresenter implements EditMealsDialogContract.Presenter {

    private EditMealsDialogContract.View view;

    private ArrayList<MealDisplayModel> meals = new ArrayList<>();
    private ArrayList<MealDisplayModel> selectedMeals = new ArrayList<>();
    private HashMap<Integer, Boolean> mealStatus = new HashMap<>();

    private GetAllMealsUseCase getAllMealsUseCase;
    private MealUIMapper mealMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public EditMealsDialogPresenter(GetAllMealsUseCase getAllMealsUseCase,
                                    MealUIMapper mealMapper) {
        this.getAllMealsUseCase = getAllMealsUseCase;
        this.mealMapper = mealMapper;
    }

    @Override
    public void start() {
        view.showLoading();
        compositeDisposable.add(getAllMealsUseCase.execute(new GetAllMealsUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    meals = mealMapper.transformAll(output.getMealList());
                    for (MealDisplayModel meal : meals)
                        mealStatus.put(meal.getId(), false);

                    Observable.fromArray(selectedMeals.toArray())
                            .concatMap(item -> {
                                mealStatus.put(((MealDisplayModel)item).getId(), true);
                                return Observable.just(item);
                            })
                            .toList()
                            .subscribe(output2 -> {
                                view.updateRecyclerView(meals, mealStatus);
                                view.hideLoading();
                            });
                })
        );
    }

    @Override
    public void setView(EditMealsDialogContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void onItemCheckChanged(int id, boolean checked) {
        mealStatus.put(id, checked);
        view.updateRecyclerView(meals, mealStatus);
    }

    @Override
    public void setSelectedMealList(ArrayList<MealDisplayModel> mealList) {
        selectedMeals = mealList;
    }

    @Override
    public ArrayList<MealDisplayModel> getSelectedMeals() {
        ArrayList<MealDisplayModel> selection = new ArrayList<>();

        Set keySet = mealStatus.keySet();

        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()) {
            Integer current = (Integer) iterator.next();
            if (mealStatus.get(current))
                selection.add(meals.get(current));
        }

        return selection;
    }

    @Override
    public void onResetSelectionClicked() {
        for (Integer key : mealStatus.keySet()) {
            mealStatus.put(key, false);
        }
        view.updateRecyclerView(meals, mealStatus);
    }
}
