package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.database.model.ServingEntity;
import de.karzek.diettracker.domain.model.ServingData;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class ServingMapper {
    public ServingData transform(ServingEntity servingEntity){
        ServingData servingData = null;
        if(servingEntity != null){
            servingData = new ServingData(servingEntity.getId(),
                    servingEntity.getDescription(),
                    servingEntity.getAmount(),
                    servingEntity.getUnit()
            );
        }
        return servingData;
    }

    public ArrayList<ServingData> transformAll(List<ServingEntity> servingEntities){
        ArrayList<ServingData> servingDataList = new ArrayList<>();
        for (ServingEntity entity: servingEntities){
            servingDataList.add(transform(entity));
        }
        return servingDataList;
    }
}
