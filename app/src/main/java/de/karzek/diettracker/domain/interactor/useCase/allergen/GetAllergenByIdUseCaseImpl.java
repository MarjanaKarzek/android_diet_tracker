package de.karzek.diettracker.domain.interactor.useCase.allergen;

import de.karzek.diettracker.data.model.AllergenDataModel;
import de.karzek.diettracker.data.model.UnitDataModel;
import de.karzek.diettracker.data.repository.repositoryInterface.AllergenRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllergenByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByIdUseCase;
import de.karzek.diettracker.domain.mapper.AllergenDomainMapper;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetAllergenByIdUseCaseImpl implements GetAllergenByIdUseCase {

    private final AllergenRepository repository;
    private final AllergenDomainMapper mapper;

    public GetAllergenByIdUseCaseImpl(AllergenRepository repository, AllergenDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllergenById(input.getId()).map(new Function<AllergenDataModel, Output>() {
            @Override
            public Output apply(AllergenDataModel dataModel) {
                return new Output(mapper.transform(dataModel), Output.SUCCESS);
            }
        });
    }

}