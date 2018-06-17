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
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 07.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 07.06.2018
 */
public class AddIngredientDialog extends AppCompatDialogFragment {

    @BindView(R.id.ingredient_amount) EditText amount;
    @BindView(R.id.spinner_unit) Spinner spinner;
    @BindView(R.id.ingredient_product_name) EditText groceryQuery;

    @BindView(R.id.dialog_action_dismiss) Button dismiss;
    @BindView(R.id.dialog_action_add_ingredient) Button addIngredient;

    private View view;
    private ArrayList<String> units;

    private OnAddIngredientClickedInDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_add_ingredient, container, false);

        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            units = bundle.getStringArrayList("units");
            initializeSpinner();
        }

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputFieldsValid()) {
                    int selectedUnitId = spinner.getSelectedItemPosition();
                    dismiss();
                    if (amount.getText().toString().equals(""))
                        listener.onAddIngredientClicked(Float.valueOf(amount.getHint().toString()), selectedUnitId, groceryQuery.getText().toString());
                    else
                        listener.onAddIngredientClicked(Float.valueOf(amount.getText().toString()), selectedUnitId, groceryQuery.getText().toString());
                } else {
                    showInvalidFieldsError();
                }
            }
        });

        return view;
    }

    private boolean inputFieldsValid() {
        if (groceryQuery.getText().toString().equals(""))
            return false;
        else
            return true;
    }

    private void showInvalidFieldsError() {
        groceryQuery.setError(getString(R.string.error_message_missing_product_name));
    }

    private void initializeSpinner() {
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(unitAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listener = (OnAddIngredientClickedInDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement OnAddIngredientClickedInDialogListener");
        }
    }

    public interface OnAddIngredientClickedInDialogListener {
        void onAddIngredientClicked(float amount, int selectedUnitId, String groceryQuery);
    }
}
