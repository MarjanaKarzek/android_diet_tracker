package de.karzek.diettracker.presentation.main;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    private CompositeDisposable disposables;

    public MainPresenter(){
        disposables = new CompositeDisposable();
    }

    @Override
    public void start() {

    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {
        disposables.clear();
    }

    /*@Override
    public void getRandomQuote() {
        Disposable disposable = getRandomQuoteUseCase.execute(new GetRandomQuoteUseCase.Input())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    RandomQuoteDisplayModel randomQuote = mapper.transform(output.getRandomQuoteData());
                    view.updateRandomQuote(randomQuote);
                });
        disposables.add(disposable);
    }*/
}
