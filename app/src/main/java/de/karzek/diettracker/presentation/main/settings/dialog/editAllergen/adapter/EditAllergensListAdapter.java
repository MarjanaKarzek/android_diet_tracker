package de.karzek.diettracker.presentation.main.settings.dialog.editAllergen.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import de.karzek.diettracker.presentation.main.diary.meal.adapter.viewHolder.DiaryEntryViewHolder;
import de.karzek.diettracker.presentation.main.settings.dialog.editAllergen.adapter.viewHolder.AllergenViewHolder;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class EditAllergensListAdapter extends RecyclerView.Adapter<AllergenViewHolder> {

    private AllergenViewHolder.OnItemCheckedChangeListener onItemCheckedChangeListener;

    private ArrayList<AllergenDisplayModel> list;
    private HashMap<Integer, Boolean> status;

    public EditAllergensListAdapter(AllergenViewHolder.OnItemCheckedChangeListener onItemCheckedChangeListener) {
        list = new ArrayList<>();
        this.onItemCheckedChangeListener = onItemCheckedChangeListener;
    }

    @NonNull
    @Override
    public AllergenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllergenViewHolder(parent, onItemCheckedChangeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllergenViewHolder holder, int position) {
        holder.bind(list.get(position), status.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setAllergens(ArrayList<AllergenDisplayModel> allergens, HashMap<Integer, Boolean> allergenStatus) {
        this.list = allergens;
        this.status = allergenStatus;
        notifyDataSetChanged();
    }
}
