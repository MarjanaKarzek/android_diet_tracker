package de.karzek.diettracker.presentation.main.settings.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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
public class SettingsMealViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.meal_name) TextView mealName;
    @BindView(R.id.action_delete_meal) ImageButton deleteMeal;

    private final OnEditMealClickedListener onEditMealClickedListener;
    private final OnDeleteMealClickedListener onDeleteMealClickedListener;

    private boolean lastItem;

    public SettingsMealViewHolder(ViewGroup viewGroup,
                                  OnEditMealClickedListener onEditMealClickedListener,
                                  OnDeleteMealClickedListener onDeleteMealClickedListener,
                                  boolean lastItem) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_settings_meal, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onEditMealClickedListener = onEditMealClickedListener;
        this.onDeleteMealClickedListener = onDeleteMealClickedListener;

        this.lastItem = lastItem;
    }

    public void bind(MealDisplayModel meal) {
        mealName.setText(meal.getName());

        if (lastItem) {
            deleteMeal.setVisibility(View.GONE);
        }

        itemView.setTag(meal.getId());
    }

    @OnClick(R.id.action_meal_time) public void onItemEditMealTimeClicked() {
        onEditMealClickedListener.onEditMealItemClicked((int) itemView.getTag());
    }

    @OnClick(R.id.action_delete_meal) public void onItemDeleteClicked(){
        onDeleteMealClickedListener.onMealItemDelete((int) itemView.getTag());
    }

    public interface OnEditMealClickedListener {
        void onEditMealItemClicked(int id);
    }

    public interface OnDeleteMealClickedListener {
        void onMealItemDelete(int id);
    }

}
