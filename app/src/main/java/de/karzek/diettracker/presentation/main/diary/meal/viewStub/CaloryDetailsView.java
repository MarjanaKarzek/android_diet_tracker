package de.karzek.diettracker.presentation.main.diary.meal.viewStub;

import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class CaloryDetailsView {
    @BindView(R.id.circle_progress_bar_calories) CircularProgressBar caloryProgressBar;
    @BindView(R.id.circle_progress_bar_calories_value) TextView caloryProgressBarValue;
    @BindView(R.id.circle_progress_bar_calories_max_value) TextView caloryProgressBarMaxValue;

    public CaloryDetailsView(View view){
        ButterKnife.bind(view);
    }
}
