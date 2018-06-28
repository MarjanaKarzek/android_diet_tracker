package de.karzek.diettracker.data.repository.repositoryInterface;

import java.util.List;

import de.karzek.diettracker.data.model.AllergenDataModel;
import de.karzek.diettracker.data.model.ServingDataModel;
import de.karzek.diettracker.domain.model.AllergenDomainModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface AllergenRepository {

    Observable<Boolean> putAllAllergens(List<AllergenDataModel> allergenDataModels);

    Observable<AllergenDataModel> getAllergenById(Integer integer);

    Observable<List<AllergenDataModel>> getAllAllergens();
}
