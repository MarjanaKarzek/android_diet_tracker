package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.model.ServingData;
import de.karzek.diettracker.presentation.model.ServingDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class ServingUIMapper {
    public ServingDisplayModel transform(ServingData servingData){
        ServingDisplayModel servingDisplayModel = null;
        if(servingData != null){
            servingDisplayModel = new ServingDisplayModel(servingData.getId(),
                    servingData.getDescription(),
                    servingData.getAmount(),
                    servingData.getUnit()
            );
        }
        return servingDisplayModel;
    }

    public ArrayList<ServingDisplayModel> transformAll(List<ServingData> servingDataList){
        ArrayList<ServingDisplayModel> servingDisplayModels = new ArrayList<>();
        for (ServingData data: servingDataList){
            servingDisplayModels.add(transform(data));
        }
        return servingDisplayModels;
    }
}
