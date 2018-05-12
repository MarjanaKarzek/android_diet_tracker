package de.karzek.diettracker.presentation.main;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;
import de.karzek.diettracker.presentation.model.RandomQuoteDisplayModel;

/**
 * Created by MarjanaKarzek on 26.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 26.04.2018
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void updateRandomQuote(RandomQuoteDisplayModel randomQuote);

    }

    interface Presenter extends BasePresenter<View> {

        void getRandomQuote();

    }

}
