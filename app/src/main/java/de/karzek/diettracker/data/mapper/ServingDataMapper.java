package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.ServingEntity;
import de.karzek.diettracker.data.model.ServingDataModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class ServingDataMapper {
    public ServingDataModel transform(ServingEntity servingEntity){
        ServingDataModel servingDataModel = null;
        if(servingEntity != null){
            servingDataModel = new ServingDataModel(servingEntity.getId(),
                    servingEntity.getDescription(),
                    servingEntity.getAmount(),
                    servingEntity.getUnit()
            );
        }
        return servingDataModel;
    }

    public ArrayList<ServingDataModel> transformAll(List<ServingEntity> servingEntities){
        ArrayList<ServingDataModel> servingDataModelList = new ArrayList<>();
        for (ServingEntity entity: servingEntities){
            servingDataModelList.add(transform(entity));
        }
        return servingDataModelList;
    }
}
