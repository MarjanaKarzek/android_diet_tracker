package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.database.model.AllergenEntity;
import de.karzek.diettracker.domain.model.AllergenData;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class AllergenMapper {

    public AllergenData transform(AllergenEntity allergenEntity){
        AllergenData allergenData = null;
        if(allergenEntity != null){
            allergenData = new AllergenData(allergenEntity.getId(),
                    allergenEntity.getName()
            );
        }
        return allergenData;
    }

    public ArrayList<AllergenData> transformAll(List<AllergenEntity> allergenEntities){
        ArrayList<AllergenData> allergenDataList = new ArrayList<>();
        for (AllergenEntity entity: allergenEntities){
            allergenDataList.add(transform(entity));
        }
        return  allergenDataList;
    }
}
