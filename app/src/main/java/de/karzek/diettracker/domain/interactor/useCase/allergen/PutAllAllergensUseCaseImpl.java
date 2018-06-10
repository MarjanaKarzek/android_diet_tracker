package de.karzek.diettracker.domain.interactor.useCase.allergen;

import de.karzek.diettracker.data.repository.AllergenRepositoryImpl;
import de.karzek.diettracker.data.repository.ServingRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.PutAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.serving.PutAllServingsUseCase;
import de.karzek.diettracker.domain.mapper.AllergenDomainMapper;
import de.karzek.diettracker.domain.mapper.ServingDomainMapper;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class PutAllAllergensUseCaseImpl implements PutAllAllergensUseCase {

    private final AllergenRepositoryImpl repository;
    private final AllergenDomainMapper mapper;

    public PutAllAllergensUseCaseImpl(AllergenRepositoryImpl repository, AllergenDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.putAllAllergens(mapper.transformAllToData(input.getAllergenDomainModels())).map(output -> {
            if (output) {
                return new Output(Output.SUCCESS);
            } else {
                return new Output(Output.ERROR_UNKNOWN_PROBLEM);
            }
        }).onErrorReturn(throwable -> new Output(Output.ERROR_UNKNOWN_PROBLEM));
    }

}