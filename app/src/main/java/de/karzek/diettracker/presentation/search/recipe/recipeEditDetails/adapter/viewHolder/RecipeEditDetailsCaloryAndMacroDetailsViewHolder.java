package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.itemWrapper.RecipeEditDetailsViewItemWrapper;
import de.karzek.diettracker.presentation.util.Constants;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeEditDetailsCaloryAndMacroDetailsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.circle_progress_bar_calories)
    CircularProgressBar caloryProgressBar;
    @BindView(R.id.circle_progress_bar_calories_max_value)
    TextView caloryProgressBarMaxValue;
    @BindView(R.id.circle_progress_bar_calories_value)
    TextView caloryProgressBarValue;

    @BindView(R.id.circle_progress_bar_protein)
    CircularProgressBar proteinProgressBar;
    @BindView(R.id.circle_progress_bar_protein_max_value)
    TextView proteinProgressBarMaxValue;
    @BindView(R.id.circle_progress_bar_protein_value)
    TextView proteinProgressBarValue;

    @BindView(R.id.circle_progress_bar_carbs)
    CircularProgressBar carbsProgressBar;
    @BindView(R.id.circle_progress_bar_carbs_max_value)
    TextView carbsProgressBarMaxValue;
    @BindView(R.id.circle_progress_bar_carbs_value)
    TextView carbsProgressBarValue;

    @BindView(R.id.circle_progress_bar_fats)
    CircularProgressBar fatsProgressBar;
    @BindView(R.id.circle_progress_bar_fats_max_value)
    TextView fatsProgressBarMaxValue;
    @BindView(R.id.circle_progress_bar_fats_value)
    TextView fatsProgressBarValue;

    @BindView(R.id.portions_hint)
    TextView portionsHint;

    public RecipeEditDetailsCaloryAndMacroDetailsViewHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_calory_and_macro_details, viewGroup, false));
        ButterKnife.bind(this, itemView);
    }

    public void bind(RecipeEditDetailsViewItemWrapper item) {
        caloryProgressBarMaxValue.setText("" + item.getMaxValues().get(Constants.CALORIES));
        caloryProgressBar.setProgress(100.0f / item.getMaxValues().get(Constants.CALORIES) * item.getValues().get(Constants.CALORIES));
        caloryProgressBarValue.setText("" + (int) item.getValues().get(Constants.CALORIES).floatValue());

        proteinProgressBarMaxValue.setText("" + item.getMaxValues().get(Constants.PROTEINS));
        proteinProgressBar.setProgress(100.0f / item.getMaxValues().get(Constants.PROTEINS) * item.getValues().get(Constants.PROTEINS));
        proteinProgressBarValue.setText("" + (int) item.getValues().get(Constants.PROTEINS).floatValue());

        carbsProgressBarMaxValue.setText("" + item.getMaxValues().get(Constants.CARBS));
        carbsProgressBar.setProgress(100.0f / item.getMaxValues().get(Constants.CARBS) * item.getValues().get(Constants.CARBS));
        carbsProgressBarValue.setText("" + (int) item.getValues().get(Constants.CARBS).floatValue());

        fatsProgressBarMaxValue.setText("" + item.getMaxValues().get(Constants.FATS));
        fatsProgressBar.setProgress(100.0f / item.getMaxValues().get(Constants.FATS) * item.getValues().get(Constants.FATS));
        fatsProgressBarValue.setText("" + (int) item.getValues().get(Constants.FATS).floatValue());

        portionsHint.setVisibility(View.GONE);
    }

}
