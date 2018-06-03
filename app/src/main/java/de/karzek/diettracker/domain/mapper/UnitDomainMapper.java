package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.ServingDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.domain.model.ServingDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class UnitDomainMapper {
    public UnitDomainModel transform(UnitDataModel unitDataModel){
        UnitDomainModel unitDomainModel = null;
        if(unitDataModel != null){
            unitDomainModel = new UnitDomainModel(unitDataModel.getId(),
                    unitDataModel.getName(),
                    unitDataModel.getMultiplier(),
                    unitDataModel.getType()
            );
        }
        return unitDomainModel;
    }

    public ArrayList<UnitDomainModel> transformAll(List<UnitDataModel> unitDataModels){
        ArrayList<UnitDomainModel> unitDomainModels = new ArrayList<>();
        for (UnitDataModel data: unitDataModels){
            unitDomainModels.add(transform(data));
        }
        return unitDomainModels;
    }

    public UnitDataModel transformToData(UnitDomainModel unitDomainModel){
        UnitDataModel unitDataModel = null;
        if(unitDomainModel != null){
            unitDataModel = new UnitDataModel(unitDomainModel.getId(),
                    unitDomainModel.getName(),
                    unitDomainModel.getMultiplier(),
                    unitDomainModel.getType()
            );
        }
        return unitDataModel;
    }

    public ArrayList<UnitDataModel> transformAllToData(List<UnitDomainModel> unitDomainModels) {
        ArrayList<UnitDataModel> unitDataModels = new ArrayList<>();
        for (UnitDomainModel data: unitDomainModels){
            unitDataModels.add(transformToData(data));
        }
        return unitDataModels;
    }
}
