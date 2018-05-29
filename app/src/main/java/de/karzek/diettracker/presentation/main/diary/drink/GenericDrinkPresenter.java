package de.karzek.diettracker.presentation.main.diary.drink;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class GenericDrinkPresenter implements  GenericDrinkContract.Presenter {

    GenericDrinkContract.View view;

    @Override
    public void start() {

    }

    @Override
    public void setView(GenericDrinkContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {

    }
}
