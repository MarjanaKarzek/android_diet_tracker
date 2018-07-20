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
public class MealDisplayModel implements Parcelable {
    private int id;
    private String name;
    private String startTime;
    private String endTime;

    protected MealDisplayModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        startTime = in.readString();
        endTime = in.readString();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(startTime);
        dest.writeString(endTime);
    }

    public static final Parcelable.Creator<MealDisplayModel> CREATOR = new Parcelable.Creator<MealDisplayModel>() {
        @Override
        public MealDisplayModel createFromParcel(Parcel in) {
            return new MealDisplayModel(in);
        }

        @Override
        public MealDisplayModel[] newArray(int size) {
            return new MealDisplayModel[size];
        }
    };
}
