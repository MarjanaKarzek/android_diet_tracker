package de.karzek.diettracker.data.repository.repositoryInterface;

import java.util.List;

import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface DiaryEntryRepository {

    Observable<Boolean> putDiaryEntry(DiaryEntryDataModel diaryEntryDataModel);

    Observable<List<DiaryEntryDataModel>> getAllDiaryEntriesMatching(String meal, String date);

    Observable<Boolean> deleteDiaryEntry(int id);
}
