package de.karzek.diettracker.domain.interactor.useCase.grocery;

import de.karzek.diettracker.data.model.GroceryDataModel;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByBarcodeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetGroceryByBarcodeUseCaseImpl implements GetGroceryByBarcodeUseCase {

    private final GroceryRepositoryImpl repository;
    private final GroceryDomainMapper mapper;

    public GetGroceryByBarcodeUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getGroceryByBarcode(input.getBarcode()).map(new Function<GroceryDataModel, Output>() {
            @Override
            public Output apply(GroceryDataModel grocery){
                if ( mapper.transform(grocery).getId() == -1)
                    return new Output(Output.ERROR_NO_DATA, null);
                else
                    return new Output(Output.SUCCESS, mapper.transform(grocery));
            }
        });
    }

}