package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.GroceryCacheImpl;
import de.karzek.diettracker.data.cache.RecipeCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.GroceryCache;
import de.karzek.diettracker.data.cache.interfaces.RecipeCache;
import de.karzek.diettracker.data.mapper.GroceryDataMapper;
import de.karzek.diettracker.data.mapper.RecipeDataMapper;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.RecipeRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteRecipeRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.GroceryRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.RecipeRepository;
import de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe.GetAllFavoriteRecipesForMealUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetExactlyMatchingGroceryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByBarcodeUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetGroceryByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.GetMatchingGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.grocery.PutAllGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.DeleteRecipeByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.GetAllRecipesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.GetMatchingRecipesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.GetRecipeByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.GetRecipesMatchingQueryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.PutRecipeUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.UpdateRecipeUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetAllFavoriteRecipesForMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetExactlyMatchingGroceryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByBarcodeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetGroceryByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.GetMatchingGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.grocery.PutAllGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.DeleteRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetAllRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetMatchingRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipesMatchingQueryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.PutRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.UpdateRecipeUseCase;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.RecipeDomainMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
@Module
public class GroceryModule {

    // data

    @Provides
    GroceryDataMapper provideGroceryDataMapper(){
        return new GroceryDataMapper();
    }

    @Provides
    GroceryCache provideGroceryCacheImpl(){
        return new GroceryCacheImpl();
    }

    @Provides
    GroceryRepository provideGroceryRepositoryImpl(GroceryCache groceryCache, GroceryDataMapper mapper){
        return new GroceryRepositoryImpl(groceryCache, mapper);
    }

    // domain

    @Provides
    GroceryDomainMapper provideGroceryDomainMapper(){
        return new GroceryDomainMapper();
    }

    @Provides
    PutAllGroceriesUseCase providePutAllGroceriesUseCase(GroceryRepository repository, GroceryDomainMapper mapper){
        return new PutAllGroceriesUseCaseImpl(repository, mapper);
    }

    @Provides
    GetExactlyMatchingGroceryUseCase provideGetExactlyMatchingGroceryUseCase(GroceryRepository repository, GroceryDomainMapper mapper){
        return new GetExactlyMatchingGroceryUseCaseImpl(repository, mapper);
    }

    @Provides
    GetGroceryByBarcodeUseCase provideGetGroceryByBarcodeUseCase(GroceryRepository repository, GroceryDomainMapper mapper) {
        return new GetGroceryByBarcodeUseCaseImpl(repository, mapper);
    }

    @Provides
    GetGroceryByIdUseCase provideGetGroceryByIdUseCase(GroceryRepository repository, GroceryDomainMapper mapper){
        return new GetGroceryByIdUseCaseImpl(repository, mapper);
    }

    @Provides
    GetMatchingGroceriesUseCase provideGetMatchingGroceriesUseCase(GroceryRepository repository, GroceryDomainMapper mapper){
        return new GetMatchingGroceriesUseCaseImpl(repository, mapper);
    }

    // presentation

    @Provides
    GroceryUIMapper provideGroceryUIMapper(){
        return new GroceryUIMapper();
    }

}
