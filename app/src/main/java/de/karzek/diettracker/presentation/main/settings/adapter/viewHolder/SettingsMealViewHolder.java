package de.karzek.diettracker.presentation.main.settings.adapter.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

    @BindView(R.id.meal_name) EditText mealName;
    @BindView(R.id.action_delete_meal) ImageButton deleteMeal;

    private final OnMealNameChangedListener onMealNameChangedListener;
    private final OnMealEditTimeClickedListener onMealEditTimeClickedListener;
    private final OnDeleteMealClickedListener onDeleteMealClickedListener;

    private boolean lastItem;

    public SettingsMealViewHolder(ViewGroup viewGroup,
                                  OnMealNameChangedListener onMealNameChangedListener,
                                  OnMealEditTimeClickedListener onMealEditTimeClickedListener,
                                  OnDeleteMealClickedListener onDeleteMealClickedListener,
                                  boolean lastItem) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_settings_meal, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onMealNameChangedListener = onMealNameChangedListener;
        this.onMealEditTimeClickedListener = onMealEditTimeClickedListener;
        this.onDeleteMealClickedListener = onDeleteMealClickedListener;

        this.lastItem = lastItem;
    }

    public void bind(MealDisplayModel meal) {
        mealName.setText(meal.getName());
        mealName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onMealNameChangedListener.onItemNameChanged(meal.getId(), mealName);
                }
                return true;
            }
        });
        mealName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                onMealNameChangedListener.onItemNameChanged(meal.getId(), mealName);
            }
        });

        if (lastItem) {
            deleteMeal.setVisibility(View.GONE);
        }

        itemView.setTag(meal.getId());
    }

    @OnClick(R.id.action_meal_time) public void onItemEditMealTimeClicked() {
        onMealEditTimeClickedListener.onItemEditTimeClicked((int) itemView.getTag());
    }

    @OnClick(R.id.action_delete_meal) public void onItemDeleteClicked(){
        onDeleteMealClickedListener.onItemDelete((int) itemView.getTag());
    }

    public interface OnMealNameChangedListener {
        void onItemNameChanged(int id, EditText view);
    }

    public interface OnMealEditTimeClickedListener {
        void onItemEditTimeClicked(int id);
    }

    public interface OnDeleteMealClickedListener {
        void onItemDelete(int id);
    }

}
