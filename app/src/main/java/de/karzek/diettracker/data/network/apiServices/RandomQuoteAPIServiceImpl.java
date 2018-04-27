package de.karzek.diettracker.data.network.apiServices;

import de.karzek.diettracker.data.network.apiServices.interfaces.RandomQuoteAPIService;
import de.karzek.diettracker.data.network.client.RetrofitClient;
import de.karzek.diettracker.data.network.models.RandomQuoteResponse;
import retrofit2.Call;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class RandomQuoteAPIServiceImpl {

    private static final String BASE_URL = "http://api.forismatic.com/";

    private static RandomQuoteAPIService randomQuoteAPIService;

    public static RandomQuoteAPIService getService() {
        if(randomQuoteAPIService == null){
            randomQuoteAPIService = RetrofitClient.getClient(BASE_URL).create(RandomQuoteAPIService.class);
        }
        return randomQuoteAPIService;
    }
}
