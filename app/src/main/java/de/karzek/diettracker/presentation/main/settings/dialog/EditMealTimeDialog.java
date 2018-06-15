package de.karzek.diettracker.presentation.main.settings.dialog;

import android.os.Bundle;
import android.provider.CalendarContract;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.diary.meal.viewStub.CaloryMacroDetailsView;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 07.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 07.06.2018
 */
public class EditMealTimeDialog extends AppCompatDialogFragment {


    @BindView(R.id.dialog_start_hour) EditText startHourField;
    @BindView(R.id.dialog_start_minute) EditText startMinuteField;
    @BindView(R.id.dialog_end_hour) EditText endHourField;
    @BindView(R.id.dialog_end_minute) EditText endMinuteField;

    @BindView(R.id.dialog_action_dismiss) Button dismiss;
    @BindView(R.id.dialog_action_save) Button save;

    private View view;

    private Calendar startTimeCalendar = Calendar.getInstance();
    private Calendar endTimeCalendar = Calendar.getInstance();

    private SimpleDateFormat timeFormat = new SimpleDateFormat("mm:hh:00", Locale.GERMANY);

    private int mealId;
    private String startTime;
    private String endTime;

    private SaveMealTimeDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_edit_meal_time, container, false);

        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mealId = bundle.getInt("mealId");
            startTime = bundle.getString("startTime");
            endTime = bundle.getString("endTime");
            setupTime();
        }

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
                listener.saveMealTime(mealId, timeFormat.format(startTimeCalendar.getTime()), timeFormat.format(endTimeCalendar.getTime()));
            }
        });

        return view;
    }

    private void setupTime() {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        try {
            startDate.setTime(timeFormat.parse(startTime));
            endDate.setTime(timeFormat.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            startDate.set(Calendar.HOUR_OF_DAY,8);
            startDate.set(Calendar.MINUTE,0);
            startDate.set(Calendar.SECOND,0);

            endDate.set(Calendar.HOUR_OF_DAY,10);
            endDate.set(Calendar.MINUTE,0);
            endDate.set(Calendar.SECOND,0);
        }

        startHourField.setText(StringUtils.formatInt(startDate.get(Calendar.HOUR_OF_DAY)));
        startMinuteField.setText(StringUtils.formatInt(startDate.get(Calendar.MINUTE)));

        endHourField.setText(StringUtils.formatInt(endDate.get(Calendar.HOUR_OF_DAY)));
        endMinuteField.setText(StringUtils.formatInt(endDate.get(Calendar.MINUTE)));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listener = (SaveMealTimeDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement SaveMealTimeDialogListener");
        }
    }

    public interface SaveMealTimeDialogListener {
        void saveMealTime(int id, String startTime, String endTime);
    }
}
