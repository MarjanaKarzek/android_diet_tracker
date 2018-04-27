package de.karzek.diettracker.data.network.apiServices.interfaces;

import de.karzek.diettracker.data.network.models.RandomQuoteResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public interface RandomQuoteAPIService {

    @GET("api/1.0/?method=getQuote&format=json&lang=en")
    Observable<RandomQuoteResponse> getRandomQuote();

}
