package de.karzek.diettracker.presentation.main.settings.dialog.manipulateMeal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 10.07.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 10.07.2018
 */
public class ManipulateMealDialog extends AppCompatDialogFragment {

    @BindView(R.id.meal_title)
    EditText mealTitle;

    @BindView(R.id.dialog_start_hour)
    EditText startHourField;
    @BindView(R.id.dialog_start_minute)
    EditText startMinuteField;
    @BindView(R.id.dialog_end_hour)
    EditText endHourField;
    @BindView(R.id.dialog_end_minute)
    EditText endMinuteField;

    @BindView(R.id.dialog_action_dismiss)
    Button dismiss;
    @BindView(R.id.dialog_action_save)
    Button save;

    private int id = -1;
    private String originalMealTitle;
    private String originalStartTime;
    private String originalEndTime;

    private Calendar startTimeCalendar = Calendar.getInstance();
    private Calendar endTimeCalendar = Calendar.getInstance();

    private SimpleDateFormat databaseTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.GERMANY);

    private AddMealFromDialogListener addListener;
    private SaveMealFromDialogListener saveListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_meal, container, false);

        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("mealId");
            originalMealTitle = bundle.getString("mealTitle");
            originalStartTime = bundle.getString("startTime");
            originalEndTime = bundle.getString("endTime");

            setupFieldsWithPreselection();
        } else {
            setupDefaultTime();
        }

        setupTimeChangeListener();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                addListener = null;
                saveListener = null;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputFieldsValid()) {
                    dismiss();
                    if(id == Constants.INVALID_ENTITY_ID)
                        addListener.addMealInDialogClicked(mealTitle.getText().toString(), databaseTimeFormat.format(startTimeCalendar.getTime()), databaseTimeFormat.format(endTimeCalendar.getTime()));
                    else
                        saveListener.saveMealInDialogClicked(id, mealTitle.getText().toString(), databaseTimeFormat.format(startTimeCalendar.getTime()), databaseTimeFormat.format(endTimeCalendar.getTime()));
                    addListener = null;
                    saveListener = null;
                } else {
                    showInvalidFieldsError();
                }
            }
        });

        return view;
    }

    private void setupFieldsWithPreselection() {
        mealTitle.setText(originalMealTitle);

        try {
            startTimeCalendar.setTime(databaseTimeFormat.parse(originalStartTime));
            endTimeCalendar.setTime(databaseTimeFormat.parse(originalEndTime));
        } catch (ParseException e) {
            e.printStackTrace();
            setupDefaultTime();
        }

        startHourField.setText(StringUtils.formatInt(startTimeCalendar.get(Calendar.HOUR_OF_DAY)));
        startMinuteField.setText(StringUtils.formatInt(startTimeCalendar.get(Calendar.MINUTE)));

        endHourField.setText(StringUtils.formatInt(endTimeCalendar.get(Calendar.HOUR_OF_DAY)));
        endMinuteField.setText(StringUtils.formatInt(endTimeCalendar.get(Calendar.MINUTE)));
    }

    private void setupTimeChangeListener() {
        startHourField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(startHourField);
                    startTimeCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startHourField.getText().toString()));
                }
                return true;
            }
        });
        startHourField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                startTimeCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startHourField.getText().toString()));
            }
        });

        startMinuteField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(startMinuteField);
                    startTimeCalendar.set(Calendar.MINUTE, Integer.valueOf(startMinuteField.getText().toString()));
                }
                return true;
            }
        });
        startMinuteField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                startTimeCalendar.set(Calendar.MINUTE, Integer.valueOf(startMinuteField.getText().toString()));
            }
        });

        endHourField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(endHourField);
                    endTimeCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(endHourField.getText().toString()));
                }
                return true;
            }
        });
        endHourField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                endTimeCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(endHourField.getText().toString()));
            }
        });

        endMinuteField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(endMinuteField);
                    endTimeCalendar.set(Calendar.MINUTE, Integer.valueOf(endMinuteField.getText().toString()));
                }
                return true;
            }
        });
        endMinuteField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                endTimeCalendar.set(Calendar.MINUTE, Integer.valueOf(endMinuteField.getText().toString()));
            }
        });
    }

    public void clearFocusOfView(EditText view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    private void setupDefaultTime() {
        startTimeCalendar.set(Calendar.HOUR_OF_DAY, 8);
        startTimeCalendar.set(Calendar.MINUTE, 0);
        startTimeCalendar.set(Calendar.SECOND, 0);

        endTimeCalendar.set(Calendar.HOUR_OF_DAY, 10);
        endTimeCalendar.set(Calendar.MINUTE, 0);
        endTimeCalendar.set(Calendar.SECOND, 0);

        startHourField.setText(StringUtils.formatInt(startTimeCalendar.get(Calendar.HOUR_OF_DAY)));
        startMinuteField.setText(StringUtils.formatInt(startTimeCalendar.get(Calendar.MINUTE)));

        endHourField.setText(StringUtils.formatInt(endTimeCalendar.get(Calendar.HOUR_OF_DAY)));
        endMinuteField.setText(StringUtils.formatInt(endTimeCalendar.get(Calendar.MINUTE)));
    }

    private boolean inputFieldsValid() {
        return !mealTitle.getText().toString().equals("") && !startTimeCalendar.after(endTimeCalendar);
    }

    private void showInvalidFieldsError() {
        if (mealTitle.getText().toString().equals(""))
            mealTitle.setError(getString(R.string.error_message_missing_meal_title));
        if (startTimeCalendar.after(endTimeCalendar))
            Toast.makeText(getContext(), R.string.error_message_invalid_start_end_time, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            addListener = (AddMealFromDialogListener) getParentFragment();
            saveListener = (SaveMealFromDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement AddMealFromDialogListener and SaveMealFromDialogListener");
        }
    }

    public interface AddMealFromDialogListener {
        void addMealInDialogClicked(String name, String startTime, String endTime);
    }

    public interface SaveMealFromDialogListener {
        void saveMealInDialogClicked(int id, String name, String startTime, String endTime);
    }
}
