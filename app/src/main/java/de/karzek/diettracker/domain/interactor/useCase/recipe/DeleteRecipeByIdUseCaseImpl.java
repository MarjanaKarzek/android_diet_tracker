package de.karzek.diettracker.domain.interactor.useCase.recipe;

import de.karzek.diettracker.data.repository.repositoryInterface.DiaryEntryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.RecipeRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.DeleteRecipeByIdUseCase;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class DeleteRecipeByIdUseCaseImpl implements DeleteRecipeByIdUseCase {

    private final RecipeRepository repository;

    public DeleteRecipeByIdUseCaseImpl(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.deleteRecipe(input.getId()).map(output -> {
            if (output) {
                return new Output(Output.SUCCESS);
            } else {
                return new Output(Output.ERROR_UNKNOWN_PROBLEM);
            }
        }).onErrorReturn(throwable -> new Output(Output.ERROR_UNKNOWN_PROBLEM));
    }

}