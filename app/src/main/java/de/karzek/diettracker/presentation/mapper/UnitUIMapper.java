package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.model.ServingDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class UnitUIMapper {
    public UnitDisplayModel transform(UnitDomainModel unitDomainModel){
        UnitDisplayModel unitDisplayModel = null;
        if(unitDomainModel != null){
            unitDisplayModel = new UnitDisplayModel(unitDomainModel.getId(),
                    unitDomainModel.getName(),
                    unitDomainModel.getMultiplier(),
                    unitDomainModel.getType()
            );
        }
        return unitDisplayModel;
    }

    public ArrayList<UnitDisplayModel> transformAll(List<UnitDomainModel> unitDomainModels){
        ArrayList<UnitDisplayModel> unitDisplayModels = new ArrayList<>();
        for (UnitDomainModel data: unitDomainModels){
            unitDisplayModels.add(transform(data));
        }
        return unitDisplayModels;
    }

    public UnitDomainModel transformToDomain(UnitDisplayModel unitDisplayModel){
        UnitDomainModel unitDomainModel = null;
        if(unitDisplayModel != null){
            unitDomainModel = new UnitDomainModel(unitDisplayModel.getId(),
                    unitDisplayModel.getName(),
                    unitDisplayModel.getMultiplier(),
                    unitDisplayModel.getType()
            );
        }
        return unitDomainModel;
    }

    public ArrayList<UnitDomainModel> transformAllToDomain(ArrayList<UnitDisplayModel> unitDisplayModels) {
        ArrayList<UnitDomainModel> unitDomainModels = new ArrayList<>();
        for (UnitDisplayModel data: unitDisplayModels){
            unitDomainModels.add(transformToDomain(data));
        }
        return unitDomainModels;
    }
}
