package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.DiaryEntryCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.DiaryEntryCache;
import de.karzek.diettracker.data.cache.model.DiaryEntryEntity;
import de.karzek.diettracker.data.mapper.DiaryEntryDataMapper;
import de.karzek.diettracker.data.mapper.MealDataMapper;
import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.repository.datasource.local.DiaryEntryLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.DiaryEntryRepository;
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

    private final DiaryEntryDataMapper diaryEntryMapper;
    private final MealDataMapper mealMapper;
    private final DiaryEntryCache diaryEntryCache;

    public DiaryEntryRepositoryImpl(DiaryEntryCache diaryEntryCache, DiaryEntryDataMapper diaryEntryMapper, MealDataMapper mealMapper) {
        this.diaryEntryCache = diaryEntryCache;
        this.mealMapper = mealMapper;
        this.diaryEntryMapper = diaryEntryMapper;
    }

    @Override
    public Observable<Boolean> putDiaryEntry(DiaryEntryDataModel diaryEntryDataModel) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).putDiaryEntry(diaryEntryMapper.transformToEntity(diaryEntryDataModel));
    }

    @Override
    public Observable<List<DiaryEntryDataModel>> getAllDiaryEntriesMatching(String meal, String date) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).getAllDiaryEntriesMatching(meal, date).map(new Function<List<DiaryEntryEntity>, List<DiaryEntryDataModel>>() {
            @Override
            public List<DiaryEntryDataModel> apply(List<DiaryEntryEntity> diaryEntryEntities) {
                return diaryEntryMapper.transformAll(diaryEntryEntities);
            }
        });
    }

    @Override
    public Observable<Boolean> deleteDiaryEntry(int id) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).deleteDiaryEntry(id);
    }

    @Override
    public Observable<Boolean> updateMealOfDiaryEntry(int id, MealDataModel meal) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).updateMealOfDiaryEntry(id, mealMapper.transformToEntity(meal));
    }

    @Override
    public Observable<DiaryEntryDataModel> getWaterStatus(String date) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).getWaterStatus(date).map(new Function<DiaryEntryEntity, DiaryEntryDataModel>() {
            @Override
            public DiaryEntryDataModel apply(DiaryEntryEntity diaryEntryEntities) {
                return diaryEntryMapper.transform(diaryEntryEntities);
            }
        });
    }

    @Override
    public Observable<Boolean> updateAmountOfWater(float amount, String date) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).updateAmountOfWater(amount, date);
    }

    @Override
    public Observable<Boolean> addAmountOfWater(float amount, String date) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).addAmountOfWater(amount, date);
    }

    @Override
    public Observable<DiaryEntryDataModel> getDiaryEntryById(int id) {
        return new DiaryEntryLocalDataSourceImpl(diaryEntryCache).getDiaryEntryById(id).map(new Function<DiaryEntryEntity, DiaryEntryDataModel>() {
            @Override
            public DiaryEntryDataModel apply(DiaryEntryEntity diaryEntryEntities) {
                return diaryEntryMapper.transform(diaryEntryEntities);
            }
        });
    }
}