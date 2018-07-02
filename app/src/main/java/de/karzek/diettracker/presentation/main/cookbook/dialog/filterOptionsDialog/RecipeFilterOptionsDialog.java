package de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseDialog;
import de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog.adapter.RecipeFilterOptionsListAdapter;

/**
 * Created by MarjanaKarzek on 07.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 07.06.2018
 */
public class RecipeFilterOptionsDialog extends BaseDialog implements RecipeFilterOptionsDialogContract.View {

    @Inject
    RecipeFilterOptionsDialogContract.Presenter presenter;

    @BindView(R.id.dialog_title)
    TextView title;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_view)
    FrameLayout loadingView;

    @BindView(R.id.dialog_action_dismiss)
    Button dismiss;
    @BindView(R.id.dialog_action_save)
    Button save;

    private View view;

    private FilterOptionsSelectedDialogListener listener;

    @Override
    protected void setupDialogComponent() {
        TrackerApplication.get(getContext()).createRecipeFilterOptionsDialogComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_edit_checkbox_list, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null)
            presenter.setFilterOptions(bundle.getStringArrayList("filterOptions"));

        title.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.filter_options_dialog_title));
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
                listener.filterOptionsSelected(presenter.getSelectedFilterOptions());
            }
        });

        presenter.setView(this);
        presenter.start();

        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecipeFilterOptionsListAdapter(presenter));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listener = (FilterOptionsSelectedDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement FilterOptionsSelectedDialogListener");
        }
    }

    @Override
    public void setPresenter(RecipeFilterOptionsDialogContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void updateRecyclerView(ArrayList<String> options, HashMap<String, Boolean> optionStatus) {
        ((RecipeFilterOptionsListAdapter) recyclerView.getAdapter()).setList(options, optionStatus);
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
        TrackerApplication.get(getContext()).releaseRecipeFilterOptionsDialogComponent();
    }

    public interface FilterOptionsSelectedDialogListener {
        void filterOptionsSelected(ArrayList<String> filterOptions);
    }
}
