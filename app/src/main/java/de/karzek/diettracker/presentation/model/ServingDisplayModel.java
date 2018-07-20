package de.karzek.diettracker.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Value
@AllArgsConstructor
public class ServingDisplayModel implements Parcelable {
    private int id;
    private String description;
    private int amount;
    private UnitDisplayModel unit;

    private ServingDisplayModel(Parcel in) {
        id = in.readInt();
        description = in.readString();
        amount = in.readInt();
        unit = (UnitDisplayModel) in.readValue(UnitDisplayModel.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeInt(amount);
        dest.writeValue(unit);
    }

    public static final Parcelable.Creator<ServingDisplayModel> CREATOR = new Parcelable.Creator<ServingDisplayModel>() {
        @Override
        public ServingDisplayModel createFromParcel(Parcel in) {
            return new ServingDisplayModel(in);
        }

        @Override
        public ServingDisplayModel[] newArray(int size) {
            return new ServingDisplayModel[size];
        }
    };
}
