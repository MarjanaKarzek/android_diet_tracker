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
public class GrocerySearchDrinkResultViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.grocery_name)
    TextView groceryName;

    private final OnGrocerySearchDrinkResultItemClickedListener onItemClickedListener;
    private final OnGrocerySearchDrinkResultAddBottleClickedListener onAddBottleClickedListener;
    private final OnGrocerySearchDrinkResultAddGlassClickedListener onAddGlassClickedListener;

    public GrocerySearchDrinkResultViewHolder(ViewGroup viewGroup,
                                              OnGrocerySearchDrinkResultItemClickedListener onItemClickedListener,
                                              OnGrocerySearchDrinkResultAddBottleClickedListener onAddBottleClickedListener,
                                              OnGrocerySearchDrinkResultAddGlassClickedListener onAddGlassClickedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_grocery_search_drink, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onItemClickedListener = onItemClickedListener;
        this.onAddBottleClickedListener = onAddBottleClickedListener;
        this.onAddGlassClickedListener = onAddGlassClickedListener;
    }

    public void bind(GroceryDisplayModel foodSearchResultItem) {
        groceryName.setText(foodSearchResultItem.getName());
        itemView.setTag(foodSearchResultItem.getId());
    }

    @OnClick(R.id.grocery_search_drink_item)
    public void onItemClicked() {
        onItemClickedListener.onItemClicked((int) itemView.getTag());
    }

    @OnClick(R.id.action_add_bottle)
    public void onAddBottleClicked() {
        onAddBottleClickedListener.onAddBottleClicked((int) itemView.getTag());
    }

    @OnClick(R.id.action_add_glass)
    public void onAddGlassClicked() {
        onAddGlassClickedListener.onAddGlassClicked((int) itemView.getTag());
    }

    public interface OnGrocerySearchDrinkResultItemClickedListener {
        void onItemClicked(int id);
    }

    public interface OnGrocerySearchDrinkResultAddBottleClickedListener {
        void onAddBottleClicked(int id);
    }

    public interface OnGrocerySearchDrinkResultAddGlassClickedListener {
        void onAddGlassClicked(int id);
    }

}
