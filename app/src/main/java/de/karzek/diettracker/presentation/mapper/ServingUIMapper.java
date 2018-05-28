package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.ServingDataModel;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class ServingUIMapper {
    public ServingDisplayModel transform(ServingDataModel servingDataModel){
        ServingDisplayModel servingDisplayModel = null;
        if(servingDataModel != null){
            servingDisplayModel = new ServingDisplayModel(servingDataModel.getId(),
                    servingDataModel.getDescription(),
                    servingDataModel.getAmount(),
                    servingDataModel.getUnit()
            );
        }
        return servingDisplayModel;
    }

    public ArrayList<ServingDisplayModel> transformAll(List<ServingDataModel> servingDataModelList){
        ArrayList<ServingDisplayModel> servingDisplayModels = new ArrayList<>();
        for (ServingDataModel data: servingDataModelList){
            servingDisplayModels.add(transform(data));
        }
        return servingDisplayModels;
    }
}
