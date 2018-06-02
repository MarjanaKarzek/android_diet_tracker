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
public class ViewUtils {
    public static void customizeBottomNavigation(CustomBottomNavigationView view) {
        view.enableAnimation(true);
        view.enableItemShiftingMode(false);
        view.enableShiftingMode(false);
        view.setTextVisibility(false);
    }

    public static void addElevationToFABMenuLabels(Context context, FloatingActionsMenu floatingActionsMenu) {
        for (int i = 0; i < floatingActionsMenu.getChildCount(); i++) {
            if (floatingActionsMenu.getChildAt(i) instanceof TextView) {
                ViewCompat.setElevation(floatingActionsMenu.getChildAt(i), dpToPx(3, context));
            }
        }
    }

    private static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }
}
