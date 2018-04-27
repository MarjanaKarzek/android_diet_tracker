package de.karzek.diettracker.presentation.common;

/**
 * Created by MarjanaKarzek on 26.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 26.04.2018
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
