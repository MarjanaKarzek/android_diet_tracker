package de.karzek.diettracker.data.mapper;

import de.karzek.diettracker.domain.model.RandomQuoteData;
import de.karzek.diettracker.data.network.models.RandomQuoteResponse;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class RandomQuoteMapper {

    public RandomQuoteData transform(RandomQuoteResponse randomQuoteResponse){
        RandomQuoteData randomQuoteData = null;
        if(randomQuoteResponse != null){
            randomQuoteData = new RandomQuoteData(randomQuoteResponse.getQuoteText(),
                    randomQuoteResponse.getQuoteAuthor(),
                    randomQuoteResponse.getSenderName(),
                    randomQuoteResponse.getSenderLink(),
                    randomQuoteResponse.getQuoteLink());
        }
        return randomQuoteData;
    }
}
