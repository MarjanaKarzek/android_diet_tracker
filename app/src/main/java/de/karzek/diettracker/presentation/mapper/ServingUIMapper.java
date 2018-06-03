package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.ServingDataModel;
import de.karzek.diettracker.domain.model.ServingDomainModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class ServingUIMapper {
    public ServingDisplayModel transform(ServingDomainModel servingDomainModel){
        ServingDisplayModel servingDisplayModel = null;
        if(servingDomainModel != null){
            servingDisplayModel = new ServingDisplayModel(servingDomainModel.getId(),
                    servingDomainModel.getDescription(),
                    servingDomainModel.getAmount(),
                    new UnitUIMapper().transform(servingDomainModel.getUnit())
            );
        }
        return servingDisplayModel;
    }

    public ArrayList<ServingDisplayModel> transformAll(List<ServingDomainModel> servingDomainModels){
        ArrayList<ServingDisplayModel> servingDisplayModels = new ArrayList<>();
        for (ServingDomainModel data: servingDomainModels){
            servingDisplayModels.add(transform(data));
        }
        return servingDisplayModels;
    }

    public ServingDomainModel transformToDomain(ServingDisplayModel servingDisplayModel){
        ServingDomainModel servingDomainModel = null;
        if(servingDisplayModel != null){
            servingDomainModel = new ServingDomainModel(servingDisplayModel.getId(),
                    servingDisplayModel.getDescription(),
                    servingDisplayModel.getAmount(),
                    new UnitUIMapper().transformToDomain(servingDisplayModel.getUnit())
            );
        }
        return servingDomainModel;
    }

    public ArrayList<ServingDomainModel> transformAllToDomain(ArrayList<ServingDisplayModel> servingDisplayModels) {
        ArrayList<ServingDomainModel> servingDomainModels = new ArrayList<>();
        for (ServingDisplayModel data: servingDisplayModels){
            servingDomainModels.add(transformToDomain(data));
        }
        return servingDomainModels;
    }
}
