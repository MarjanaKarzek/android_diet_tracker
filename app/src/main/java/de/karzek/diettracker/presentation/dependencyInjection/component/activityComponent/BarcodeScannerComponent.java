package de.karzek.diettracker.presentation.dependencyInjection.component.activityComponent;

import dagger.Subcomponent;
import de.karzek.diettracker.presentation.dependencyInjection.module.activityModules.BarcodeScannerModule;
import de.karzek.diettracker.presentation.search.grocery.barcodeScanner.BarcodeScannerActivity;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
@Subcomponent(modules = {BarcodeScannerModule.class})
public interface BarcodeScannerComponent {

    void inject(BarcodeScannerActivity activity);

}
