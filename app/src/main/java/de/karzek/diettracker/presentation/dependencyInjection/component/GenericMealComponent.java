package de.karzek.diettracker.presentation.dependencyInjection.component;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.GenericMealModule;
import de.karzek.diettracker.presentation.diary.meal.GenericMealFragment;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Subcomponent(modules = {GenericMealModule.class})
public interface GenericMealComponent {

    void inject(GenericMealFragment fragment);
}
