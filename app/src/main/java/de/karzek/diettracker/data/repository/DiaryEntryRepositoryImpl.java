package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.DiaryEntryCacheImpl;
import de.karzek.diettracker.data.cache.UnitCacheImpl;
import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.mapper.DiaryEntryDataMapper;
import de.karzek.diettracker.data.mapper.UnitDataMapper;
import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.datasource.local.DiaryEntryLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.datasource.local.UnitLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.DiaryEntryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class DiaryEntryRepositoryImpl implements DiaryEntryRepository {

    private final DiaryEntryDataMapper mapper;
    private final DiaryEntryCacheImpl diaryEntryCache;

    public DiaryEntryRepositoryImpl(DiaryEntryCacheImpl diaryEntryCache, DiaryEntryDataMapper mapper) {
        this.diaryEntryCache = diaryEntryCache;
        this.mapper = mapper;
    }

    @Override
    public Observable<Boolean> putDiaryEntry(DiaryEntryDataModel diaryEntryDataModel) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).putDiaryEntry(mapper.transformToEntity(diaryEntryDataModel));
    }

    @Override
    public Observable<List<DiaryEntryDataModel>> getAllDiaryEntriesMatching(String meal, String date) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).getAllDiaryEntriesMatching(meal, date).map(new Function<List<DiaryEntryEntity>, List<DiaryEntryDataModel>>() {
            @Override
            public List<DiaryEntryDataModel> apply(List<DiaryEntryEntity> diaryEntryEntities) {
                return mapper.transformAll(diaryEntryEntities);
            }
        });
    }

    @Override
    public Observable<Boolean> deleteDiaryEntry(int id) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).deleteDiaryEntry(id);
    }
}