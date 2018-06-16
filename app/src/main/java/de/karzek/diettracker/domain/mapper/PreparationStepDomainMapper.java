package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.model.PreparationStepDataModel;
import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;
import de.karzek.diettracker.domain.model.PreparationStepDomainModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class PreparationStepDomainMapper {
    public PreparationStepDomainModel transform(PreparationStepDataModel dataModel){
        PreparationStepDomainModel domainModel = null;
        if(dataModel != null){
            domainModel = new PreparationStepDomainModel(dataModel.getId(),
                    dataModel.getStepNo(),
                    dataModel.getDescription());
        }
        return domainModel;
    }

    public ArrayList<PreparationStepDomainModel> transformAll(List<PreparationStepDataModel> dataModels){
        ArrayList<PreparationStepDomainModel> domainModels = new ArrayList<>();
        for (PreparationStepDataModel data: dataModels){
            domainModels.add(transform(data));
        }
        return domainModels;
    }

    public PreparationStepDataModel transformToData(PreparationStepDomainModel domainModel){
        PreparationStepDataModel dataModel = null;
        if(domainModel != null){
            dataModel = new PreparationStepDataModel(domainModel.getId(),
                    domainModel.getStepNo(),
                    domainModel.getDescription());
        }
        return dataModel;
    }

    public ArrayList<PreparationStepDataModel> transformAllToData(List<PreparationStepDomainModel> domainModels) {
        ArrayList<PreparationStepDataModel> dataModels = new ArrayList<>();
        for (PreparationStepDomainModel data: domainModels){
            dataModels.add(transformToData(data));
        }
        return dataModels;
    }
}
