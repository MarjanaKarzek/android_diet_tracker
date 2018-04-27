package de.karzek.diettracker.data.provider;

import de.karzek.diettracker.data.cache.interfaces.RandomQuoteCache;
import de.karzek.diettracker.data.repository.datasource.RandomQuoteRemoteDataSourceImpl;
import de.karzek.diettracker.data.repository.datasource.interfaces.RandomQuoteDataSource;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class RandomQuoteProvider {

    private final RandomQuoteCache randomQuoteCache;

    public RandomQuoteProvider(RandomQuoteCache randomQuoteCache){
        this.randomQuoteCache = randomQuoteCache;
    }

    public RandomQuoteDataSource create(){
        if(!randomQuoteCache.isExpired() && randomQuoteCache.isCached()){
            return null; //new RandomQuoteLocalDataSourceImpl(randomQuoteCache);
        }else{
            return new RandomQuoteRemoteDataSourceImpl(randomQuoteCache);
        }
    }
}
