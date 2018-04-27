package de.karzek.diettracker.domain.useCase;

import de.karzek.diettracker.data.repository.RandomQuoteRepositoryImpl;
import de.karzek.diettracker.domain.useCase.useCaseInterface.GetRandomQuoteUseCase;
import de.karzek.diettracker.domain.mapper.RandomQuoteUIMapper;
import de.karzek.diettracker.domain.model.RandomQuoteData;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 2.0
 * @date 26.04.2018
 */

public class GetRandomQuoteUseCaseImpl implements GetRandomQuoteUseCase {

    private final RandomQuoteRepositoryImpl repository;
    private final RandomQuoteUIMapper mapper;

    public GetRandomQuoteUseCaseImpl(RandomQuoteRepositoryImpl repository, RandomQuoteUIMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getRandomQuote().map(new Function<RandomQuoteData, Output>() {
            @Override
            public Output apply(RandomQuoteData randomQuoteData) throws Exception {
                return new Output(Output.SUCCESS, randomQuoteData);
            }
        });
    }
}
