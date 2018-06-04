package de.karzek.diettracker.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;
import de.karzek.diettracker.domain.model.GroceryDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.UnitDisplayModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class DiaryEntryUIMapper {
    public DiaryEntryDisplayModel transform(DiaryEntryDomainModel domainModel){
        DiaryEntryDisplayModel displayModel = null;
        if(domainModel != null){
            displayModel = new DiaryEntryDisplayModel(domainModel.getId(),
                    new MealUIMapper().transform(domainModel.getMeal()),
                    domainModel.getAmount(),
                    new UnitUIMapper().transform(domainModel.getUnit()),
                    new GroceryUIMapper().transform(domainModel.getGrocery()),
                    domainModel.getDate()
            );
        }
        return displayModel;
    }

    public ArrayList<DiaryEntryDisplayModel> transformAll(List<DiaryEntryDomainModel> domainModels){
        ArrayList<DiaryEntryDisplayModel> displayModels = new ArrayList<>();
        for (DiaryEntryDomainModel data: domainModels){
            displayModels.add(transform(data));
        }
        return displayModels;
    }

    public DiaryEntryDomainModel transformToDomain(DiaryEntryDisplayModel displayModel){
        DiaryEntryDomainModel domainModel = null;
        if(displayModel != null){
            domainModel = new DiaryEntryDomainModel(displayModel.getId(),
                    new MealUIMapper().transformToDomain(displayModel.getMeal()),
                    displayModel.getAmount(),
                    new UnitUIMapper().transformToDomain(displayModel.getUnit()),
                    new GroceryUIMapper().transformToDomain(displayModel.getGrocery()),
                    displayModel.getDate()
            );
        }
        return domainModel;
    }

    public ArrayList<DiaryEntryDomainModel> transformAllToDomain(ArrayList<DiaryEntryDisplayModel> displayModels) {
        ArrayList<DiaryEntryDomainModel> domainModels = new ArrayList<>();
        for (DiaryEntryDisplayModel data: displayModels){
            domainModels.add(transformToDomain(data));
        }
        return domainModels;
    }
}
