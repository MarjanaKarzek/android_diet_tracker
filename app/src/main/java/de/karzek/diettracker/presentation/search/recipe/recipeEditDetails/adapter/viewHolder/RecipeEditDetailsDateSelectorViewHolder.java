package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.itemWrapper.RecipeDetailsViewItemWrapper;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.itemWrapper.RecipeEditDetailsViewItemWrapper;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeEditDetailsDateSelectorViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date_label)
    TextView dateLabel;

    private SimpleDateFormat databaseDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d. MMM yyyy", Locale.GERMANY);

    private OnDateClickListener onDateClickListener;

    public RecipeEditDetailsDateSelectorViewHolder(ViewGroup viewGroup,
                                                   OnDateClickListener onDateClickListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_edit_details_date_selector, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onDateClickListener = onDateClickListener;
    }

    public void bind(RecipeEditDetailsViewItemWrapper item) {
        try {
            dateLabel.setText(simpleDateFormat.format(databaseDateFormat.parse(item.getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.date_label) public void onDateClicked(){
        onDateClickListener.onDateClicked();
    }

    public interface OnDateClickListener {
        void onDateClicked();
    }

}
