package de.karzek.diettracker.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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
public class GroceryDisplayModel implements Parcelable{
    private int id;
    private String barcode;
    private String name;
    private float calories_per_1U;
    private float proteins_per_1U;
    private float carbohydrates_per_1U;
    private float fats_per_1U;
    private int type;
    private int unit_type;
    private ArrayList<AllergenDisplayModel> allergens;
    private ArrayList<ServingDisplayModel> servings;

    protected GroceryDisplayModel(Parcel in) {
        id = in.readInt();
        barcode = in.readString();
        name = in.readString();
        calories_per_1U = in.readFloat();
        proteins_per_1U = in.readFloat();
        carbohydrates_per_1U = in.readFloat();
        fats_per_1U = in.readFloat();
        type = in.readInt();
        unit_type = in.readInt();
        if (in.readByte() == 0x01) {
            allergens = new ArrayList<>();
            in.readList(allergens, AllergenDisplayModel.class.getClassLoader());
        } else {
            allergens = null;
        }
        if (in.readByte() == 0x01) {
            servings = new ArrayList<>();
            in.readList(servings, ServingDisplayModel.class.getClassLoader());
        } else {
            servings = null;
        }
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(barcode);
        dest.writeString(name);
        dest.writeFloat(calories_per_1U);
        dest.writeFloat(proteins_per_1U);
        dest.writeFloat(carbohydrates_per_1U);
        dest.writeFloat(fats_per_1U);
        dest.writeInt(type);
        dest.writeInt(unit_type);
        if (allergens == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(allergens);
        }
        if (servings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(servings);
        }
    }

    public static final Parcelable.Creator<GroceryDisplayModel> CREATOR = new Parcelable.Creator<GroceryDisplayModel>() {
        @Override
        public GroceryDisplayModel createFromParcel(Parcel in) {
            return new GroceryDisplayModel(in);
        }

        @Override
        public GroceryDisplayModel[] newArray(int size) {
            return new GroceryDisplayModel[size];
        }
    };
}
