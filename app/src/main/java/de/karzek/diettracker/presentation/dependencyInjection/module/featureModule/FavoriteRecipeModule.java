package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.AllergenCacheImpl;
import de.karzek.diettracker.data.cache.FavoriteRecipeCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.AllergenCache;
import de.karzek.diettracker.data.cache.interfaces.FavoriteRecipeCache;
import de.karzek.diettracker.data.mapper.AllergenDataMapper;
import de.karzek.diettracker.data.mapper.FavoriteRecipeDataMapper;
import de.karzek.diettracker.data.repository.AllergenRepositoryImpl;
import de.karzek.diettracker.data.repository.FavoriteRecipeRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.AllergenRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteRecipeRepository;
import de.karzek.diettracker.domain.interactor.useCase.allergen.GetAllAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.GetAllergenByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.PutAllAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe.GetFavoriteRecipesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe.GetFavoriteStateForRecipeByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe.PutFavoriteRecipeUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteRecipe.RemoveFavoriteRecipeByTitleUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllergenByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.PutAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetFavoriteRecipesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.GetFavoriteStateForRecipeByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.PutFavoriteRecipeUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteRecipe.RemoveFavoriteRecipeByTitleUseCase;
import de.karzek.diettracker.domain.mapper.AllergenDomainMapper;
import de.karzek.diettracker.domain.mapper.FavoriteRecipeDomainMapper;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.FavoriteRecipeUIMapper;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
@Module
public class FavoriteRecipeModule {

    // data

    @Provides
    FavoriteRecipeCache providesFavoriteRecipeCache(){
        return new FavoriteRecipeCacheImpl();
    }

    @Provides
    FavoriteRecipeDataMapper providesFavoriteRecipeDataMapper(){
        return new FavoriteRecipeDataMapper();
    }

    @Provides
    FavoriteRecipeRepository providesFavoriteRecipeRepository(FavoriteRecipeCache cache, FavoriteRecipeDataMapper mapper){
        return new FavoriteRecipeRepositoryImpl(cache, mapper);
    }

    // domain

    @Provides
    FavoriteRecipeDomainMapper providesFavoriteRecipeDomainMapper(){
        return new FavoriteRecipeDomainMapper();
    }

    @Provides
    GetFavoriteRecipesUseCase providesGetFavoriteRecipesUseCase(FavoriteRecipeRepository repository, FavoriteRecipeDomainMapper mapper){
        return new GetFavoriteRecipesUseCaseImpl(repository, mapper);
    }

    @Provides
    GetFavoriteStateForRecipeByIdUseCase providesGetFavoriteStateForRecipeByIdUseCase(FavoriteRecipeRepository repository){
        return new GetFavoriteStateForRecipeByIdUseCaseImpl(repository);
    }

    @Provides
    PutFavoriteRecipeUseCase providesPutFavoriteRecipeUseCase(FavoriteRecipeRepository repository, FavoriteRecipeDomainMapper mapper){
        return new PutFavoriteRecipeUseCaseImpl(repository, mapper);
    }

    @Provides
    RemoveFavoriteRecipeByTitleUseCase providesRemoveFavoriteRecipeByTitleUseCase(FavoriteRecipeRepository repository){
        return new RemoveFavoriteRecipeByTitleUseCaseImpl(repository);
    }

    // presentation

    @Provides
    FavoriteRecipeUIMapper providesFavoriteRecipeUIMapper(){
        return new FavoriteRecipeUIMapper();
    }

}
