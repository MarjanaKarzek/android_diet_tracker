package de.karzek.diettracker.domain.interactor.useCase.meal;

import java.util.List;

import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.repository.MealRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealCountUseCase;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetMealCountUseCaseImpl implements GetMealCountUseCase {

    private final MealRepositoryImpl repository;

    public GetMealCountUseCaseImpl(MealRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getMealCount().map(new Function<Long, Output>() {
            @Override
            public Output apply(Long count) {
                return new Output(count, Output.SUCCESS);
            }
        });
    }

}