package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseDialog;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals.adapter.EditMealsListAdapter;
import de.karzek.diettracker.presentation.model.MealDisplayModel;

/**
 * Created by MarjanaKarzek on 07.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 07.06.2018
 */
public class EditMealsDialog extends BaseDialog implements EditMealsDialogContract.View {

    @Inject
    EditMealsDialogContract.Presenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_view)
    FrameLayout loadingView;

    @BindView(R.id.dialog_action_dismiss)
    Button dismiss;
    @BindView(R.id.dialog_action_save)
    Button save;

    private View view;

    private ArrayList<Integer> mealList = new ArrayList<>();

    private SaveMealsSelectionDialogListener listener;

    @Override
    protected void setupDialogComponent() {
        TrackerApplication.get(getContext()).createEditMealsDialogComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_edit_checkbox_list, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mealList = bundle.getIntegerArrayList("SelectedMeals");
        }
        presenter.setSelectedMealList(mealList);

        setupRecyclerView();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.updateMeals(presenter.getSelectedMeals());
            }
        });

        presenter.setView(this);
        presenter.start();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new EditMealsListAdapter(presenter));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listener = (SaveMealsSelectionDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement SaveMealsSelectionDialogListener");
        }
    }

    @Override
    public void updateRecyclerView(ArrayList<MealDisplayModel> meals, HashMap<Integer, Boolean> mealStatus) {
        ((EditMealsListAdapter) recyclerView.getAdapter()).setMeals(meals, mealStatus);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @OnClick(R.id.reset_selection)
    void onResetSelectionClicked() {
        presenter.onResetSelectionClicked();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(getContext()).releaseEditMealsDialogComponent();
    }

    public interface SaveMealsSelectionDialogListener {
        void updateMeals(ArrayList<MealDisplayModel> selectedMeals);
    }
}
