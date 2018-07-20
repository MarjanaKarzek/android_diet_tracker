package de.karzek.diettracker.presentation.main.diary.meal.adapter.favoriteRecipeList.viewHolder;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.util.StringUtils;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_DRINK;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class FavoriteRecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.favorite_recipe_image)
    CircleImageView imageView;
    @BindView(R.id.favorite_recipe_title)
    TextView title;

    private Context context;
    private final OnFavoriteRecipeItemClickedListener onItemClickedListener;

    public FavoriteRecipeViewHolder(ViewGroup viewGroup,
                                    OnFavoriteRecipeItemClickedListener onItemClickedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_diary_favorite_recipe, viewGroup, false));
        ButterKnife.bind(this, itemView);

        context = viewGroup.getContext();
        this.onItemClickedListener = onItemClickedListener;
    }

    public void bind(RecipeDisplayModel entry) {
        if(entry.getPhoto() != null)
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(entry.getPhoto(), 0, entry.getPhoto().length));
        else
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_chef_accent_dark));
        title.setText(entry.getTitle());

        itemView.setTag(entry.getId());
    }

    @OnClick(R.id.favorite_recipe_image) public void onItemClicked() {
        onItemClickedListener.onFavoriteRecipeItemClicked((int) itemView.getTag());
    }

    public interface OnFavoriteRecipeItemClickedListener {
        void onFavoriteRecipeItemClicked(int id);
    }
}
