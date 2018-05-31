package de.karzek.diettracker.presentation.main.diary.meal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseFragment;
import de.karzek.diettracker.presentation.main.diary.meal.viewStub.CaloryDetailsView;
import de.karzek.diettracker.presentation.main.diary.meal.viewStub.CaloryMacroDetailsView;

/**
 * Created by MarjanaKarzek on 28.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.05.2018
 */
public class GenericMealFragment extends BaseFragment implements GenericMealContract.View{

    @Override
    protected void setupFragmentComponent() {
        TrackerApplication.get(getContext()).createGenericMealComponent().inject(this);
    }

    @Inject
    GenericMealContract.Presenter presenter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.viewstub_calory_details) ViewStub caloryDetails;
    @BindView(R.id.viewstub_calory_makro_details) ViewStub caloryMacroDetails;
    @BindView(R.id.grocery_list_placeholder) TextView placeholder;

    private CaloryDetailsView detailsView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generic_meal, container, false);
        ButterKnife.bind(this,view);

        if(false) {
            detailsView = new CaloryDetailsView(caloryDetails.inflate());
        } else {
            detailsView = new CaloryMacroDetailsView(caloryMacroDetails.inflate());
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
        presenter.start();
    }

    @Override
    public void setPresenter(GenericMealContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(getContext()).releaseGenericMealComponent();
    }

    @Override
    public void showGroceryListPlaceholder(){
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView(){
        recyclerView.setVisibility(View.GONE);
    }
}
