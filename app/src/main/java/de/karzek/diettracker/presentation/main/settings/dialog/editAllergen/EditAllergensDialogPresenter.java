package de.karzek.diettracker.presentation.main.settings.dialog.editAllergen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllAllergensUseCase;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
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

public class EditAllergensDialogPresenter implements EditAllergensDialogContract.Presenter {

    private EditAllergensDialogContract.View view;

    private ArrayList<AllergenDisplayModel> allergens = new ArrayList<>();
    private HashMap<Integer, Boolean> allergenStatus = new HashMap<>();

    private SharedPreferencesManager sharedPreferencesManager;
    private GetAllAllergensUseCase getAllAllergensUseCase;
    private AllergenUIMapper allergenMapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public EditAllergensDialogPresenter(SharedPreferencesManager sharedPreferencesManager,
                                        GetAllAllergensUseCase getAllAllergensUseCase,
                                        AllergenUIMapper allergenMapper) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.getAllAllergensUseCase = getAllAllergensUseCase;
        this.allergenMapper = allergenMapper;
    }

    @Override
    public void start() {
        view.showLoading();
        compositeDisposable.add(getAllAllergensUseCase.execute(new GetAllAllergensUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    allergens = allergenMapper.transformAll(output.getAllergens());
                    for (AllergenDisplayModel allergen : allergens)
                        allergenStatus.put(allergen.getId(), false);

                    Observable.fromArray(sharedPreferencesManager.getAllergenIds().toArray())
                            .concatMap(item -> {
                                allergenStatus.put((Integer) item, true);
                                return Observable.just(item);
                            })
                            .toList()
                            .subscribe(output2 -> {
                                view.updateRecyclerView(allergens, allergenStatus);
                                view.hideLoading();
                            });
                })
        );
    }

    @Override
    public void setView(EditAllergensDialogContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }

    @Override
    public void onItemCheckChanged(int id, boolean checked) {
        allergenStatus.put(id, checked);
        view.updateRecyclerView(allergens, allergenStatus);
    }

    @Override
    public void saveAllergenSelection() {
        String allergenSelection = "";
        Set keySet = allergenStatus.keySet();

        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()) {
            Integer current = (Integer) iterator.next();
            if(allergenStatus.get(current))
                allergenSelection += current + Constants.SHARED_PREFERENCES_SPLIT_ARRAY_CHAR;
        }

        sharedPreferencesManager.putAllergenIds(allergenSelection.substring(0, allergenSelection.length() - 1));
    }
}
