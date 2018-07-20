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
public class UnitDisplayModel implements Parcelable {
    private int id;
    private String name;
    private int multiplier;
    private int type;

    protected UnitDisplayModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        multiplier = in.readInt();
        type = in.readInt();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(multiplier);
        dest.writeInt(type);
    }

    public static final Parcelable.Creator<UnitDisplayModel> CREATOR = new Parcelable.Creator<UnitDisplayModel>() {
        @Override
        public UnitDisplayModel createFromParcel(Parcel in) {
            return new UnitDisplayModel(in);
        }

        @Override
        public UnitDisplayModel[] newArray(int size) {
            return new UnitDisplayModel[size];
        }
    };
}
