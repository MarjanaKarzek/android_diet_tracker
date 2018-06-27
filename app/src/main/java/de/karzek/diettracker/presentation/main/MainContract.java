package de.karzek.diettracker.presentation.main;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;

/**
 * Created by MarjanaKarzek on 26.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 26.04.2018
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<View> {

    }

}
