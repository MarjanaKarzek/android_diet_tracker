package de.karzek.diettracker.data.cache.interfaces;

import java.util.List;

import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.MealDataModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface DiaryEntryCache {
    boolean isExpired();
    boolean isCached();

    Observable<Boolean> putDiaryEntry(DiaryEntryEntity diaryEntryEntity);

    Observable<List<DiaryEntryEntity>> getAllDiaryEntriesMatching(String meal, String date);

    Observable<Boolean> deleteDiaryEntry(int id);

    Observable<Boolean> updateMealOfDiaryEntry(int id, MealEntity meal);
}
