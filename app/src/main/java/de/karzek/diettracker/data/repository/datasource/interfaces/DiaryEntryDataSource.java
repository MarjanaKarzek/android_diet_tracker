package de.karzek.diettracker.data.repository.datasource.interfaces;

import java.util.List;

import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.model.MealDataModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface DiaryEntryDataSource {

    Observable<Boolean> putDiaryEntry(DiaryEntryEntity diaryEntryEntity);

    Observable<List<DiaryEntryEntity>> getAllDiaryEntriesMatching(String meal, String date);

    Observable<Boolean> deleteDiaryEntry(int id);

    Observable<Boolean> updateMealOfDiaryEntry(int id, MealEntity meal);

    Observable<DiaryEntryEntity>  getWaterStatus(String date);

    Observable<Boolean> updateAmountOfWater(float amount, String date);

    Observable<Boolean> addAmountOfWater(float amount, String date);

    Observable<DiaryEntryEntity> getDiaryEntryById(int id);
}
