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
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.StringUtils;
import de.karzek.diettracker.presentation.util.ValidationUtil;

/**
 * Created by MarjanaKarzek on 07.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 07.06.2018
 */
public class AddIngredientDialog extends AppCompatDialogFragment {

    private static final float LOWER_BOUND_AMOUNT = 0.0f;
    private static final float UPPER_BOUND_AMOUNT = 2000.0f;

    @BindView(R.id.ingredient_amount)
    EditText amount;
    @BindView(R.id.spinner_unit)
    Spinner spinner;
    @BindView(R.id.ingredient_product_name)
    EditText groceryQuery;

    @BindView(R.id.dialog_action_dismiss)
    Button dismiss;
    @BindView(R.id.dialog_action_add_ingredient)
    Button addIngredient;

    private View view;
    private ArrayList<String> units;
    private int manualIngredientId;

    private OnAddIngredientClickedInDialogListener addListener;
    private OnSaveIngredientClickedInDialogListener saveListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_add_ingredient, container, false);

        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            units = bundle.getStringArrayList("units");
            manualIngredientId = bundle.getInt("manualIngredientId");
            initializeSpinner();
        }

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                addListener = null;
                saveListener = null;
            }
        });

        if (manualIngredientId == Constants.INVALID_ENTITY_ID)
            addIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inputFieldsValid()) {
                        int selectedUnitId = spinner.getSelectedItemPosition();
                        dismiss();
                        if (amount.getText().toString().equals(""))
                            addListener.onAddManualIngredientClicked(Float.valueOf(amount.getHint().toString()), selectedUnitId, groceryQuery.getText().toString());
                        else
                            addListener.onAddManualIngredientClicked(Float.valueOf(amount.getText().toString()), selectedUnitId, groceryQuery.getText().toString());
                        addListener = null;
                    }
                }
            });
        else {
            addIngredient.setText(R.string.save_button);
            addIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (inputFieldsValid()) {
                        int selectedUnitId = spinner.getSelectedItemPosition();
                        dismiss();
                        if (amount.getText().toString().equals(""))
                            saveListener.onSaveManualIngredientClicked(manualIngredientId, Float.valueOf(amount.getHint().toString()), selectedUnitId, groceryQuery.getText().toString());
                        else
                            saveListener.onSaveManualIngredientClicked(manualIngredientId, Float.valueOf(amount.getText().toString()), selectedUnitId, groceryQuery.getText().toString());
                        saveListener = null;
                    }
                }
            });

            groceryQuery.setText(bundle.getString("groceryQuery"));
            amount.setText(StringUtils.formatFloat(bundle.getFloat("amount")));
            spinner.setSelection(bundle.getInt("unitId"));
        }

        return view;
    }

    private boolean inputFieldsValid() {
        if (groceryQuery.getText().toString().equals("")) {
            showInvalidFieldsError();
            return false;
        } else if(ValidationUtil.isValid(LOWER_BOUND_AMOUNT, UPPER_BOUND_AMOUNT, Float.valueOf(amount.getText().toString()), amount, getContext()))
            return true;
        else
            return false;
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
            addListener = (OnAddIngredientClickedInDialogListener) getActivity();
            saveListener = (OnSaveIngredientClickedInDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement OnAddIngredientClickedInDialogListener and OnSaveIngredientClickedInDialogListener");
        }
    }

    public interface OnAddIngredientClickedInDialogListener {
        void onAddManualIngredientClicked(float amount, int selectedUnitId, String groceryQuery);
    }

    public interface OnSaveIngredientClickedInDialogListener {
        void onSaveManualIngredientClicked(int id, float amount, int selectedUnitId, String groceryQuery);
    }
}
