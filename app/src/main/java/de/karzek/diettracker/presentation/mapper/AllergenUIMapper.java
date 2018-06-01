package de.karzek.diettracker.presentation.mapper;

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
public class AllergenUIMapper {
    public AllergenDisplayModel transform(AllergenDomainModel allergenDomainModel){
        AllergenDisplayModel allergenDisplayModel = null;
        if(allergenDomainModel != null){
            allergenDisplayModel = new AllergenDisplayModel(allergenDomainModel.getId(),
                    allergenDomainModel.getName()
            );
        }
        return allergenDisplayModel;
    }

    public ArrayList<AllergenDisplayModel> transformAll(List<AllergenDomainModel> allergenDomainModels){
        ArrayList<AllergenDisplayModel> allergenDisplayModels = new ArrayList<>();
        for (AllergenDomainModel data: allergenDomainModels){
            allergenDisplayModels.add(transform(data));
        }
        return allergenDisplayModels;
    }

    public AllergenDomainModel transformToDomain(AllergenDisplayModel allergenDisplayModel){
        AllergenDomainModel allergenDomainModel1 = null;
        if(allergenDisplayModel != null){
            allergenDomainModel1 = new AllergenDomainModel(allergenDisplayModel.getId(),
                    allergenDisplayModel.getName()
            );
        }
        return allergenDomainModel1;
    }

    public ArrayList<AllergenDomainModel> transformAllToDomain(ArrayList<AllergenDisplayModel> allergenDisplayModels) {
        ArrayList<AllergenDomainModel> allergenDomainModels = new ArrayList<>();
        for (AllergenDisplayModel data: allergenDisplayModels){
            allergenDomainModels.add(transformToDomain(data));
        }
        return allergenDomainModels;
    }
}
