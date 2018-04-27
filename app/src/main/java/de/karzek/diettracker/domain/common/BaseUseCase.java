package de.karzek.diettracker.domain.common;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public interface BaseUseCase<IN extends BaseUseCase.Input, OUT extends BaseUseCase.Output> {

    interface Input {
    }

    interface Output {
    }

}
