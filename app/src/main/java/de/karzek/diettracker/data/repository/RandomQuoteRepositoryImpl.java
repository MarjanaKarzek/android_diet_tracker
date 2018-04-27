package de.karzek.diettracker.data.repository;

import de.karzek.diettracker.data.mapper.RandomQuoteMapper;
import de.karzek.diettracker.data.network.models.RandomQuoteResponse;
import de.karzek.diettracker.data.provider.RandomQuoteProvider;
import de.karzek.diettracker.domain.model.RandomQuoteData;
import de.karzek.diettracker.data.repository.repositoryInterface.RandomQuoteRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class RandomQuoteRepositoryImpl implements RandomQuoteRepository {

    private final RandomQuoteProvider provider;
    private final RandomQuoteMapper mapper;

    public RandomQuoteRepositoryImpl(RandomQuoteProvider provider, RandomQuoteMapper mapper) {
        this.provider = provider;
        this.mapper = mapper;
    }

    @Override
    public Observable<RandomQuoteData> getRandomQuote() {
        return provider.create().getRandomQuote().map(new Function<RandomQuoteResponse, RandomQuoteData>() {
            @Override
            public RandomQuoteData apply(RandomQuoteResponse randomQuoteResponse) throws Exception {
                return mapper.transform(randomQuoteResponse);
            }
        });
    }
}
