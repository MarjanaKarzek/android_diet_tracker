package de.karzek.diettracker.presentation.util;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import de.karzek.diettracker.presentation.custom.CustomBottomNavigationView;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class StringUtils {
    public static String formatFloat(float value) {
        if(value % 1 == 0){
            return "" + (int)value;
        } else
            return String.format("%.1f", value);
    }

    public static String formatInt(int value) {
        return "" + value;
    }
}
