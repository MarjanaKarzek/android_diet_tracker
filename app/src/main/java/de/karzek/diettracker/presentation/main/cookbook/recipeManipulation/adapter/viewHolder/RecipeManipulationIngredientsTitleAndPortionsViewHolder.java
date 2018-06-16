package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeManipulationIngredientsTitleAndPortionsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.value_portions) EditText portions;

    private final OnPortionChangedListener onPortionChangedListener;

    public RecipeManipulationIngredientsTitleAndPortionsViewHolder(ViewGroup viewGroup,
                                                                   OnPortionChangedListener onPortionChangedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_man_ingredients_title_and_portions, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onPortionChangedListener = onPortionChangedListener;

        portions.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onPortionChangedListener.onPortionChanges(Float.valueOf(portions.getText().toString()));
                }
                return true;
            }
        });
        portions.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                onPortionChangedListener.onPortionChanges(Float.valueOf(portions.getText().toString()));
            }
        });
    }

    public void bind(RecipeManipulationViewItemWrapper item) {
        portions.setText(StringUtils.formatFloat(item.getPortions()));
    }

    @OnClick(R.id.remove_portion) public void onRemovePortionClicked() {
        float newValue = Float.valueOf(portions.getText().toString()) -1;
        portions.setText(StringUtils.formatFloat(newValue));
        onPortionChangedListener.onPortionChanges(newValue);
    }

    @OnClick(R.id.add_portion) public void onAddPortionClicked() {
        float newValue = Float.valueOf(portions.getText().toString()) +1;
        portions.setText(StringUtils.formatFloat(newValue));
        onPortionChangedListener.onPortionChanges(newValue);
    }

    public interface OnPortionChangedListener {
        void onPortionChanges(float portion);
    }

}
