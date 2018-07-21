package de.karzek.diettracker.domain.interactor.useCase.diaryEntry;

import de.karzek.diettracker.data.repository.repositoryInterface.DiaryEntryRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteAllDiaryEntriesMatchingMealIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.DeleteDiaryEntryUseCase;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class DeleteAllDiaryEntriesMatchingMealIdUseCaseImpl implements DeleteAllDiaryEntriesMatchingMealIdUseCase {

    private final DiaryEntryRepository repository;

    public DeleteAllDiaryEntriesMatchingMealIdUseCaseImpl(DiaryEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.deleteAllDiaryEntriesMatchingMealId(input.getId()).map(output -> {
            if (output) {
                return new Output(Output.SUCCESS);
            } else {
                return new Output(Output.ERROR_UNKNOWN_PROBLEM);
            }
        }).onErrorReturn(throwable -> new Output(Output.ERROR_UNKNOWN_PROBLEM));
    }

}