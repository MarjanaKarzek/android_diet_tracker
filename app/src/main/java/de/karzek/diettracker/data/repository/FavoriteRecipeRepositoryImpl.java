package de.karzek.diettracker.data.repository;

import java.util.List;

import de.karzek.diettracker.data.cache.interfaces.FavoriteGroceryCache;
import de.karzek.diettracker.data.cache.interfaces.FavoriteRecipeCache;
import de.karzek.diettracker.data.cache.model.FavoriteGroceryEntity;
import de.karzek.diettracker.data.cache.model.FavoriteRecipeEntity;
import de.karzek.diettracker.data.mapper.FavoriteGroceryDataMapper;
import de.karzek.diettracker.data.mapper.FavoriteRecipeDataMapper;
import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import de.karzek.diettracker.data.model.FavoriteRecipeDataModel;
import de.karzek.diettracker.data.repository.datasource.local.FavoriteGroceryLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.datasource.local.FavoriteRecipeLocalDataSourceImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteRecipeRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class FavoriteRecipeRepositoryImpl implements FavoriteRecipeRepository {

    private final FavoriteRecipeDataMapper mapper;
    private final FavoriteRecipeCache cache;

    public FavoriteRecipeRepositoryImpl(FavoriteRecipeCache cache, FavoriteRecipeDataMapper mapper) {
        this.cache = cache;
        this.mapper = mapper;
    }

    @Override
    public Observable<List<FavoriteRecipeDataModel>> getAllFavoriteRecipes() {
        return new FavoriteRecipeLocalDataSourceImpl(cache).getAllFavorites().map(new Function<List<FavoriteRecipeEntity>, List<FavoriteRecipeDataModel>>() {
            @Override
            public List<FavoriteRecipeDataModel> apply(List<FavoriteRecipeEntity> favoriteEntities) {
                return mapper.transformAll(favoriteEntities);
            }
        });
    }

    @Override
    public Observable<Boolean> putFavoriteRecipe(FavoriteRecipeDataModel dataModel) {
        return new FavoriteRecipeLocalDataSourceImpl(cache).putFavoriteRecipe(mapper.transformToEntity(dataModel));
    }

    @Override
    public Observable<Boolean> removeFavoriteRecipeByTitle(String title) {
        return new FavoriteRecipeLocalDataSourceImpl(cache).removeFavoriteRecipeByTitle(title);
    }

    @Override
    public Observable<Boolean> getFavoriteStateForRecipeById(int id) {
        return new FavoriteRecipeLocalDataSourceImpl(cache).getFavoriteStateForRecipeById(id);
    }

    @Override
    public Observable<List<FavoriteRecipeDataModel>> getAllFavoriteRecipesForMeal(String meal) {
        return new FavoriteRecipeLocalDataSourceImpl(cache).getAllFavoriteRecipesForMeal(meal).map(new Function<List<FavoriteRecipeEntity>, List<FavoriteRecipeDataModel>>() {
            @Override
            public List<FavoriteRecipeDataModel> apply(List<FavoriteRecipeEntity> favoriteEntities) {
                return mapper.transformAll(favoriteEntities);
            }
        });
    }
}