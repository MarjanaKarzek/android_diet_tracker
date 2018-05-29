package de.karzek.diettracker.presentation.main.diary.meal;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class GenericMealPresenter implements  GenericMealContract.Presenter {

    GenericMealContract.View view;

    @Override
    public void start() {

    }

    @Override
    public void setView(GenericMealContract.View view) {
        this.view = view;
    }

    @Override
    public void finish() {

    }
}
