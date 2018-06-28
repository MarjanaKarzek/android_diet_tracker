package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeManipulationPreparationStepItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.preparation_step_number) TextView stepNumber;
    @BindView(R.id.preparation_step_text) EditText description;

    private final OnDeletePreparationStepClickedListener onDeletePreparationStepClickedListener;
    private final OnEditPreparationStepFinishedListener onEditPreparationStepFinishedListener;

    public RecipeManipulationPreparationStepItemViewHolder(ViewGroup viewGroup,
                                                           OnDeletePreparationStepClickedListener onDeletePreparationStepClickedListener,
                                                           OnEditPreparationStepFinishedListener onEditPreparationStepFinishedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_recipe_man_preparation_step, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onDeletePreparationStepClickedListener = onDeletePreparationStepClickedListener;
        this.onEditPreparationStepFinishedListener = onEditPreparationStepFinishedListener;

        setupDescriptionChangedListener();
    }

    private void setupDescriptionChangedListener() {
        description.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onEditPreparationStepFinishedListener.onEditPreparationStepFinished((int) itemView.getTag(), description.getText().toString());
                }
                return true;
            }
        });
        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                onEditPreparationStepFinishedListener.onEditPreparationStepFinished((int) itemView.getTag(), description.getText().toString());
            }
        });
    }

    public void bind(RecipeManipulationViewItemWrapper item, int innerListId) {
        stepNumber.setText("" + item.getPreparationStepDisplayModel().getStepNo());
        description.setText(item.getPreparationStepDisplayModel().getDescription());

        itemView.setTag(innerListId);
    }

    @OnClick(R.id.action_delete) public void onDeletePreparationStepClicked() {
        onDeletePreparationStepClickedListener.onDeletePreparationStepClicked((int) itemView.getTag());
    }

    public interface OnDeletePreparationStepClickedListener {
        void onDeletePreparationStepClicked(int id);
    }

    public interface OnEditPreparationStepFinishedListener {
        void onEditPreparationStepFinished(int id, String description);
    }

}
