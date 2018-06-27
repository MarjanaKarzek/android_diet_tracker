package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetUnitByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByIdUseCase;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationContract;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationPresenter;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.UnitUIMapper;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
@Module
public class RecipeManipulationModule {

    @Provides
    GetUnitByIdUseCase provideGetUnitByIdUseCase(UnitRepository repository, UnitDomainMapper mapper){
        return new GetUnitByIdUseCaseImpl(repository, mapper);
    }

    //presentation

    @Provides
    RecipeManipulationContract.Presenter provideRecipeManipulationPresenter(Lazy<GetAllDefaultUnitsUseCase> getAllDefaultUnitsUseCase,
                                                                            Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase,
                                                                            Lazy<GetUnitByIdUseCase> getUnitByIdUseCase,
                                                                            UnitUIMapper unitMapper,
                                                                            GroceryUIMapper groceryMapper) {
        return new RecipeManipulationPresenter(getAllDefaultUnitsUseCase,
                getGroceryByIdUseCase,
                getUnitByIdUseCase,
                unitMapper,
                groceryMapper);
    }
}
