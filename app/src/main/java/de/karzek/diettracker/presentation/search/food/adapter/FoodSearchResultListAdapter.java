package de.karzek.diettracker.presentation.search.food.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.search.food.FoodSearchContract;
import de.karzek.diettracker.presentation.search.food.adapter.viewHolder.FoodSearchResultViewHolder;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class FoodSearchResultListAdapter extends RecyclerView.Adapter<FoodSearchResultViewHolder> {

    private FoodSearchContract.Presenter itemOnClickListener;

    private ArrayList<GroceryDisplayModel> list;

    public FoodSearchResultListAdapter(FoodSearchContract.Presenter itemOnClickListener){
        list = new ArrayList<>();
        this.itemOnClickListener = itemOnClickListener;
    }

    @NonNull
    @Override
    public FoodSearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodSearchResultViewHolder(parent, itemOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodSearchResultViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<GroceryDisplayModel> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
