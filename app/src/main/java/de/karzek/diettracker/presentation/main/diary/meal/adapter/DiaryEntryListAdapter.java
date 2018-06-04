package de.karzek.diettracker.presentation.main.diary.meal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.dependencyInjection.component.GenericMealComponent;
import de.karzek.diettracker.presentation.main.diary.meal.GenericMealContract;
import de.karzek.diettracker.presentation.main.diary.meal.adapter.viewHolder.DiaryEntryViewHolder;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
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
public class DiaryEntryListAdapter extends RecyclerView.Adapter<DiaryEntryViewHolder> {

    private GenericMealContract.Presenter itemOnClickListener;

    private ArrayList<DiaryEntryDisplayModel> list;

    public DiaryEntryListAdapter(GenericMealContract.Presenter itemOnClickListener){
        list = new ArrayList<>();
        this.itemOnClickListener = itemOnClickListener;
    }

    @NonNull
    @Override
    public DiaryEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiaryEntryViewHolder(parent, itemOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryEntryViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<DiaryEntryDisplayModel> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
