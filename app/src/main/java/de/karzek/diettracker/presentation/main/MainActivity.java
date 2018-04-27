package de.karzek.diettracker.presentation.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.data.cache.RandomQuoteCacheImpl;
import de.karzek.diettracker.data.cache.interfaces.RandomQuoteCache;
import de.karzek.diettracker.data.mapper.RandomQuoteMapper;
import de.karzek.diettracker.data.provider.RandomQuoteProvider;
import de.karzek.diettracker.data.repository.RandomQuoteRepositoryImpl;
import de.karzek.diettracker.domain.useCase.GetRandomQuoteUseCaseImpl;
import de.karzek.diettracker.domain.mapper.RandomQuoteUIMapper;
import de.karzek.diettracker.presentation.model.RandomQuoteDisplayModel;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.random_quote_view) TextView randomQuoteView;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RandomQuoteCache cache = new RandomQuoteCacheImpl();
        RandomQuoteProvider provider = new RandomQuoteProvider(cache);
        RandomQuoteMapper mapper = new RandomQuoteMapper();
        RandomQuoteRepositoryImpl repository = new RandomQuoteRepositoryImpl(provider, mapper);
        RandomQuoteUIMapper mapper2 = new RandomQuoteUIMapper();
        GetRandomQuoteUseCaseImpl getRandomQuoteUseCase = new GetRandomQuoteUseCaseImpl(repository, mapper2);

        presenter = new MainPresenter(getRandomQuoteUseCase);
        presenter.setTestView(this);
        setPresenter(presenter);
        presenter.start();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        presenter.finish();
        super.onDestroy();
    }

    @Override
    public void updateRandomQuote(RandomQuoteDisplayModel randomQuote){
        randomQuoteView.setText(randomQuote.quoteText);
    }
}
