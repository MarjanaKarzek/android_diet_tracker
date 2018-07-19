package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.repository.repositoryInterface.UnitRepository;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.unit.GetUnitByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.DeleteRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.PutRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.UpdateRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetAllDefaultUnitsUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.unit.GetUnitByIdUseCase;
import de.karzek.diettracker.domain.mapper.UnitDomainMapper;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationContract;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationPresenter;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;
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
    RecipeManipulationContract.Presenter provideRecipeManipulationPresenter(SharedPreferencesManager sharedPreferencesManager,
                                                                            Lazy<GetAllDefaultUnitsUseCase> getAllDefaultUnitsUseCase,
                                                                            Lazy<GetGroceryByIdUseCase> getGroceryByIdUseCase,
                                                                            Lazy<GetUnitByIdUseCase> getUnitByIdUseCase,
                                                                            Lazy<PutRecipeUseCase> putRecipeUseCase,
                                                                            Lazy<UpdateRecipeUseCase> updateRecipeUseCase,
                                                                            Lazy<GetRecipeByIdUseCase> getRecipeByIdUseCase,
                                                                            Lazy<DeleteRecipeByIdUseCase> deleteRecipeByIdUseCase,
                                                                            UnitUIMapper unitMapper,
                                                                            GroceryUIMapper groceryMapper,
                                                                            RecipeUIMapper recipeMapper) {
        return new RecipeManipulationPresenter(sharedPreferencesManager,
                getAllDefaultUnitsUseCase,
                getGroceryByIdUseCase,
                getUnitByIdUseCase,
                putRecipeUseCase,
                updateRecipeUseCase,
                getRecipeByIdUseCase,
                deleteRecipeByIdUseCase,
                unitMapper,
                groceryMapper,
                recipeMapper);
    }
}
