package de.karzek.diettracker.data.repository.datasource.local;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.DiaryEntryCache;
import de.karzek.diettracker.data.cache.interfaces.UnitCache;
import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.model.MealDataModel;
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

    @Override
    public Observable<Boolean> deleteDiaryEntry(int id) {
        return diaryEntryCache.deleteDiaryEntry(id);
    }

    @Override
    public Observable<Boolean> updateMealOfDiaryEntry(int id, MealEntity meal) {
        return diaryEntryCache.updateMealOfDiaryEntry(id, meal);
    }

    @Override
    public Observable<DiaryEntryEntity> getWaterStatus(String date) {
        return diaryEntryCache.getWaterStatus(date);
    }

    @Override
    public Observable<Boolean> updateAmountOfWater(float amount, String date) {
        return diaryEntryCache.updateAmountOfWater(amount, date);
    }

    @Override
    public Observable<Boolean> addAmountOfWater(float amount, String date) {
        return diaryEntryCache.addAmountOfWater(amount, date);
    }

    @Override
    public Observable<DiaryEntryEntity> getDiaryEntryById(int id) {
        return diaryEntryCache.getDiaryEntryById(id);
    }

    @Override
    public Observable<Boolean> deleteAllDiaryEntriesMatchingMealId(int mealId) {
        return diaryEntryCache.deleteAllDiaryEntriesMatchingMealId(mealId);
    }
}
