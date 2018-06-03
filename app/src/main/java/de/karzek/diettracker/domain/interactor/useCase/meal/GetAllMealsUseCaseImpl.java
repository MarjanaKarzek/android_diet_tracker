package de.karzek.diettracker.domain.interactor.useCase.meal;

import java.util.List;

import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.MealRepositoryImpl;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetAllMealsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetAllMealsUseCaseImpl implements GetAllMealsUseCase {

    private final MealRepositoryImpl repository;
    private final MealDomainMapper mapper;

    public GetAllMealsUseCaseImpl(MealRepositoryImpl repository, MealDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllMeals().map(new Function<List<MealDataModel>, Output>() {
            @Override
            public Output apply(List<MealDataModel> mealDataModels) {
                return new Output( mapper.transformAll(mealDataModels), Output.SUCCESS);
            }
        });
    }

}