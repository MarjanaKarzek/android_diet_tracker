package de.karzek.diettracker.domain.interactor.useCase.meal;

import java.util.List;

import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
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
public class GetMealByIdUseCaseImpl implements GetMealByIdUseCase {

    private final MealRepository repository;
    private final MealDomainMapper mapper;

    public GetMealByIdUseCaseImpl(MealRepository repository, MealDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getMealById(input.getId()).map(new Function<MealDataModel, Output>() {
            @Override
            public Output apply(MealDataModel mealDataModel) {
                return new Output(mapper.transform(mealDataModel), Output.SUCCESS);
            }
        });
    }

}