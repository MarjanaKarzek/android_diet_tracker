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
public class AllergenDisplayModel implements Parcelable {
    private int id;
    private String name;

    protected AllergenDisplayModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<AllergenDisplayModel> CREATOR = new Parcelable.Creator<AllergenDisplayModel>() {
        @Override
        public AllergenDisplayModel createFromParcel(Parcel in) {
            return new AllergenDisplayModel(in);
        }

        @Override
        public AllergenDisplayModel[] newArray(int size) {
            return new AllergenDisplayModel[size];
        }
    };
}
