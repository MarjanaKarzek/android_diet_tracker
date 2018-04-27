package de.karzek.diettracker.data.cache.interfaces;

import de.karzek.diettracker.domain.model.RandomQuoteData;
import io.reactivex.Single;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public interface RandomQuoteCache {

    boolean isExpired();
    boolean isCached();
    Single<RandomQuoteData> get();
    void put(RandomQuoteData randomQuoteData);

}
