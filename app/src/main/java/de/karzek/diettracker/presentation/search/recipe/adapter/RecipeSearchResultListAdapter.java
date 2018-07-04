package de.karzek.diettracker.presentation.search.recipe.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.main.cookbook.adapter.viewHolder.RecipeCookbookSearchResultViewHolder;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import de.karzek.diettracker.presentation.search.recipe.adapter.viewHolder.RecipeSearchResultViewHolder;

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

    private ArrayList<RecipeDisplayModel> list;

    public RecipeSearchResultListAdapter(RecipeSearchResultViewHolder.OnRecipeItemClickedListener onRecipeItemClickedListener,
                                         RecipeSearchResultViewHolder.OnRecipeAddPortionClickedListener onRecipeAddPortionClickedListener){
        list = new ArrayList<>();

        this.onRecipeItemClickedListener = onRecipeItemClickedListener;
        this.onRecipeAddPortionClickedListener = onRecipeAddPortionClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeSearchResultViewHolder(parent, onRecipeItemClickedListener, onRecipeAddPortionClickedListener);
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
