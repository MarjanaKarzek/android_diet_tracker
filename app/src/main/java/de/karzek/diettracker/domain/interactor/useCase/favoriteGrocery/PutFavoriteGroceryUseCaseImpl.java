package de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery;

import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.UnitRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteGroceryRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.PutFavoriteGroceryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.PutAllUnitsUseCase;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import de.karzek.diettracker.domain.model.FavoriteGroceryDomainModel;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class PutFavoriteGroceryUseCaseImpl implements PutFavoriteGroceryUseCase {

    private final FavoriteGroceryRepository repository;
    private final FavoriteGroceryDomainMapper mapper;

    public PutFavoriteGroceryUseCaseImpl(FavoriteGroceryRepository repository, FavoriteGroceryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.putFavoriteGrocery(mapper.transformToData(input.getFavoriteGrocery())).map(output -> {
            if (output) {
                return new Output(Output.SUCCESS);
            } else {
                return new Output(Output.ERROR_UNKNOWN_PROBLEM);
            }
        }).onErrorReturn(throwable -> new Output(Output.ERROR_UNKNOWN_PROBLEM));
    }

}