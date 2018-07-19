package de.karzek.diettracker.presentation.main.cookbook.dialog.sortOptionsDialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
public class RecipeSortOptionsDialog extends AppCompatDialogFragment {

    @BindView(R.id.dialog_action_dismiss)
    Button dismiss;
    @BindView(R.id.dialog_action_save)
    Button save;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.title_asc)
    RadioButton radioButtonTitleASC;
    @BindView(R.id.title_desc)
    RadioButton radioButtonTitleDESC;
    @BindView(R.id.portions_asc)
    RadioButton radioButtonPortionsASC;
    @BindView(R.id.portions_desc)
    RadioButton radioButtonPortionsDESC;

    private View view;

    private String selectedOption;
    private boolean asc;

    private SortOptionSelectedDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_edit_recipe_sort_option, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selectedOption = bundle.getString("sortOption");
            asc = bundle.getBoolean("asc");
            switch (selectedOption) {
                case "title":
                    if (asc)
                        radioButtonTitleASC.setChecked(true);
                    else
                        radioButtonTitleDESC.setChecked(true);
                    break;
                case "portions":
                    if (asc)
                        radioButtonPortionsASC.setChecked(true);
                    else
                        radioButtonPortionsDESC.setChecked(true);
                    break;
            }
        } else
            radioButtonTitleASC.setSelected(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int viewId) {
                switch (viewId) {
                    case R.id.title_asc:
                        selectedOption = "title";
                        asc = true;
                        break;
                    case R.id.title_desc:
                        selectedOption = "title";
                        asc = false;
                        break;
                    case R.id.portions_asc:
                        selectedOption = "portions";
                        asc = true;
                        break;
                    case R.id.portions_desc:
                        selectedOption = "portions";
                        asc = false;
                        break;
                }
            }
        });

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
                listener.sortOptionSelected(selectedOption, asc);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listener = (SortOptionSelectedDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement SortOptionSelectedDialogListener");
        }
    }

    public interface SortOptionSelectedDialogListener {
        void sortOptionSelected(String sortOption, boolean asc);
    }
}
