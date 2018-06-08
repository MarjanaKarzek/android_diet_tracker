package de.karzek.diettracker.presentation.search.grocery.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class GrocerySearchFoodResultViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.grocery_name) TextView groceryName;

    private final OnFoodSearchResultItemClickedListener onItemClickedListener;

    public GrocerySearchFoodResultViewHolder(ViewGroup viewGroup, OnFoodSearchResultItemClickedListener onItemClickedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_grocery_search_food, viewGroup, false));
        ButterKnife.bind(this, itemView);
        this.onItemClickedListener = onItemClickedListener;
    }

    public void bind(GroceryDisplayModel foodSearchResultItem) {
        groceryName.setText(foodSearchResultItem.getName());
        itemView.setTag(foodSearchResultItem.getId());
    }

    @OnClick(R.id.grocery_search_food_item) public void onItemClicked() {
        onItemClickedListener.onItemClicked((int) itemView.getTag());
    }

    public interface OnFoodSearchResultItemClickedListener {
        void onItemClicked(int id);
    }

}
