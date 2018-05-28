package de.karzek.diettracker.presentation.dependencyInjection.module;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.presentation.main.MainContract;
import de.karzek.diettracker.presentation.main.MainPresenter;

/**
 * Created by MarjanaKarzek on 28.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.04.2018
 */

@Module
public class AppModule {

    //data

    /*@Provides
    RandomQuoteCache provideRandomQuoteCache(){
        return new RandomQuoteCacheImpl();
    }

    @Provides
    RandomQuoteProvider provideRandomQuoteProvider(RandomQuoteCache cache){
        return new RandomQuoteProvider(cache);
    }

    @Provides
    RandomQuoteMapper provideRandomQuoteMapper(){
        return new RandomQuoteMapper();
    }

    @Provides
    RandomQuoteRepositoryImpl provideRandomQuoteRepositoryImpl(RandomQuoteProvider provider, RandomQuoteMapper mapper){
        return new RandomQuoteRepositoryImpl(provider, mapper);
    }

    //domain

    @Provides
    RandomQuoteUIMapper provideRandomQuoteUIMapper(){
        return new RandomQuoteUIMapper();
    }

    @Provides
    GetRandomQuoteUseCaseImpl provideGetRandomQuoteUseCaseImpl(RandomQuoteRepositoryImpl repository, RandomQuoteUIMapper mapper){
        return new GetRandomQuoteUseCaseImpl(repository, mapper);
    }*/

    //presentation

    @Provides
    MainContract.Presenter provideMainPresenter() {
        return new MainPresenter();
    }

}
