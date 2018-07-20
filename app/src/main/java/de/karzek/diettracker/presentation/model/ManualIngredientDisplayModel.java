package de.karzek.diettracker.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import timber.log.Timber;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ManualIngredientDisplayModel extends IngredientDisplayModel implements Parcelable{
    private String groceryQuery;

    public ManualIngredientDisplayModel(int id, GroceryDisplayModel groceryDisplayModel, float amount, UnitDisplayModel unit, String groceryQuery){
        super(id, groceryDisplayModel, amount, unit);
        this.groceryQuery = groceryQuery;
    }

    private ManualIngredientDisplayModel(Parcel in) {
        readInFromParcel(in);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    protected void readInFromParcel(Parcel in) {
        super.readInFromParcel(in);
        groceryQuery = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(groceryQuery);
    }

    public static final Parcelable.Creator<ManualIngredientDisplayModel> CREATOR = new Parcelable.Creator<ManualIngredientDisplayModel>() {
        @Override
        public ManualIngredientDisplayModel createFromParcel(Parcel in) {
            return new ManualIngredientDisplayModel(in);
        }

        @Override
        public ManualIngredientDisplayModel[] newArray(int size) {
            return new ManualIngredientDisplayModel[size];
        }
    };
}
