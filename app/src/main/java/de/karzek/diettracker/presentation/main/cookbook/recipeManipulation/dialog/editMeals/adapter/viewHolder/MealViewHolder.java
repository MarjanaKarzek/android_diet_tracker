package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.editMeals.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.model.MealDisplayModel;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class MealViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.checkbox) CheckBox allergen;

    private final OnItemCheckedChangeListener onItemCheckedChangeListener;

    public MealViewHolder(ViewGroup viewGroup,
                          OnItemCheckedChangeListener onItemCheckedChangeListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dialog_checkbox, viewGroup, false));
        ButterKnife.bind(this, itemView);
        this.onItemCheckedChangeListener = onItemCheckedChangeListener;
    }

    public void bind(MealDisplayModel displayModel, boolean status) {
        itemView.setTag(displayModel.getId());

        allergen.setText(displayModel.getName());
        allergen.setChecked(status);
    }

    @OnClick (R.id.checkbox) public void onCheckedChanged(){
        onItemCheckedChangeListener.onItemCheckChanged((int) itemView.getTag(), allergen.isChecked());
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckChanged(int id, boolean checked);
    }

}
