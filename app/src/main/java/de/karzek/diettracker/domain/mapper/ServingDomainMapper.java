package de.karzek.diettracker.domain.mapper;

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
public class ServingDomainMapper {
    public ServingDomainModel transform(ServingDataModel servingDataModel){
        ServingDomainModel servingDomainModel = null;
        if(servingDataModel != null){
            servingDomainModel = new ServingDomainModel(servingDataModel.getId(),
                    servingDataModel.getDescription(),
                    servingDataModel.getAmount(),
                    new UnitDomainMapper().transform(servingDataModel.getUnit())
            );
        }
        return servingDomainModel;
    }

    public ArrayList<ServingDomainModel> transformAll(List<ServingDataModel> servingDataModelList){
        ArrayList<ServingDomainModel> servingDomainModels = new ArrayList<>();
        for (ServingDataModel data: servingDataModelList){
            servingDomainModels.add(transform(data));
        }
        return servingDomainModels;
    }

    public ServingDataModel transformToData(ServingDomainModel servingDomainModel){
        ServingDataModel servingDataModel = null;
        if(servingDomainModel != null){
            servingDataModel = new ServingDataModel(servingDomainModel.getId(),
                    servingDomainModel.getDescription(),
                    servingDomainModel.getAmount(),
                    new UnitDomainMapper().transformToData(servingDomainModel.getUnit())
            );
        }
        return servingDataModel;
    }

    public ArrayList<ServingDataModel> transformAllToData(List<ServingDomainModel> servingDomainModels) {
        ArrayList<ServingDataModel> servingDataModels = new ArrayList<>();
        for (ServingDomainModel data: servingDomainModels){
            servingDataModels.add(transformToData(data));
        }
        return servingDataModels;
    }
}
