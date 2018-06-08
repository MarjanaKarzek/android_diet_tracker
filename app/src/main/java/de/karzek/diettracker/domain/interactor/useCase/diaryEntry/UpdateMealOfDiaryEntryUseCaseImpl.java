package de.karzek.diettracker.domain.interactor.useCase.diaryEntry;

import de.karzek.diettracker.data.repository.DiaryEntryRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.PutDiaryEntryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.UpdateMealOfDiaryEntryUseCase;
import de.karzek.diettracker.domain.mapper.DiaryEntryDomainMapper;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.domain.model.MealDomainModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class UpdateMealOfDiaryEntryUseCaseImpl implements UpdateMealOfDiaryEntryUseCase {

    private final DiaryEntryRepositoryImpl repository;
    private final MealDomainMapper mapper;

    public UpdateMealOfDiaryEntryUseCaseImpl(DiaryEntryRepositoryImpl repository, MealDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.updateMealOfDiaryEntry(input.getId(), mapper.transformToData(input.getMeal())).map(output -> {
            if (output) {
                return new Output(Output.SUCCESS);
            } else {
                return new Output(Output.ERROR_NO_DATA);
            }
        }).onErrorReturn(throwable -> new Output(Output.ERROR_UNKNOWN_PROBLEM));
    }

}