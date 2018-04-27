package de.karzek.diettracker.domain.mapper;

import de.karzek.diettracker.domain.model.RandomQuoteData;
import de.karzek.diettracker.presentation.model.RandomQuoteDisplayModel;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class RandomQuoteUIMapper {

    public RandomQuoteDisplayModel transform(RandomQuoteData randomQuoteData){
        RandomQuoteDisplayModel randomQuoteDisplayModel = null;
        if(randomQuoteData != null){
            randomQuoteDisplayModel = new RandomQuoteDisplayModel(randomQuoteData.getQuoteText(),
                    randomQuoteData.getQuoteAuthor(),
                    randomQuoteData.getSenderName(),
                    randomQuoteData.getSenderLink(),
                    randomQuoteData.getQuoteLink());
        }
        return randomQuoteDisplayModel;
    }
}
