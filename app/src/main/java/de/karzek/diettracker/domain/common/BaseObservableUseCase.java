package de.karzek.diettracker.domain.common;

import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 26.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 26.04.2018
 */

public interface BaseObservableUseCase<Input extends BaseUseCase.Input, Output extends BaseUseCase.Output>
        extends BaseUseCase {

    Observable<Output> execute(Input input);

}
