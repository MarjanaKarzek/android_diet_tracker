package de.karzek.diettracker.domain.interactor.manager.managerInterface;

import android.support.annotation.IntDef;

import de.karzek.diettracker.domain.common.BaseObservableUseCase;
import de.karzek.diettracker.domain.common.BaseUseCase;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface InitializeSharedPreferencesUseCase extends BaseObservableUseCase<InitializeSharedPreferencesUseCase.Input, InitializeSharedPreferencesUseCase.Output> {

    @AllArgsConstructor
    @Data
    class Input implements BaseUseCase.Input {

    }

    @AllArgsConstructor
    @Data
    class Output implements BaseUseCase.Output {

        @InitializeSharedPreferencesStatus
        int status;
        public static final int SUCCESS = 0;
        public static final int ERROR_UNKNOWN_PROBLEM = 1;

        @IntDef({SUCCESS, ERROR_UNKNOWN_PROBLEM})

        private @interface InitializeSharedPreferencesStatus {

        }
    }
}