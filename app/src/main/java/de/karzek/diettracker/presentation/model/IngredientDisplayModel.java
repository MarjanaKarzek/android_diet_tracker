package de.karzek.diettracker.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Data
@AllArgsConstructor
public class IngredientDisplayModel implements Parcelable {
    private int id;
    private GroceryDisplayModel grocery;
    private float amount;
    private UnitDisplayModel unit;

    public IngredientDisplayModel() {
        id = -1;
        grocery = null;
        amount = 0;
        unit = null;
    }

    protected IngredientDisplayModel(Parcel in) {
        readInFromParcel(in);
    }

    protected void readInFromParcel(Parcel in) {
        id = in.readInt();
        grocery = (GroceryDisplayModel) in.readValue(GroceryDisplayModel.class.getClassLoader());
        amount = in.readFloat();
        unit = (UnitDisplayModel) in.readValue(UnitDisplayModel.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeValue(grocery);
        dest.writeFloat(amount);
        dest.writeValue(unit);
    }

    public static final Parcelable.Creator<IngredientDisplayModel> CREATOR = new Parcelable.Creator<IngredientDisplayModel>() {
        @Override
        public IngredientDisplayModel createFromParcel(Parcel in) {
            return new IngredientDisplayModel(in);
        }

        @Override
        public IngredientDisplayModel[] newArray(int size) {
            return new IngredientDisplayModel[size];
        }
    };
}