package de.karzek.diettracker.presentation.dependencyInjection.component.fragmentComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.fragmentModule.DiaryModule;
import de.karzek.diettracker.presentation.main.diary.DiaryFragment;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {DiaryModule.class})
public interface DiaryComponent {

    void inject(DiaryFragment fragment);
}
