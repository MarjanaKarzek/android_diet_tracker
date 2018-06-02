package de.karzek.diettracker.presentation.search.food;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.model.FavoriteGroceryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.search.food.adapter.FoodSearchResultListAdapter;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class FoodSearchActivity extends BaseActivity implements FoodSearchContract.View {

    @Inject
    FoodSearchContract.Presenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.food_search_placeholder) TextView placeholder;
    @BindView(R.id.loading_view) FrameLayout loadingView;

    public static Intent newIntent(Context context) {
        return new Intent(context, FoodSearchActivity.class);
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.food_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.getFoodsMatchingQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        ButterKnife.bind(this);

        setupSupportActionBar();
        setupRecyclerView();

        presenter.setView(this);
        setPresenter(presenter);
        presenter.start();
    }

    @Override
    protected void onDestroy() {
        presenter.finish();
        super.onDestroy();
    }

    @Override
    public void setPresenter(FoodSearchContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showFoodDetails(int id){

    }

    @Override
    public void updateFoodSearchResultList(ArrayList<GroceryDisplayModel> foods) {
        ((FoodSearchResultListAdapter) recyclerView.getAdapter()).setList(foods);
    }

    @Override
    public void showPlaceholder() {
        placeholder.setText(getString(R.string.food_search_placeholder));
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlaceholder() {
        placeholder.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showQueryWithoutResultPlaceholder() {
        placeholder.setText(R.string.food_search_query_without_result_placeholder);
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideQueryWithoutResultPlaceholder() {
        loadingView.setVisibility(View.VISIBLE);
    }

    private void setupSupportActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow_white,null));
        getSupportActionBar().setTitle(getString(R.string.food_search_title));
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FoodSearchResultListAdapter(presenter));
    }
}
