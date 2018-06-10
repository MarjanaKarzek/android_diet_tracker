package de.karzek.diettracker.domain.interactor.useCase.diaryEntry;

import de.karzek.diettracker.data.repository.DiaryEntryRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.AddAmountOfWaterUseCase;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class AddAmountOfWaterUseCaseImpl implements AddAmountOfWaterUseCase {

    private final DiaryEntryRepositoryImpl repository;

    public AddAmountOfWaterUseCaseImpl(DiaryEntryRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.addAmountOfWater(input.getAmount(), input.getDate()).map(output -> {
            if (output) {
                return new Output(Output.SUCCESS);
            } else {
                return new Output(Output.ERROR_NO_DATA);
            }
        }).onErrorReturn(throwable -> new Output(Output.ERROR_UNKNOWN_PROBLEM));
    }

}