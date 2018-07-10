package de.karzek.diettracker.presentation.main.settings.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.main.settings.adapter.viewHolder.SettingsMealViewHolder;
import de.karzek.diettracker.presentation.model.MealDisplayModel;

/**
 * Created by MarjanaKarzek on 14.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 14.06.2018
 */
public class SettingsMealListAdapter extends RecyclerView.Adapter<SettingsMealViewHolder> {

    private final SettingsMealViewHolder.OnEditMealClickedListener onEditMealClickedListener;
    private final SettingsMealViewHolder.OnDeleteMealClickedListener onDeleteMealClickedListener;

    private ArrayList<MealDisplayModel> list;

    public SettingsMealListAdapter(SettingsMealViewHolder.OnEditMealClickedListener onEditMealClickedListener,
                                   SettingsMealViewHolder.OnDeleteMealClickedListener onDeleteMealClickedListener) {
        list = new ArrayList<>();

        this.onEditMealClickedListener = onEditMealClickedListener;
        this.onDeleteMealClickedListener = onDeleteMealClickedListener;
    }

    @NonNull
    @Override
    public SettingsMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean lastItem = list.size() == 1;
        return new SettingsMealViewHolder(parent, onEditMealClickedListener, onDeleteMealClickedListener, lastItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsMealViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<MealDisplayModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItem(MealDisplayModel item) {
        list.add(item);
        notifyDataSetChanged();
    }
}
