package de.karzek.diettracker.domain.interactor.useCase.allergen;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.model.AllergenDataModel;
import de.karzek.diettracker.data.repository.repositoryInterface.AllergenRepository;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetMatchingAllergensUseCase;
import de.karzek.diettracker.domain.mapper.AllergenDomainMapper;
import de.karzek.diettracker.domain.model.AllergenDomainModel;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetMatchingAllergensUseCaseImpl implements GetMatchingAllergensUseCase {

    private SharedPreferencesManager sharedPreferencesManager;

    public GetMatchingAllergensUseCaseImpl(SharedPreferencesManager sharedPreferencesManager) {
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return Observable.just(sharedPreferencesManager.getAllergenIds()).map(new Function<List<Integer>, Output>() {
            @Override
            public Output apply(List<Integer> allergenIds) {
                ArrayList<AllergenDomainModel> allergens = new ArrayList<>();
                for(AllergenDomainModel allergen: input.getGroceryAllergens()){
                    if(allergenIds.contains(allergen.getId()))
                        allergens.add(allergen);
                }

                return new Output(allergens, Output.SUCCESS);
            }
        });
    }

}