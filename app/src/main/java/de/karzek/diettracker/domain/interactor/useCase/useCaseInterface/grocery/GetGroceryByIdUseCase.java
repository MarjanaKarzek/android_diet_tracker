package de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery;

import android.support.annotation.IntDef;

import de.karzek.diettracker.domain.common.BaseObservableUseCase;
import de.karzek.diettracker.domain.common.BaseUseCase;
import de.karzek.diettracker.domain.model.GroceryDomainModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface GetGroceryByIdUseCase extends BaseObservableUseCase<GetGroceryByIdUseCase.Input, GetGroceryByIdUseCase.Output> {

    @AllArgsConstructor
    @Data
    class Input implements BaseUseCase.Input {
        public int id;
    }

    @AllArgsConstructor
    @Data
    class Output implements BaseUseCase.Output {

        @GroceryByIdStatus
        int status;
        public static final int ERROR_NO_DATA = 0;
        public static final int ERROR_NETWORK_PROBLEM = 1;
        public static final int ERROR_UNKNOWN_PROBLEM = 2;
        public static final int SUCCESS = 3;

        GroceryDomainModel grocery;

        @IntDef({ERROR_NO_DATA, ERROR_NETWORK_PROBLEM, ERROR_UNKNOWN_PROBLEM, SUCCESS})

        public @interface GroceryByIdStatus {

        }
    }
}