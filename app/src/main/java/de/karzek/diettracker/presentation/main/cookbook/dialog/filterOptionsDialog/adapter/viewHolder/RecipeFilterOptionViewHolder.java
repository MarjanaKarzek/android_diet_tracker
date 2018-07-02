package de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeFilterOptionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.checkbox) CheckBox option;

    private final OnItemCheckedChangeListener onItemCheckedChangeListener;

    public RecipeFilterOptionViewHolder(ViewGroup viewGroup,
                                        OnItemCheckedChangeListener onItemCheckedChangeListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dialog_checkbox, viewGroup, false));
        ButterKnife.bind(this, itemView);
        this.onItemCheckedChangeListener = onItemCheckedChangeListener;
    }

    public void bind(String value, boolean status) {
        itemView.setTag(value);
        option.setText(value);
        option.setChecked(status);
    }

    @OnClick (R.id.checkbox) public void onCheckedChanged(){
        onItemCheckedChangeListener.onItemCheckChanged((String) itemView.getTag(), option.isChecked());
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckChanged(String option, boolean checked);
    }

}
