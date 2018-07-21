package de.karzek.diettracker.presentation.dependencyInjection.module.featureModule;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.FavoriteGroceryCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.mapper.FavoriteGroceryDataMapper;
import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteGroceryRepository;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.GetFavoriteGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.GetFavoriteStateForGroceryIdUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.PutFavoriteGroceryUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery.RemoveFavoriteGroceryByNameUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteGroceriesUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteStateForGroceryIdUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.PutFavoriteGroceryUseCase;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.RemoveFavoriteGroceryByNameUseCase;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import de.karzek.diettracker.presentation.mapper.FavoriteGroceryUIMapper;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
@Module
public class FavoriteGroceryModule {

    // data

    @Provides
    FavoriteGroceryCache provideFavoriteGroceryCacheImpl() {
        return new FavoriteGroceryCacheImpl();
    }

    @Provides
    FavoriteGroceryDataMapper provideFavoriteGroceryDataMapper() {
        return new FavoriteGroceryDataMapper();
    }

    @Provides
    FavoriteGroceryRepository provideFavoriteGroceryRepositoryImpl(FavoriteGroceryCache cache, FavoriteGroceryDataMapper mapper) {
        return new FavoriteGroceryRepositoryImpl(cache, mapper);
    }

    // domain

    @Provides
    FavoriteGroceryDomainMapper provideFavoriteGroceryDomainMapper() {
        return new FavoriteGroceryDomainMapper();
    }

    @Provides
    GetFavoriteGroceriesUseCase provideGetFavoriteFoodsUseCase(FavoriteGroceryRepository repository, FavoriteGroceryDomainMapper mapper) {
        return new GetFavoriteGroceriesUseCaseImpl(repository, mapper);
    }

    @Provides
    PutFavoriteGroceryUseCase providePutFavoriteGroceryUseCaseImpl(FavoriteGroceryRepository repository, FavoriteGroceryDomainMapper mapper) {
        return new PutFavoriteGroceryUseCaseImpl(repository, mapper);
    }

    @Provides
    RemoveFavoriteGroceryByNameUseCase provideRemoveFavoriteGroceryByNameUseCaseImpl(FavoriteGroceryRepository repository) {
        return new RemoveFavoriteGroceryByNameUseCaseImpl(repository);
    }

    @Provides
    GetFavoriteStateForGroceryIdUseCase provideGetFavoriteStateForGroceryIdUseCaseImpl(FavoriteGroceryRepository repository) {
        return new GetFavoriteStateForGroceryIdUseCaseImpl(repository);
    }

    // presentation

    @Provides
    FavoriteGroceryUIMapper provideFavoriteGroceryUIMapper() {
        return new FavoriteGroceryUIMapper();
    }


}
