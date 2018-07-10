package de.karzek.diettracker.domain.interactor.useCase.meal;

import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.PutAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.PutMealUseCase;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class PutMealUseCaseImpl implements PutMealUseCase {

    private final MealRepository repository;
    private final MealDomainMapper mapper;

    public PutMealUseCaseImpl(MealRepository repository, MealDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.putMeal(mapper.transformToData(input.getMeal())).map(output -> {
            if (output) {
                return new Output(Output.SUCCESS);
            } else {
                return new Output(Output.ERROR_UNKNOWN_PROBLEM);
            }
        }).onErrorReturn(throwable -> new Output(Output.ERROR_UNKNOWN_PROBLEM));
    }

}