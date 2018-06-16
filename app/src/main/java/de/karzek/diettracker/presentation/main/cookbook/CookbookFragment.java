package de.karzek.diettracker.presentation.main.cookbook;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseFragment;
import de.karzek.diettracker.presentation.main.cookbook.adapter.RecipeSearchResultListAdapter;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class CookbookFragment extends BaseFragment implements CookbookContract.View{

    @Inject CookbookContract.Presenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cookbook_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.loading_view) FrameLayout loadingView;
    @BindView(R.id.cookbook_placeholder) TextView placeholder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_cookbook, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cookbook, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.cookbook_search).getActionView();
        searchView.setQueryHint(getString(R.string.recipe_search_hint));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconified(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals(""))
                    presenter.getRecipesMatchingQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (!query.equals(""))
                    presenter.getRecipesMatchingQuery(query);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
        setupRecyclerView();

        presenter.setView(this);
        presenter.start();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecipeSearchResultListAdapter(presenter, presenter, presenter, presenter));
    }

    @Override
    protected void setupFragmentComponent() {
        TrackerApplication.get(getContext()).createCookbookComponent().inject(this);
    }

    @Override
    public void setPresenter(CookbookContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(getContext()).releaseCookbookComponent();
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlaceholder() {
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateRecyclerView(ArrayList<RecipeDisplayModel> recipes) {
        ((RecipeSearchResultListAdapter) recyclerView.getAdapter()).setList(recipes);
    }

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hidePlaceholder() {
        placeholder.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @OnClick(R.id.add_recipe) public void onAddRecipeClicked(){
        //startActivity();
    }
}
