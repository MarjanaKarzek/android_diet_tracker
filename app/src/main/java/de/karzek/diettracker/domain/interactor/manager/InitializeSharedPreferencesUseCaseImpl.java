package de.karzek.diettracker.domain.interactor.manager;

import de.karzek.diettracker.domain.interactor.manager.managerInterface.InitializeSharedPreferencesUseCase;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class InitializeSharedPreferencesUseCaseImpl implements InitializeSharedPreferencesUseCase {

    private final SharedPreferencesUtil sharedPreferencesUtil;

    public InitializeSharedPreferencesUseCaseImpl(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return Observable.just(sharedPreferencesUtil.initialiseStandardValues())
                .map(output -> {
                    if (output) {
                        return new Output(Output.SUCCESS);
                    } else {
                        return new Output(Output.ERROR_UNKNOWN_PROBLEM);
                    }
                });
    }
}