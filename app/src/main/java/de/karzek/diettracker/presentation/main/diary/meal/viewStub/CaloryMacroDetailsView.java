package de.karzek.diettracker.presentation.main.diary.meal.viewStub;

import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CaloryMacroDetailsView extends CaloryDetailsView {

    @BindView(R.id.circle_progress_bar_protein)
    CircularProgressBar proteinProgressBar;
    @BindView(R.id.circle_progress_bar_protein_value)
    TextView proteinProgressBarValue;
    @BindView(R.id.circle_progress_bar_protein_max_value)
    TextView proteinProgressBarMaxValue;

    @BindView(R.id.circle_progress_bar_carbs)
    CircularProgressBar carbsProgressBar;
    @BindView(R.id.circle_progress_bar_carbs_value)
    TextView carbsProgressBarValue;
    @BindView(R.id.circle_progress_bar_carbs_max_value)
    TextView carbsProgressBarMaxValue;

    @BindView(R.id.circle_progress_bar_fats)
    CircularProgressBar fatsProgressBar;
    @BindView(R.id.circle_progress_bar_fats_value)
    TextView fatsProgressBarValue;
    @BindView(R.id.circle_progress_bar_fats_max_value)
    TextView fatsProgressBarMaxValue;

    public CaloryMacroDetailsView(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
