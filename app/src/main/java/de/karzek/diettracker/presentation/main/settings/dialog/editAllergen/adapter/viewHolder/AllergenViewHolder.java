package de.karzek.diettracker.presentation.main.settings.dialog.editAllergen.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.util.StringUtils;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_DRINK;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class AllergenViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.allergen) CheckBox allergen;

    private final OnItemCheckedChangeListener onItemCheckedChangeListener;

    public AllergenViewHolder(ViewGroup viewGroup,
                              OnItemCheckedChangeListener onItemCheckedChangeListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_edit_allergens_dialog_allergen, viewGroup, false));
        ButterKnife.bind(this, itemView);
        this.onItemCheckedChangeListener = onItemCheckedChangeListener;
    }

    public void bind(AllergenDisplayModel displayModel, boolean status) {
        itemView.setTag(displayModel.getId());

        allergen.setText(displayModel.getName());
        allergen.setChecked(status);
    }

    @OnClick (R.id.allergen) public void onCheckedChanged(){
        onItemCheckedChangeListener.onItemCheckChanged((int) itemView.getTag(), allergen.isChecked());
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckChanged(int id, boolean checked);
    }

}
