package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.RecipeCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.RecipeCache;
import de.karzek.diettracker.data.mapper.RecipeDataMapper;
import de.karzek.diettracker.data.repository.RecipeRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.RecipeRepository;
import de.karzek.diettracker.domain.interactor.useCase.recipe.GetAllRecipesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetAllRecipesUseCase;
import de.karzek.diettracker.domain.mapper.RecipeDomainMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
@Module
public class RecipeModule {

    // data

    @Provides
    RecipeCache providesRecipeCache(){
        return new RecipeCacheImpl();
    }

    @Provides
    RecipeDataMapper providesRecipeDataMapper(){
        return new RecipeDataMapper();
    }

    @Provides
    RecipeRepository providesRecipeRepository(RecipeCache cache, RecipeDataMapper mapper){
        return new RecipeRepositoryImpl(cache, mapper);
    }

    // domain

    @Provides
    RecipeDomainMapper providesRecipeDomainMapper(){
        return new RecipeDomainMapper();
    }

    @Provides
    GetAllRecipesUseCase providesGetAllRecipesUseCase(RecipeRepository repository, RecipeDomainMapper mapper){
        return new GetAllRecipesUseCaseImpl(repository, mapper);
    }

    // presentation

    @Provides
    RecipeUIMapper providesRecipeUIMapper(){
        return new RecipeUIMapper();
    }

}
