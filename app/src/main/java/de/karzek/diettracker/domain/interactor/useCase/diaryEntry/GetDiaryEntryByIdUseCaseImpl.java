package de.karzek.diettracker.domain.interactor.useCase.diaryEntry;

import java.util.List;

import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.repository.DiaryEntryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.DiaryEntryRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetDiaryEntryByIdUseCase;
import de.karzek.diettracker.domain.mapper.DiaryEntryDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetDiaryEntryByIdUseCaseImpl implements GetDiaryEntryByIdUseCase {

    private final DiaryEntryRepository repository;
    private final DiaryEntryDomainMapper mapper;

    public GetDiaryEntryByIdUseCaseImpl(DiaryEntryRepository repository, DiaryEntryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getDiaryEntryById(input.getId()).map(new Function<DiaryEntryDataModel, Output>() {
            @Override
            public Output apply(DiaryEntryDataModel diaryEntry) {
                return new Output( mapper.transform(diaryEntry), Output.SUCCESS);
            }
        });
    }

}