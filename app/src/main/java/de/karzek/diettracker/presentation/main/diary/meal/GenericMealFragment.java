package de.karzek.diettracker.presentation.main.diary.meal;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseFragment;
import de.karzek.diettracker.presentation.main.diary.DiaryFragment;
import de.karzek.diettracker.presentation.main.diary.meal.adapter.DiaryEntryListAdapter;
import de.karzek.diettracker.presentation.main.diary.meal.dialog.MoveDiaryEntryDialog;
import de.karzek.diettracker.presentation.main.diary.meal.viewStub.CaloryDetailsView;
import de.karzek.diettracker.presentation.main.diary.meal.viewStub.CaloryMacroDetailsView;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsActivity;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.StringUtils;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY;

/**
 * Created by MarjanaKarzek on 28.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 28.05.2018
 */
public class GenericMealFragment extends BaseFragment implements GenericMealContract.View {

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
    @BindView(R.id.loading_view) FrameLayout loadingView;

    OnRefreshViewPagerNeededListener callback;

    public interface OnRefreshViewPagerNeededListener {
        public void onRefreshViewPagerNeeded();
    }

    private CaloryDetailsView detailsView;

    private String meal;
    private String selectedDate;
    private SimpleDateFormat databaseDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);

    private HashMap<String, Long> maxValues;
    private ArrayList<MealDisplayModel> meals;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (OnRefreshViewPagerNeededListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRefreshViewPagerNeededListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generic_meal, container, false);
        ButterKnife.bind(this, view);

        meal = getArguments().getString("meal");
        selectedDate = ((DiaryFragment) getActivity().getSupportFragmentManager().findFragmentByTag("DiaryFragment")).getSelectedDate();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        presenter.setView(this);
        presenter.setMeal(meal);

        presenter.start();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new DiaryEntryListAdapter(presenter,presenter,presenter,presenter));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation()));
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
    public void showGroceryListPlaceholder() {
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGroceryListPlaceholder() {
        placeholder.setVisibility(View.GONE);
    }

    @Override
    public void showNutritionDetails(String value) {
        if (value.equals(VALUE_SETTING_NUTRITION_DETAILS_CALORIES_ONLY)) {
            detailsView = new CaloryDetailsView(caloryDetails.inflate());
        } else {
            detailsView = new CaloryMacroDetailsView(caloryMacroDetails.inflate());
        }
    }

    @Override
    public void setNutritionMaxValues(HashMap<String, Long> values) {
        maxValues = values;
        detailsView.getCaloryProgressBarMaxValue().setText("" + values.get(Constants.calories));

        if (detailsView instanceof CaloryMacroDetailsView){
            ((CaloryMacroDetailsView) detailsView).getProteinProgressBarMaxValue().setText("von\n" + values.get(Constants.proteins) + "g");
            ((CaloryMacroDetailsView) detailsView).getCarbsProgressBarMaxValue().setText("von\n" + values.get(Constants.carbs) + "g");
            ((CaloryMacroDetailsView) detailsView).getFatsProgressBarMaxValue().setText("von\n" + values.get(Constants.fats) + "g");
        }
    }

    @Override
    public void updateNutritionDetails(HashMap<String, Float> values) {
        detailsView.getCaloryProgressBar().setProgress(100.0f / maxValues.get(Constants.calories) * values.get(Constants.calories));
        detailsView.getCaloryProgressBarValue().setText("" + (int)values.get(Constants.calories).floatValue());

        if (detailsView instanceof CaloryMacroDetailsView){
            ((CaloryMacroDetailsView) detailsView).getProteinProgressBar().setProgress(100.0f / maxValues.get(Constants.proteins) * values.get(Constants.proteins));
            ((CaloryMacroDetailsView) detailsView).getProteinProgressBarValue().setText("" + StringUtils.formatFloat(values.get(Constants.proteins)));

            ((CaloryMacroDetailsView) detailsView).getCarbsProgressBar().setProgress(100.0f / maxValues.get(Constants.carbs) * values.get(Constants.carbs));
            ((CaloryMacroDetailsView) detailsView).getCarbsProgressBarValue().setText("" + StringUtils.formatFloat(values.get(Constants.carbs)));

            ((CaloryMacroDetailsView) detailsView).getFatsProgressBar().setProgress(100.0f / maxValues.get(Constants.fats) * values.get(Constants.fats));
            ((CaloryMacroDetailsView) detailsView).getFatsProgressBarValue().setText("" + StringUtils.formatFloat(values.get(Constants.fats)));
        }
    }

    @Override
    public void updateGroceryList(ArrayList<DiaryEntryDisplayModel> diaryEntries) {
        ((DiaryEntryListAdapter) recyclerView.getAdapter()).setList(diaryEntries);
    }

    @Override
    public String getSelectedDate() {
        return selectedDate;
    }

    @Override
    public void setSelectedDate(String date) {
        selectedDate = date;
        presenter.updateDiaryEntries(selectedDate);
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
    public void refreshRecyclerView() {
        presenter.updateDiaryEntries(selectedDate);
    }

    @Override
    public void showMoveDiaryEntryDialog(int id, ArrayList<MealDisplayModel> allMeals, ArrayList<String> meals) {
        this.meals = allMeals;

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment previous = getFragmentManager().findFragmentByTag("dialog");
        if (previous != null){
            fragmentTransaction.remove(previous);
        }
        fragmentTransaction.addToBackStack(null);

        AppCompatDialogFragment dialogFragment = new MoveDiaryEntryDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("diaryEntry", id);
        bundle.putStringArrayList("meals", meals);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction,"dialog");
    }

    @Override
    public void startEditMode(int id) {
        startActivity(GroceryDetailsActivity.newIntent(getContext(), null, null, null, id));
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void mealToMoveDiaryEntryToSelected(int diaryEntryId, int mealId) {
        presenter.moveDiaryItemToMeal(diaryEntryId, meals.get(mealId));
        callback.onRefreshViewPagerNeeded();
    }
}
