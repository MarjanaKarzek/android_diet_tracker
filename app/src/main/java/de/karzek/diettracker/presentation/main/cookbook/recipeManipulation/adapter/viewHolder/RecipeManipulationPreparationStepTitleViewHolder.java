package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeManipulationPreparationStepTitleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_image) ImageView imageView;

    private final OnDeleteImageClickListener onDeleteImageClickListener;

    public RecipeManipulationPreparationStepTitleViewHolder(ViewGroup viewGroup,
                                                            OnDeleteImageClickListener onDeleteImageClickListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_man_photo, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onDeleteImageClickListener = onDeleteImageClickListener;
    }

    public void bind(RecipeManipulationViewItemWrapper item) {
        imageView.setImageBitmap(item.getImage());
    }

    @OnClick(R.id.delete_image) public void onDeleteImageClicked() {
        onDeleteImageClickListener.onDeleteImageClicked();
    }

    public interface OnDeleteImageClickListener {
        void onDeleteImageClicked();
    }

}
