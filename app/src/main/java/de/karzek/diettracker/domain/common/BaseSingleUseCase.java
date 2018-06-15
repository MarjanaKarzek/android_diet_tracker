package de.karzek.diettracker.domain.common;

import io.reactivex.Single;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public interface BaseSingleUseCase<Input extends BaseUseCase.Input, Output extends BaseUseCase.Output>
        extends BaseUseCase {

    Single<Output> execute(Input input);

}
