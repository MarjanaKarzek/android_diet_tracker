package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.AllergenDataModel;
import de.karzek.diettracker.domain.model.AllergenDomainModel;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class AllergenDomainMapper {
    public AllergenDomainModel transform(AllergenDataModel allergenDataModel){
        AllergenDomainModel allergenDomainModel = null;
        if(allergenDataModel != null){
            allergenDomainModel = new AllergenDomainModel(allergenDataModel.getId(),
                    allergenDataModel.getName()
            );
        }
        return allergenDomainModel;
    }

    public ArrayList<AllergenDomainModel> transformAll(List<AllergenDataModel> allergenDataModelList){
        ArrayList<AllergenDomainModel> allergenDomainModels = new ArrayList<>();
        for (AllergenDataModel data: allergenDataModelList){
            allergenDomainModels.add(transform(data));
        }
        return allergenDomainModels;
    }

    private AllergenDataModel transformToData(AllergenDomainModel allergenDomainModel) {
        AllergenDataModel allergenDataModel = null;
        if(allergenDomainModel != null){
            allergenDataModel = new AllergenDataModel(allergenDomainModel.getId(),
                    allergenDomainModel.getName()
            );
        }
        return allergenDataModel;
    }

    public ArrayList<AllergenDataModel> transformAllToData(List<AllergenDomainModel> allergenDomainModels) {
        ArrayList<AllergenDataModel> allergenDataModels = new ArrayList<>();
        for (AllergenDomainModel data: allergenDomainModels){
            allergenDataModels.add(transformToData(data));
        }
        return allergenDataModels;
    }
}
