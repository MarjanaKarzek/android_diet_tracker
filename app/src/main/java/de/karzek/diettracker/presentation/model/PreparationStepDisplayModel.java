package de.karzek.diettracker.presentation.model;

import android.widget.ScrollView;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PreparationStepDisplayModel {
    private int id;
    private int stepNo;
    private String description;

    public PreparationStepDisplayModel() {
        id = -1;
        stepNo = -1;
        description = "";
    }

    public PreparationStepDisplayModel(int id, int stepNo, String description){
        this.id = id;
        this.stepNo = stepNo;
        this.description = description;
    }
}
