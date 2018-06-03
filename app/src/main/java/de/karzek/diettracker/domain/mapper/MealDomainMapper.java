package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.domain.model.MealDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class MealDomainMapper {
    public MealDomainModel transform(MealDataModel mealDataModel){
        MealDomainModel mealDomainModel = null;
        if(mealDataModel != null){
            mealDomainModel = new MealDomainModel(mealDataModel.getId(),
                    mealDataModel.getName(),
                    mealDataModel.getStartTime(),
                    mealDataModel.getEndTime()
            );
        }
        return mealDomainModel;
    }

    public ArrayList<MealDomainModel> transformAll(List<MealDataModel> mealDataModels){
        ArrayList<MealDomainModel> mealDomainModels = new ArrayList<>();
        for (MealDataModel data: mealDataModels){
            mealDomainModels.add(transform(data));
        }
        return mealDomainModels;
    }

    public MealDataModel transformToData(MealDomainModel mealDomainModel){
        MealDataModel mealDataModel = null;
        if(mealDomainModel != null){
            mealDataModel = new MealDataModel(mealDomainModel.getId(),
                    mealDomainModel.getName(),
                    mealDomainModel.getStartTime(),
                    mealDomainModel.getEndTime()
            );
        }
        return mealDataModel;
    }

    public ArrayList<MealDataModel> transformAllToData(List<MealDomainModel> mealDomainModels) {
        ArrayList<MealDataModel> mealDataModels = new ArrayList<>();
        for (MealDomainModel data: mealDomainModels){
            mealDataModels.add(transformToData(data));
        }
        return mealDataModels;
    }
}
