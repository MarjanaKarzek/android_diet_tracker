package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.RecipeCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.RecipeCache;
import de.karzek.diettracker.data.mapper.RecipeDataMapper;
import de.karzek.diettracker.data.repository.RecipeRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteRecipeRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.RecipeRepository;
import de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe.GetAllFavoriteRecipesForMealUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.DeleteRecipeByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.GetAllRecipesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.GetMatchingRecipesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.GetRecipeByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.PutRecipeUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.UpdateRecipeUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetAllFavoriteRecipesForMealUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.DeleteRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetAllRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetMatchingRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.PutRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.UpdateRecipeUseCase;
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

    @Provides
    GetRecipeByIdUseCase providesGetRecipeByIdUseCase(RecipeRepository repository, RecipeDomainMapper mapper){
        return new GetRecipeByIdUseCaseImpl(repository, mapper);
    }

    @Provides
    PutRecipeUseCase providesPutRecipeUseCase(RecipeRepository repository, RecipeDomainMapper mapper){
        return new PutRecipeUseCaseImpl(repository, mapper);
    }

    @Provides
    UpdateRecipeUseCase providesUpdateRecipeUseCase(RecipeRepository repository, RecipeDomainMapper mapper){
        return new UpdateRecipeUseCaseImpl(repository, mapper);
    }

    @Provides
    DeleteRecipeByIdUseCase providesDeleteRecipeByIdUseCase(RecipeRepository repository){
        return new DeleteRecipeByIdUseCaseImpl(repository);
    }

    @Provides
    GetMatchingRecipesUseCase providesGetMatchingRecipesUseCase(RecipeRepository repository, RecipeDomainMapper mapper){
        return new GetMatchingRecipesUseCaseImpl(repository, mapper);
    }

    @Provides
    GetAllFavoriteRecipesForMealUseCase providesGetAllFavoriteRecipesForMealUseCase(FavoriteRecipeRepository repository, RecipeDomainMapper mapper){
        return new GetAllFavoriteRecipesForMealUseCaseImpl(repository, mapper);
    }

    // presentation

    @Provides
    RecipeUIMapper providesRecipeUIMapper(){
        return new RecipeUIMapper();
    }

}
