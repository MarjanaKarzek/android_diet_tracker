package de.karzek.diettracker.data.repository.datasource.interfaces;

import java.util.List;

import de.karzek.diettracker.data.cache.model.AllergenEntity;
import de.karzek.diettracker.data.cache.model.ServingEntity;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface AllergenDataSource {

    Observable<Boolean> putAllAllergens(List<AllergenEntity> allergenEntities);

    Observable<AllergenEntity> getAllergenById(Integer id);

    Observable<List<AllergenEntity>> getAllAllergens();
}
