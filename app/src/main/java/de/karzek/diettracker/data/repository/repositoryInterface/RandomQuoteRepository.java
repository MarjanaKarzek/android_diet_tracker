package de.karzek.diettracker.data.repository.repositoryInterface;

import de.karzek.diettracker.domain.model.RandomQuoteData;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public interface RandomQuoteRepository {

    Observable<RandomQuoteData> getRandomQuote();

}
