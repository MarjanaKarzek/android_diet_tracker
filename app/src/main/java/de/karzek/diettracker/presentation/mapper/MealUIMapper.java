package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.model.MealDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class MealUIMapper {
    public MealDisplayModel transform(MealDomainModel mealDomainModel){
        MealDisplayModel mealDisplayModel = null;
        if(mealDomainModel != null){
            mealDisplayModel = new MealDisplayModel(mealDomainModel.getId(),
                    mealDomainModel.getName(),
                    mealDomainModel.getStartTime(),
                    mealDomainModel.getEndTime()
            );
        }
        return mealDisplayModel;
    }

    public ArrayList<MealDisplayModel> transformAll(List<MealDomainModel> mealDomainModels){
        ArrayList<MealDisplayModel> mealDisplayModels = new ArrayList<>();
        for (MealDomainModel data: mealDomainModels){
            mealDisplayModels.add(transform(data));
        }
        return mealDisplayModels;
    }

    public MealDomainModel transformToDomain(MealDisplayModel mealDisplayModel){
        MealDomainModel mealDomainModel = null;
        if(mealDisplayModel != null){
            mealDomainModel = new MealDomainModel(mealDisplayModel.getId(),
                    mealDisplayModel.getName(),
                    mealDisplayModel.getStartTime(),
                    mealDisplayModel.getEndTime()
            );
        }
        return mealDomainModel;
    }

    public ArrayList<MealDomainModel> transformAllToDomain(ArrayList<MealDisplayModel> mealDisplayModels) {
        ArrayList<MealDomainModel> mealDomainModels = new ArrayList<>();
        for (MealDisplayModel data: mealDisplayModels){
            mealDomainModels.add(transformToDomain(data));
        }
        return mealDomainModels;
    }
}
