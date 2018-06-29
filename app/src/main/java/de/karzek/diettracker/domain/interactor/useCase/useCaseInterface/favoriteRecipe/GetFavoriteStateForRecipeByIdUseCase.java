package de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe;

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
public interface GetFavoriteStateForRecipeByIdUseCase extends BaseObservableUseCase<GetFavoriteStateForRecipeByIdUseCase.Input, GetFavoriteStateForRecipeByIdUseCase.Output> {

    @AllArgsConstructor
    @Data
    class Input implements BaseUseCase.Input {
        int id;
    }

    @AllArgsConstructor
    @Data
    class Output implements BaseUseCase.Output {

        boolean state;

        @GetFavoriteStateForRecipeByIdUseCaseStatus
        int status;
        public static final int ERROR_NO_DATA = 0;
        public static final int ERROR_NETWORK_PROBLEM = 1;
        public static final int ERROR_UNKNOWN_PROBLEM = 2;
        public static final int SUCCESS = 3;

        @IntDef({ERROR_NO_DATA, ERROR_NETWORK_PROBLEM, ERROR_UNKNOWN_PROBLEM, SUCCESS})

        private @interface GetFavoriteStateForRecipeByIdUseCaseStatus {

        }
    }
}