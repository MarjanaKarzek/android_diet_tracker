package de.karzek.diettracker.presentation.search.grocery.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.search.grocery.GrocerySearchContract;
import de.karzek.diettracker.presentation.search.grocery.adapter.itemWrapper.GrocerySearchResultItemWrapper;
import de.karzek.diettracker.presentation.search.grocery.adapter.viewHolder.GrocerySearchDrinkResultViewHolder;
import de.karzek.diettracker.presentation.search.grocery.adapter.viewHolder.GrocerySearchFoodResultViewHolder;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class GrocerySearchResultListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private GrocerySearchContract.Presenter itemOnClickListener;
    private GrocerySearchContract.Presenter onAddBottleClickListener;
    private GrocerySearchContract.Presenter onAddGlassClickListener;

    private ArrayList<GrocerySearchResultItemWrapper> list;

    public GrocerySearchResultListAdapter(GrocerySearchContract.Presenter itemOnClickListener,
                                          GrocerySearchContract.Presenter onAddBottleClickListener,
                                          GrocerySearchContract.Presenter onAddGlassClickListener){
        list = new ArrayList<>();
        this.itemOnClickListener = itemOnClickListener;
        this.onAddBottleClickListener = onAddBottleClickListener;
        this.onAddGlassClickListener = onAddGlassClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == GrocerySearchResultItemWrapper.ItemType.FOOD)
            return new GrocerySearchFoodResultViewHolder(parent, itemOnClickListener);
        else
            return new GrocerySearchDrinkResultViewHolder(parent,itemOnClickListener,onAddBottleClickListener,onAddGlassClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GrocerySearchFoodResultViewHolder) {
            ((GrocerySearchFoodResultViewHolder) holder).bind(list.get(position).getDisplayModel());
        } else if (holder instanceof GrocerySearchDrinkResultViewHolder) {
            ((GrocerySearchDrinkResultViewHolder) holder).bind(list.get(position).getDisplayModel());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<GrocerySearchResultItemWrapper> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override public int getItemViewType(int position) {
        return list.get(position).getType();
    }
}
