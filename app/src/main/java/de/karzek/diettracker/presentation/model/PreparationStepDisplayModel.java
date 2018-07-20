package de.karzek.diettracker.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ScrollView;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class PreparationStepDisplayModel implements Parcelable {
    private int id;
    private int stepNo;
    private String description;

    private PreparationStepDisplayModel(Parcel in) {
        id = in.readInt();
        stepNo = in.readInt();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(stepNo);
        dest.writeString(description);
    }

    public static final Parcelable.Creator<PreparationStepDisplayModel> CREATOR = new Parcelable.Creator<PreparationStepDisplayModel>() {
        @Override
        public PreparationStepDisplayModel createFromParcel(Parcel in) {
            return new PreparationStepDisplayModel(in);
        }

        @Override
        public PreparationStepDisplayModel[] newArray(int size) {
            return new PreparationStepDisplayModel[size];
        }
    };
}
