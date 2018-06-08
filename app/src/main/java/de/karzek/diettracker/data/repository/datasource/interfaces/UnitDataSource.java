package de.karzek.diettracker.data.repository.datasource.interfaces;

import java.util.List;

import de.karzek.diettracker.data.cache.model.GroceryEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import de.karzek.diettracker.data.model.UnitDataModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface UnitDataSource {

    Observable<Boolean> putAllUnits(List<UnitEntity> unitEntities);

    Observable<List<UnitEntity>> getAllDefaultUnits(int type);

    Observable<UnitEntity> getUnitByName(String name);
}
