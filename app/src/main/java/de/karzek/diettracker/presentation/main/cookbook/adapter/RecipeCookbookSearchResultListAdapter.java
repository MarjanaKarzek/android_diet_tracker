package de.karzek.diettracker.presentation.main.cookbook.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.main.cookbook.adapter.viewHolder.RecipeCookbookSearchResultViewHolder;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeCookbookSearchResultListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecipeCookbookSearchResultViewHolder.OnRecipeItemClickedListener onRecipeItemClickedListener;
    private RecipeCookbookSearchResultViewHolder.OnRecipeAddPortionClickedListener onRecipeAddPortionClickedListener;
    private RecipeCookbookSearchResultViewHolder.OnRecipeEditClickedListener onRecipeEditClickedListener;
    private RecipeCookbookSearchResultViewHolder.OnRecipeDeleteClickedListener onRecipeDeleteClickedListener;

    private ArrayList<RecipeDisplayModel> list;

    public RecipeCookbookSearchResultListAdapter(RecipeCookbookSearchResultViewHolder.OnRecipeItemClickedListener onRecipeItemClickedListener,
                                                 RecipeCookbookSearchResultViewHolder.OnRecipeAddPortionClickedListener onRecipeAddPortionClickedListener,
                                                 RecipeCookbookSearchResultViewHolder.OnRecipeEditClickedListener onRecipeEditClickedListener,
                                                 RecipeCookbookSearchResultViewHolder.OnRecipeDeleteClickedListener onRecipeDeleteClickedListener) {
        list = new ArrayList<>();

        this.onRecipeItemClickedListener = onRecipeItemClickedListener;
        this.onRecipeAddPortionClickedListener = onRecipeAddPortionClickedListener;
        this.onRecipeEditClickedListener = onRecipeEditClickedListener;
        this.onRecipeDeleteClickedListener = onRecipeDeleteClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeCookbookSearchResultViewHolder(parent, onRecipeItemClickedListener, onRecipeAddPortionClickedListener, onRecipeEditClickedListener, onRecipeDeleteClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RecipeCookbookSearchResultViewHolder) holder).bind(list.get(position));
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
