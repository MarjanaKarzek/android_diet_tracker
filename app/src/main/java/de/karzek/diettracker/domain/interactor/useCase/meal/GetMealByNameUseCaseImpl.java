package de.karzek.diettracker.domain.interactor.useCase.meal;

import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetMealByNameUseCase;
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
public class GetMealByNameUseCaseImpl implements GetMealByNameUseCase {

    private final MealRepository repository;
    private final MealDomainMapper mapper;

    public GetMealByNameUseCaseImpl(MealRepository repository, MealDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getMealByName(input.getName()).map(new Function<MealDataModel, Output>() {
            @Override
            public Output apply(MealDataModel mealDataModel) {
                return new Output(mapper.transform(mealDataModel), Output.SUCCESS);
            }
        });
    }

}