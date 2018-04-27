package de.karzek.diettracker.presentation.main;

import de.karzek.diettracker.domain.useCase.GetRandomQuoteUseCaseImpl;
import de.karzek.diettracker.domain.useCase.useCaseInterface.GetRandomQuoteUseCase;
import de.karzek.diettracker.domain.mapper.RandomQuoteUIMapper;
import de.karzek.diettracker.presentation.model.RandomQuoteDisplayModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class MainPresenter implements MainContract.Presenter {

    //private MainContract.View view;
    private MainActivity view;

    private GetRandomQuoteUseCaseImpl getRandomQuoteUseCase;

    private RandomQuoteUIMapper mapper;

    private CompositeDisposable disposables;

    public MainPresenter(GetRandomQuoteUseCaseImpl getRandomQuoteUseCase){
        this.getRandomQuoteUseCase = getRandomQuoteUseCase;
        mapper = new RandomQuoteUIMapper();
        disposables = new CompositeDisposable();
    }

    @Override
    public void start() {
        getRandomQuote();
    }

    @Override
    public void setView(MainContract.View view) {
        //this.view = view;
    }

    @Override
    public void setTestView(MainActivity view){
        this.view = view;
    }


    @Override
    public void finish() {
        disposables.clear();
    }

    @Override
    public void getRandomQuote() {
        Disposable disposable = getRandomQuoteUseCase.execute(new GetRandomQuoteUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    RandomQuoteDisplayModel randomQuote = mapper.transform(output.getRandomQuoteData());
                    view.updateRandomQuote(randomQuote);
                });
        disposables.add(disposable);
    }
}
