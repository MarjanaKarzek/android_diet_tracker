package de.karzek.diettracker.data.repository.repositoryInterface;

import java.util.List;

import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.model.MealDataModel;
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

    Observable<Boolean> updateMealOfDiaryEntry(int id, MealDataModel mealDataModel);

    Observable<DiaryEntryDataModel> getWaterStatus(String date);

    Observable<Boolean> updateAmountOfWater(float amount, String date);

    Observable<Boolean> addAmountOfWater(float amount, String date);

    Observable<DiaryEntryDataModel> getDiaryEntryById(int id);

    Observable<Boolean> deleteAllDiaryEntriesMatchingMealId(int mealId);
}
