package de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.itemWrapper.RecipeDetailsViewItemWrapper;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeDetailsTitleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    TextView title;

    public RecipeDetailsTitleViewHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_title, viewGroup, false));
        ButterKnife.bind(this, itemView);
    }

    public void bind(RecipeDetailsViewItemWrapper item) {
        title.setText(item.getTitle());
    }

}
