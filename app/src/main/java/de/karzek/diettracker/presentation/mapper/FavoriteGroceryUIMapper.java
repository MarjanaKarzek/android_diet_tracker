package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.AllergenDataModel;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class AllergenUIMapper {
    public AllergenDisplayModel transform(AllergenDataModel allergenDataModel){
        AllergenDisplayModel allergenDisplayModel = null;
        if(allergenDataModel != null){
            allergenDisplayModel = new AllergenDisplayModel(allergenDataModel.getId(),
                    allergenDataModel.getName()
            );
        }
        return allergenDisplayModel;
    }

    public ArrayList<AllergenDisplayModel> transformAll(List<AllergenDataModel> allergenDataModelList){
        ArrayList<AllergenDisplayModel> allergenDisplayModels = new ArrayList<>();
        for (AllergenDataModel data: allergenDataModelList){
            allergenDisplayModels.add(transform(data));
        }
        return allergenDisplayModels;
    }
}
