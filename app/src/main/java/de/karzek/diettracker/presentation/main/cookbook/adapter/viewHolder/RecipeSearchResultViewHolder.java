package de.karzek.diettracker.presentation.main.cookbook.adapter.viewHolder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
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
    @BindView(R.id.swipe_layout) SwipeLayout swipeLayout;

    private final OnRecipeItemClickedListener onRecipeItemClickedListener;
    private final OnRecipeAddPortionClickedListener onRecipeAddPortionClickedListener;
    private final OnRecipeEditClickedListener onRecipeEditClickedListener;
    private final OnRecipeDeleteClickedListener onRecipeDeleteClickedListener;

    public RecipeSearchResultViewHolder(ViewGroup viewGroup,
                                        OnRecipeItemClickedListener onRecipeItemClickedListener,
                                        OnRecipeAddPortionClickedListener onRecipeAddPortionClickedListener,
                                        OnRecipeEditClickedListener onRecipeEditClickedListener,
                                        OnRecipeDeleteClickedListener onRecipeDeleteClickedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cookbook_recipe, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onRecipeItemClickedListener = onRecipeItemClickedListener;
        this.onRecipeAddPortionClickedListener = onRecipeAddPortionClickedListener;
        this.onRecipeEditClickedListener = onRecipeEditClickedListener;
        this.onRecipeDeleteClickedListener = onRecipeDeleteClickedListener;
    }

    public void bind(RecipeDisplayModel recipe) {
        if (recipe.getPhoto() != null)
            recipeImage.setImageBitmap(BitmapFactory.decodeByteArray(recipe.getPhoto(), 0, recipe.getPhoto().length));
        recipeTitle.setText(recipe.getTitle());
        itemView.setTag(recipe.getId());
    }

    @OnClick(R.id.item_layout) public void onItemClicked() {
        swipeLayout.close();
        onRecipeItemClickedListener.onItemClicked((int) itemView.getTag());
    }

    @OnClick(R.id.swipe_option_portion) public void onAddPortionClicked() {
        onRecipeAddPortionClickedListener.onAddPortionClicked((int) itemView.getTag());
    }

    @OnClick(R.id.swipe_option_edit) public void onEditRecipeClicked() {
        swipeLayout.close();
        onRecipeEditClickedListener.onEditRecipeClicked((int) itemView.getTag());
    }

    @OnClick(R.id.swipe_option_delete) public void onDeleteRecipeClicked() {
        swipeLayout.close();
        onRecipeDeleteClickedListener.onDeleteRecipeClicked((int) itemView.getTag());
    }

    public interface OnRecipeItemClickedListener {
        void onItemClicked(int id);
    }

    public interface OnRecipeAddPortionClickedListener {
        void onAddPortionClicked(int id);
    }

    public interface OnRecipeEditClickedListener {
        void onEditRecipeClicked(int id);
    }

    public interface OnRecipeDeleteClickedListener {
        void onDeleteRecipeClicked(int id);
    }

}
