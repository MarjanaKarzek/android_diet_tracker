package de.karzek.diettracker.presentation.custom;

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
}
