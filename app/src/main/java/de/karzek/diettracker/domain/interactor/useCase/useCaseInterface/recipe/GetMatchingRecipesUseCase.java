package de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe;

import android.support.annotation.IntDef;

import java.util.List;

import de.karzek.diettracker.domain.common.BaseObservableUseCase;
import de.karzek.diettracker.domain.common.BaseUseCase;
import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.domain.model.RecipeDomainModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface GetMatchingRecipesUseCase extends BaseObservableUseCase<GetMatchingRecipesUseCase.Input, GetMatchingRecipesUseCase.Output> {

    @AllArgsConstructor
    @Data
    class Input implements BaseUseCase.Input {

        String query;

    }

    @AllArgsConstructor
    @Data
    class Output implements BaseUseCase.Output {

        @GetMatchingRecipesUseCaseStatus
        int status;
        public static final int ERROR_NO_DATA = 0;
        public static final int ERROR_NETWORK_PROBLEM = 1;
        public static final int ERROR_UNKNOWN_PROBLEM = 2;
        public static final int SUCCESS = 3;

        List<RecipeDomainModel> recipes;

        @IntDef({ERROR_NO_DATA, ERROR_NETWORK_PROBLEM, ERROR_UNKNOWN_PROBLEM, SUCCESS})

        private @interface GetMatchingRecipesUseCaseStatus {

        }
    }
}