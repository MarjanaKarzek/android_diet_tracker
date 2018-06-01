package de.karzek.diettracker.presentation.splash;

import java.util.ArrayList;

import de.karzek.diettracker.domain.interactor.useCase.PutAllGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.InitializeSharedPreferencesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.PutAllGroceriesUseCase;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;
import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_SOLID;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_APP_INITIALIZED;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View view;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private PutAllGroceriesUseCaseImpl putAllGroceriesUseCase;
    private InitializeSharedPreferencesUseCase initializeSharedPreferencesUseCase;

    private GroceryUIMapper mapper;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ArrayList<GroceryDisplayModel> groceries = new ArrayList<>();


    public SplashPresenter(SharedPreferencesUtil sharedPreferencesUtil, PutAllGroceriesUseCaseImpl putAllGroceriesUseCase, InitializeSharedPreferencesUseCase initializeSharedPreferencesUseCase, GroceryUIMapper mapper) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.putAllGroceriesUseCase = putAllGroceriesUseCase;
        this.initializeSharedPreferencesUseCase = initializeSharedPreferencesUseCase;

        this.mapper = mapper;
    }

    public void init(){
        groceries.add(new GroceryDisplayModel(0,0,"Brokkoli", 0.34f,0.038f,0.027f,0.002f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(),new ArrayList<>()));
        groceries.add(new GroceryDisplayModel(1,0,"Rote Paprika", 0.43f,0.013f,0.064f,0.005f, TYPE_FOOD, TYPE_SOLID, new ArrayList<>(),new ArrayList<>()));
    }

    @Override
    public void start() {
        if(!sharedPreferencesUtil.getBoolean(KEY_APP_INITIALIZED, false)){
            Disposable subs = Observable.zip(
                    initializeSharedPreferencesUseCase.execute(new InitializeSharedPreferencesUseCase.Input()),
                    putAllGroceriesUseCase.execute(new PutAllGroceriesUseCase.Input(mapper.transformAllToDomain(groceries))),
                    (output1,output2) -> (output1.getStatus() + output2.getStatus()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(output -> {
                                if (output == 0)
                                    view.startMainActivity();
                                //else
                                    //todo handle error
                            }
                    );
            compositeDisposable.add(subs);
        }
    }

    @Override
    public void setView(SplashContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        compositeDisposable.clear();
    }
}
