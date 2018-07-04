package de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.search.recipe.recipeEditDetails.adapter.itemWrapper.RecipeEditDetailsViewItemWrapper;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeEditDetailsPhotoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_image) ImageView imageView;

    public RecipeEditDetailsPhotoViewHolder(ViewGroup viewGroup) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_details_photo, viewGroup, false));
        ButterKnife.bind(this, itemView);
    }

    public void bind(RecipeEditDetailsViewItemWrapper item) {
        imageView.setImageBitmap(item.getImage());
    }

}
