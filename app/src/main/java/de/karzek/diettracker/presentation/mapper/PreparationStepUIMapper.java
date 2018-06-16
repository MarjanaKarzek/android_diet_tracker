package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.model.IngredientDomainModel;
import de.karzek.diettracker.domain.model.PreparationStepDomainModel;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.PreparationStepDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class PreparationStepUIMapper {
    public PreparationStepDisplayModel transform(PreparationStepDomainModel domainModel){
        PreparationStepDisplayModel displayModel = null;
        if(domainModel != null){
            displayModel = new PreparationStepDisplayModel(domainModel.getId(),
                    domainModel.getStepNo(),
                    domainModel.getDescription()
            );
        }
        return displayModel;
    }

    public ArrayList<PreparationStepDisplayModel> transformAll(List<PreparationStepDomainModel> domainModels){
        ArrayList<PreparationStepDisplayModel> displayModels = new ArrayList<>();
        for (PreparationStepDomainModel data: domainModels){
            displayModels.add(transform(data));
        }
        return displayModels;
    }

    public PreparationStepDomainModel transformToDomain(PreparationStepDisplayModel displayModel){
        PreparationStepDomainModel domainModel = null;
        if(displayModel != null){
            domainModel = new PreparationStepDomainModel(displayModel.getId(),
                    displayModel.getStepNo(),
                    displayModel.getDescription()
            );
        }
        return domainModel;
    }

    public ArrayList<PreparationStepDomainModel> transformAllToDomain(ArrayList<PreparationStepDisplayModel> displayModels) {
        ArrayList<PreparationStepDomainModel> domainModels = new ArrayList<>();
        for (PreparationStepDisplayModel data: displayModels){
            domainModels.add(transformToDomain(data));
        }
        return domainModels;
    }
}
