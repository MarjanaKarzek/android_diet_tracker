package de.karzek.diettracker.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.DiaryEntryDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.domain.model.DiaryEntryDomainModel;
import de.karzek.diettracker.domain.model.UnitDomainModel;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class DiaryEntryDomainMapper {
    public DiaryEntryDomainModel transform(DiaryEntryDataModel dataModel){
        DiaryEntryDomainModel domainModel = null;
        if(dataModel != null){
            domainModel = new DiaryEntryDomainModel(dataModel.getId(),
                    new MealDomainMapper().transform(dataModel.getMeal()),
                    dataModel.getAmount(),
                    new UnitDomainMapper().transform(dataModel.getUnit()),
                    new GroceryDomainMapper().transform(dataModel.getGrocery()),
                    dataModel.getDate()
            );
        }
        return domainModel;
    }

    public ArrayList<DiaryEntryDomainModel> transformAll(List<DiaryEntryDataModel> dataModels){
        ArrayList<DiaryEntryDomainModel> domainModels = new ArrayList<>();
        for (DiaryEntryDataModel data: dataModels){
            domainModels.add(transform(data));
        }
        return domainModels;
    }

    public DiaryEntryDataModel transformToData(DiaryEntryDomainModel domainModel){
        DiaryEntryDataModel dataModel = null;
        if(domainModel != null){
            dataModel = new DiaryEntryDataModel(domainModel.getId(),
                    new MealDomainMapper().transformToData(domainModel.getMeal()),
                    domainModel.getAmount(),
                    new UnitDomainMapper().transformToData(domainModel.getUnit()),
                    new GroceryDomainMapper().transformToData(domainModel.getGrocery()),
                    domainModel.getDate()
            );
        }
        return dataModel;
    }

    public ArrayList<DiaryEntryDataModel> transformAllToData(List<DiaryEntryDomainModel> domainModels) {
        ArrayList<DiaryEntryDataModel> dataModels = new ArrayList<>();
        for (DiaryEntryDomainModel data: domainModels){
            dataModels.add(transformToData(data));
        }
        return dataModels;
    }
}
