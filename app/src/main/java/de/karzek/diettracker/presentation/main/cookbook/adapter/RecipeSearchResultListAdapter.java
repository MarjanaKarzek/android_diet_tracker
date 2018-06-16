package de.karzek.diettracker.presentation.main.cookbook.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.main.cookbook.adapter.viewHolder.RecipeSearchResultViewHolder;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
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
public class RecipeSearchResultListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private RecipeSearchResultViewHolder.OnRecipeItemClickedListener onRecipeItemClickedListener;
    private RecipeSearchResultViewHolder.OnRecipeAddPortionClickedListener onRecipeAddPortionClickedListener;
    private RecipeSearchResultViewHolder.OnRecipeEditClickedListener onRecipeEditClickedListener;
    private RecipeSearchResultViewHolder.OnRecipeDeleteClickedListener onRecipeDeleteClickedListener;

    private ArrayList<RecipeDisplayModel> list;

    public RecipeSearchResultListAdapter(RecipeSearchResultViewHolder.OnRecipeItemClickedListener onRecipeItemClickedListener,
            RecipeSearchResultViewHolder.OnRecipeAddPortionClickedListener onRecipeAddPortionClickedListener,
            RecipeSearchResultViewHolder.OnRecipeEditClickedListener onRecipeEditClickedListener,
            RecipeSearchResultViewHolder.OnRecipeDeleteClickedListener onRecipeDeleteClickedListener){
        list = new ArrayList<>();

        this.onRecipeItemClickedListener = onRecipeItemClickedListener;
        this.onRecipeAddPortionClickedListener = onRecipeAddPortionClickedListener;
        this.onRecipeEditClickedListener = onRecipeEditClickedListener;
        this.onRecipeDeleteClickedListener = onRecipeDeleteClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeSearchResultViewHolder(parent, onRecipeItemClickedListener, onRecipeAddPortionClickedListener, onRecipeEditClickedListener, onRecipeDeleteClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RecipeSearchResultViewHolder) holder).bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<RecipeDisplayModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

}
