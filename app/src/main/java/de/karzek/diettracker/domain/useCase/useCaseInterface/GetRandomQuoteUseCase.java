package de.karzek.diettracker.domain.useCase.useCaseInterface;

import android.support.annotation.IntDef;

import de.karzek.diettracker.domain.model.RandomQuoteData;
import de.karzek.diettracker.domain.common.BaseObservableUseCase;
import de.karzek.diettracker.domain.common.BaseUseCase;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public interface GetRandomQuoteUseCase extends BaseObservableUseCase<GetRandomQuoteUseCase.Input, GetRandomQuoteUseCase.Output> {

    class Input implements BaseUseCase.Input{

    }

    @AllArgsConstructor
    @Data
    class Output implements BaseUseCase.Output {

        @RandomQuoteStatus int status;
        public static final int ERROR_NO_DATA = 0;
        public static final int ERROR_NETWORK_PROBLEM = 1;
        public static final int ERROR_UNKNOWN_PROBLEM = 2;
        public static final int SUCCESS = 3;

        RandomQuoteData randomQuoteData;

        @IntDef({ ERROR_NO_DATA, ERROR_NETWORK_PROBLEM, ERROR_UNKNOWN_PROBLEM, SUCCESS })

        public @interface RandomQuoteStatus {

        }
    }
}
