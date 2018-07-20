package de.karzek.diettracker.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import de.karzek.diettracker.domain.model.IngredientDomainModel;
import de.karzek.diettracker.domain.model.PreparationStepDomainModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
@Data
@AllArgsConstructor
public class RecipeDisplayModel implements Parcelable {
    private int id;
    private String title;
    private byte[] photo;
    private float portions;
    private ArrayList<IngredientDisplayModel> ingredients;
    private ArrayList<PreparationStepDisplayModel> steps;
    private ArrayList<MealDisplayModel> meals;

    protected RecipeDisplayModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        if (in.readByte() == 0x01) {
            photo = new byte[in.readInt()];
            in.readByteArray(photo);
        } else {
            photo = null;
        }
        portions = in.readFloat();
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<>();
            in.readList(ingredients, IngredientDisplayModel.class.getClassLoader());
        } else {
            ingredients = null;
        }
        if (in.readByte() == 0x01) {
            steps = new ArrayList<>();
            in.readList(steps, PreparationStepDisplayModel.class.getClassLoader());
        } else {
            steps = null;
        }
        if (in.readByte() == 0x01) {
            meals = new ArrayList<>();
            in.readList(meals, MealDisplayModel.class.getClassLoader());
        } else {
            meals = null;
        }
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        if (photo == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(photo.length);
            dest.writeByteArray(photo);
        }
        dest.writeFloat(portions);
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }
        if (steps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(steps);
        }
        if (meals == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(meals);
        }
    }

    public static final Parcelable.Creator<RecipeDisplayModel> CREATOR = new Parcelable.Creator<RecipeDisplayModel>() {
        @Override
        public RecipeDisplayModel createFromParcel(Parcel in) {
            return new RecipeDisplayModel(in);
        }

        @Override
        public RecipeDisplayModel[] newArray(int size) {
            return new RecipeDisplayModel[size];
        }
    };
}
