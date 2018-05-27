package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.model.AllergenData;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class AllergenUIMapper {
    public AllergenDisplayModel transform(AllergenData allergenData){
        AllergenDisplayModel allergenDisplayModel = null;
        if(allergenData != null){
            allergenDisplayModel = new AllergenDisplayModel(allergenData.getId(),
                    allergenData.getName()
            );
        }
        return allergenDisplayModel;
    }

    public ArrayList<AllergenDisplayModel> transformAll(List<AllergenData> allergenDataList){
        ArrayList<AllergenDisplayModel> allergenDisplayModels = new ArrayList<>();
        for (AllergenData data: allergenDataList){
            allergenDisplayModels.add(transform(data));
        }
        return allergenDisplayModels;
    }
}
