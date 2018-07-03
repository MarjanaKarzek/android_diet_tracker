package de.karzek.diettracker.presentation.main.diary.meal.adapter.favoriteRecipeList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.main.diary.meal.adapter.favoriteRecipeList.viewHolder.FavoriteRecipeViewHolder;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class FavoriteRecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FavoriteRecipeViewHolder.OnFavoriteRecipeItemClickedListener onItemClickListener;

    private ArrayList<RecipeDisplayModel> list;

    public FavoriteRecipeListAdapter(FavoriteRecipeViewHolder.OnFavoriteRecipeItemClickedListener onItemClickListener) {
        list = new ArrayList<>();

        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteRecipeViewHolder(parent, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FavoriteRecipeViewHolder) holder).bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<RecipeDisplayModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
