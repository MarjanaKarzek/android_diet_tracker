package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.DiaryEntryCache;
import de.karzek.diettracker.data.cache.interfaces.UnitCache;
import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.repository.datasource.interfaces.DiaryEntryDataSource;
import de.karzek.diettracker.data.repository.datasource.interfaces.UnitDataSource;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class DiaryEntryLocalDataSourceImpl implements DiaryEntryDataSource {

    private final DiaryEntryCache diaryEntryCache;

    public DiaryEntryLocalDataSourceImpl(DiaryEntryCache diaryEntryCache){
        this.diaryEntryCache = diaryEntryCache;
    }

    @Override
    public Observable<Boolean> putDiaryEntry(DiaryEntryEntity diaryEntryEntity) {
        return diaryEntryCache.putDiaryEntry(diaryEntryEntity);
    }

    @Override
    public Observable<List<DiaryEntryEntity>> getAllDiaryEntriesMatching(String meal, String date) {
        return diaryEntryCache.getAllDiaryEntriesMatching(meal, date);
    }
}
