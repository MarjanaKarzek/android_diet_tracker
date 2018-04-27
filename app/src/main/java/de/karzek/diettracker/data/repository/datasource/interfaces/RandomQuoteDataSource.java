package de.karzek.diettracker.data.repository.datasource.interfaces;

import de.karzek.diettracker.data.network.models.RandomQuoteResponse;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public interface RandomQuoteDataSource {

    Observable<RandomQuoteResponse> getRandomQuote();

}
