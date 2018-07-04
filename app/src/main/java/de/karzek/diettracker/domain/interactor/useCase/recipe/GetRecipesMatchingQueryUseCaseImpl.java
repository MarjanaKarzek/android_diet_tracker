package de.karzek.diettracker.domain.interactor.useCase.recipe;

import java.util.List;

import de.karzek.diettracker.data.model.RecipeDataModel;
import de.karzek.diettracker.data.repository.repositoryInterface.RecipeRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetMatchingRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipesMatchingQueryUseCase;
import de.karzek.diettracker.domain.mapper.RecipeDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class GetRecipesMatchingQueryUseCaseImpl implements GetRecipesMatchingQueryUseCase {

    private final RecipeRepository repository;
    private final RecipeDomainMapper mapper;

    public GetRecipesMatchingQueryUseCaseImpl(RecipeRepository repository, RecipeDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllRecipesMatching(input.getQuery())
                .map(new Function<List<RecipeDataModel>, Output>() {
                    @Override
                    public Output apply(List<RecipeDataModel> dataModels) {
                        return new Output(Output.SUCCESS, mapper.transformAll(dataModels));
                    }
                });
    }
}
