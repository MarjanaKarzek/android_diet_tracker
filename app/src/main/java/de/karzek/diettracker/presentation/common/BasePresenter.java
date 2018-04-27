package de.karzek.diettracker.presentation.common;

/**
 * Created by MarjanaKarzek on 26.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 26.04.2018
 */

public interface BasePresenter<T extends BaseView> {

    void start();

    void setView(T view);

    void finish();

}
