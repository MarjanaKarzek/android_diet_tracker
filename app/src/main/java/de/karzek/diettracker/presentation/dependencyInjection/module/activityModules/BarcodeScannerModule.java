package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByBarcodeUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.meal.GetAllMealsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByBarcodeUseCase;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.presentation.main.MainContract;
import de.karzek.diettracker.presentation.main.MainPresenter;
import de.karzek.diettracker.presentation.mapper.MealUIMapper;
import de.karzek.diettracker.presentation.search.grocery.barcodeScanner.BarcodeScannerContract;
import de.karzek.diettracker.presentation.search.grocery.barcodeScanner.BarcodeScannerPresenter;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class BarcodeScannerModule {

    //domain

    @Provides
    GetGroceryByBarcodeUseCase provideGetGroceryByBarcodeUseCaseImpl(GroceryRepository repository, GroceryDomainMapper mapper) {
        return new GetGroceryByBarcodeUseCaseImpl(repository, mapper);
    }

    //presentation

    @Provides
    BarcodeScannerContract.Presenter provideBarcodeScannerPresenter(Lazy<GetGroceryByBarcodeUseCase> getGroceryByBarcodeUseCase) {
        return new BarcodeScannerPresenter(getGroceryByBarcodeUseCase);
    }
}
