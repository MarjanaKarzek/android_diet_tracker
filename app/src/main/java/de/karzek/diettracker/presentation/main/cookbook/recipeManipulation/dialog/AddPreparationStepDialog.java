package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
public class AddPreparationStepDialog extends AppCompatDialogFragment {

    @BindView(R.id.preparation_step_description)
    EditText description;

    @BindView(R.id.dialog_action_dismiss)
    Button dismiss;
    @BindView(R.id.dialog_action_add)
    Button add;

    private OnAddPreparationStepClickedInDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_preparation_step, container, false);

        ButterKnife.bind(this, view);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputFieldsValid()) {
                    dismiss();
                    listener.onAddPreparationStepClicked(description.getText().toString());
                } else {
                    showInvalidFieldsError();
                }
            }
        });

        return view;
    }

    private boolean inputFieldsValid() {
        return !description.getText().toString().equals("");
    }

    private void showInvalidFieldsError() {
        description.setError(getString(R.string.error_message_missing_preparation_step_description));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listener = (OnAddPreparationStepClickedInDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement OnAddPreparationStepClickedInDialogListener");
        }
    }

    public interface OnAddPreparationStepClickedInDialogListener {
        void onAddPreparationStepClicked(String description);
    }
}
