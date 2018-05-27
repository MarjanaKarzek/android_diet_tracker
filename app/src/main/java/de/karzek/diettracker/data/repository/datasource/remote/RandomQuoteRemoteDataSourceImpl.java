package de.karzek.diettracker.data.repository.datasource.remote;

import de.karzek.diettracker.data.cache.interfaces.RandomQuoteCache;
import de.karzek.diettracker.data.network.apiServices.RandomQuoteAPIServiceImpl;
import de.karzek.diettracker.data.network.models.RandomQuoteResponse;
import de.karzek.diettracker.data.repository.datasource.interfaces.RandomQuoteDataSource;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class RandomQuoteRemoteDataSourceImpl implements RandomQuoteDataSource {

    private final RandomQuoteCache randomQuoteCache;

    public  RandomQuoteRemoteDataSourceImpl(RandomQuoteCache randomQuoteCache){
        this.randomQuoteCache = randomQuoteCache;
    }

    @Override
    public Observable<RandomQuoteResponse> getRandomQuote() {
        return RandomQuoteAPIServiceImpl.getService().getRandomQuote().doOnNext(new Consumer<RandomQuoteResponse>() {
            @Override
            public void accept(RandomQuoteResponse randomQuoteResponse) throws Exception {
                //randomQuoteCache.put(randomQuoteResponse); todo write to database
            }
        });
    }
}
