package de.karzek.diettracker.domain.interactor.useCase.recipe;

import java.util.List;

import de.karzek.diettracker.data.model.RecipeDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.repositoryInterface.RecipeRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetAllRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.mapper.RecipeDomainMapper;
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
public class GetAllRecipesUseCaseImpl implements GetAllRecipesUseCase {

    private final RecipeRepository repository;
    private final RecipeDomainMapper mapper;

    public GetAllRecipesUseCaseImpl(RecipeRepository repository, RecipeDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllRecipes(input.getFilterOptions(), input.getSortOption(), input.isAsc()).map(new Function<List<RecipeDataModel>, Output>() {
            @Override
            public Output apply(List<RecipeDataModel> dataModels) {
                return new Output( mapper.transformAll(dataModels), Output.SUCCESS);
            }
        });
    }

}