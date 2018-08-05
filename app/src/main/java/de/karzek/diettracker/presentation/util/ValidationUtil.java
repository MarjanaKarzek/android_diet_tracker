package de.karzek.diettracker.presentation.util;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import de.karzek.diettracker.R;

/**
 * Created by MarjanaKarzek on 05.08.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 05.08.2018
 */
public class ValidationUtil {

    public static boolean isValid(int lowerBound, int upperBound, int value, EditText field, Context context){
        if(value < lowerBound) {
            Toast.makeText(context, context.getString(R.string.error_message_lower_bound_exceded, StringUtils.formatInt(lowerBound)),Toast.LENGTH_SHORT).show();
            field.setText("" + lowerBound);
            return false;
        } else if (value > upperBound) {
            Toast.makeText(context, context.getString(R.string.error_message_upper_bound_exceded, StringUtils.formatInt(upperBound)),Toast.LENGTH_SHORT).show();
            field.setText("" + upperBound);
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValid(float lowerBound, float upperBound, float value, EditText field, Context context){
        if(value < lowerBound) {
            Toast.makeText(context, context.getString(R.string.error_message_lower_bound_exceded, StringUtils.formatFloat(lowerBound)),Toast.LENGTH_SHORT).show();
            field.setText("" + lowerBound);
            return false;
        } else if (value > upperBound) {
            Toast.makeText(context, context.getString(R.string.error_message_upper_bound_exceded, StringUtils.formatFloat(upperBound)),Toast.LENGTH_SHORT).show();
            field.setText("" + upperBound);
            return false;
        } else {
            return true;
        }
    }

}
