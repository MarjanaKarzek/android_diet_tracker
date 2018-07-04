package de.karzek.diettracker.presentation.search.recipe.adapter.viewHolder;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeSearchResultViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_image) CircleImageView recipeImage;
    @BindView(R.id.recipe_title) TextView recipeTitle;

    private final OnRecipeItemClickedListener onRecipeItemClickedListener;
    private final OnRecipeAddPortionClickedListener onRecipeAddPortionClickedListener;

    public RecipeSearchResultViewHolder(ViewGroup viewGroup,
                                        OnRecipeItemClickedListener onRecipeItemClickedListener,
                                        OnRecipeAddPortionClickedListener onRecipeAddPortionClickedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recipe_search_result, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onRecipeItemClickedListener = onRecipeItemClickedListener;
        this.onRecipeAddPortionClickedListener = onRecipeAddPortionClickedListener;
    }

    public void bind(RecipeDisplayModel recipe) {
        if (recipe.getPhoto() != null)
            recipeImage.setImageBitmap(BitmapFactory.decodeByteArray(recipe.getPhoto(), 0, recipe.getPhoto().length));
        recipeTitle.setText(recipe.getTitle());
        itemView.setTag(recipe.getId());
    }

    @OnClick(R.id.item_layout) public void onItemClicked() {
        onRecipeItemClickedListener.onItemClicked((int) itemView.getTag());
    }

    @OnClick(R.id.recipe_add_portion) public void onAddPortionClicked() {
        onRecipeAddPortionClickedListener.onAddPortionClicked((int) itemView.getTag());
    }

    public interface OnRecipeItemClickedListener {
        void onItemClicked(int id);
    }

    public interface OnRecipeAddPortionClickedListener {
        void onAddPortionClicked(int id);
    }

}
