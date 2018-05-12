package de.karzek.diettracker.presentation.main;

import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.model.RandomQuoteDisplayModel;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.random_quote_view) TextView randomQuoteView;

    @Inject MainContract.Presenter presenter;

    @Override protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter.setView(this);
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
