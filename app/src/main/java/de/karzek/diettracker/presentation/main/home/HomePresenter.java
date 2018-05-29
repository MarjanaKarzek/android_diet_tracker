package de.karzek.diettracker.presentation.main.home;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    @Override
    public void start() {

    }

    @Override
    public void setView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {

    }
}
