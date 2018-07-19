package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.AllergenCacheImpl;
import de.karzek.diettracker.data.cache.RecipeCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.AllergenCache;
import de.karzek.diettracker.data.cache.interfaces.RecipeCache;
import de.karzek.diettracker.data.mapper.AllergenDataMapper;
import de.karzek.diettracker.data.mapper.RecipeDataMapper;
import de.karzek.diettracker.data.repository.AllergenRepositoryImpl;
import de.karzek.diettracker.data.repository.RecipeRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.AllergenRepository;
import de.karzek.diettracker.data.repository.repositoryInterface.RecipeRepository;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.interactor.useCase.allergen.GetAllAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.GetAllergenByIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.GetMatchingAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.allergen.PutAllAllergensUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.recipe.GetAllRecipesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetAllergenByIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.GetMatchingAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.allergen.PutAllAllergensUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.recipe.GetAllRecipesUseCase;
import de.karzek.diettracker.domain.mapper.AllergenDomainMapper;
import de.karzek.diettracker.domain.mapper.RecipeDomainMapper;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.mapper.RecipeUIMapper;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
@Module
public class AllergenModule {

    // data

    @Provides
    AllergenCache providesAllergenCache(){
        return new AllergenCacheImpl();
    }

    @Provides
    AllergenDataMapper providesAllergenDataMapper(){
        return new AllergenDataMapper();
    }

    @Provides
    AllergenRepository providesAllergenRepository(AllergenCache cache, AllergenDataMapper mapper){
        return new AllergenRepositoryImpl(cache, mapper);
    }

    // domain

    @Provides
    AllergenDomainMapper providesAllergenDomainMapper(){
        return new AllergenDomainMapper();
    }

    @Provides
    GetAllergenByIdUseCase providesGetAllergenByIdUseCase(AllergenRepository repository, AllergenDomainMapper mapper){
        return new GetAllergenByIdUseCaseImpl(repository, mapper);
    }

    @Provides
    PutAllAllergensUseCase providePutAllAllergensUseCaseImpl(AllergenRepository repository, AllergenDomainMapper mapper){
        return new PutAllAllergensUseCaseImpl(repository, mapper);
    }

    @Provides
    GetAllAllergensUseCase provideGetAllAllergensUseCase(AllergenRepository repository, AllergenDomainMapper mapper){
        return new GetAllAllergensUseCaseImpl(repository, mapper);
    }

    @Provides
    GetMatchingAllergensUseCase provideGetMatchingAllergensUseCase(SharedPreferencesManager sharedPreferencesManager){
        return new GetMatchingAllergensUseCaseImpl(sharedPreferencesManager);
    }

    // presentation

    @Provides
    AllergenUIMapper providesAllergenUIMapper(){
        return new AllergenUIMapper();
    }

}
