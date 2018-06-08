package de.karzek.diettracker.presentation.main.diary.meal.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;

/**
 * Created by MarjanaKarzek on 07.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 07.06.2018
 */
public class MoveDiaryEntryDialog extends AppCompatDialogFragment {

    @BindView(R.id.spinner_meal) Spinner spinner;
    @BindView(R.id.dialog_action_dismiss) Button dismiss;
    @BindView(R.id.dialog_action_move) Button move;

    private View view;
    private int diaryEntryId;
    private ArrayList<String> meals;

    private MealSelectedInDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_move_diary_entry, container, false);

        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            diaryEntryId = bundle.getInt("diaryEntry");
            meals = bundle.getStringArrayList("meals");
            initializeSpinner();
        }

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedMealId = spinner.getSelectedItemPosition();
                dismiss();
                listener.mealToMoveDiaryEntryToSelected(diaryEntryId, selectedMealId);
            }
        });

        return view;
    }

    private void initializeSpinner() {
        ArrayAdapter<String> mealAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, meals);
        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mealAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listener = (MealSelectedInDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement MealSelectedInDialogListener");
        }
    }

    public interface MealSelectedInDialogListener {
        void mealToMoveDiaryEntryToSelected(int diaryEntryId, int mealId);
    }
}
