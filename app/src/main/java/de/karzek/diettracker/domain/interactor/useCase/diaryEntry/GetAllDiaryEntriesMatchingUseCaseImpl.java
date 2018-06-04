package de.karzek.diettracker.domain.interactor.useCase.diaryEntry;

import java.util.List;

import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.DiaryEntryRepositoryImpl;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.diaryEntry.GetAllDiaryEntriesMatchingUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.mapper.DiaryEntryDomainMapper;
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
public class GetAllDiaryEntriesMatchingUseCaseImpl implements GetAllDiaryEntriesMatchingUseCase {

    private final DiaryEntryRepositoryImpl repository;
    private final DiaryEntryDomainMapper mapper;

    public GetAllDiaryEntriesMatchingUseCaseImpl(DiaryEntryRepositoryImpl repository, DiaryEntryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllDiaryEntriesMatching(input.getMeal(), input.getDate()).map(new Function<List<DiaryEntryDataModel>, Output>() {
            @Override
            public Output apply(List<DiaryEntryDataModel> diaryEntryDataModels) {
                return new Output( mapper.transformAll(diaryEntryDataModels), Output.SUCCESS);
            }
        });
    }

}