package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.AutomatedIngredientSearchContract;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.automatedIngredientSearch.adapter.itemWrapper.IngredientSearchItemWrapper;
import de.karzek.diettracker.presentation.model.IngredientDisplayModel;
import de.karzek.diettracker.presentation.model.ManualIngredientDisplayModel;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class IngredientSearchFailedViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ingredient_summary)
    TextView summary;
    @BindView(R.id.fail_reason)
    TextView failReason;

    private OnStartGrocerySearchClickListener onStartGrocerySearchClickListener;
    private OnStartBarcodeScanClickListener onStartBarcodeScanClickListener;
    private OnDeleteIngredientClickListener onDeleteIngredientClickListener;

    private Context context;

    public IngredientSearchFailedViewHolder(ViewGroup viewGroup,
                                            OnStartGrocerySearchClickListener onStartGrocerySearchClickListener,
                                            OnStartBarcodeScanClickListener onStartBarcodeScanClickListener,
                                            OnDeleteIngredientClickListener onDeleteIngredientClickListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_ingredient_search_failed, viewGroup, false));
        ButterKnife.bind(this, itemView);

        this.onStartGrocerySearchClickListener = onStartGrocerySearchClickListener;
        this.onStartBarcodeScanClickListener = onStartBarcodeScanClickListener;
        this.onDeleteIngredientClickListener = onDeleteIngredientClickListener;

        context = viewGroup.getContext();
    }

    public void bind(IngredientSearchItemWrapper item, int index) {
        summary.setText(formatSummary(item.getIngredientDisplayModel()));
        if(item.getFailReason() == AutomatedIngredientSearchContract.FailReasons.FAIL_REASON_WRONG_UNIT)
            failReason.setText(context.getString(R.string.fail_reason_description_wrong_unit));
        else
            failReason.setText(context.getString(R.string.fail_reason_description_grocery_not_found));

        itemView.setTag(index);
    }

    private String formatSummary(IngredientDisplayModel ingredient){
        if (ingredient instanceof ManualIngredientDisplayModel) {
            String amount = StringUtils.formatFloat(ingredient.getAmount());
            return amount + ingredient.getUnit().getName() + " " + ((ManualIngredientDisplayModel) ingredient).getGroceryQuery();
        } else {
            String amount = StringUtils.formatFloat(ingredient.getAmount());
            return amount + ingredient.getUnit().getName() + " " + ingredient.getGrocery().getName();
        }
    }

    @OnClick(R.id.action_search) public void onStartGrocerySearchClicked() {
        onStartGrocerySearchClickListener.onStartGrocerySearchClicked((int) itemView.getTag());
    }

    @OnClick(R.id.action_barcode_search) public void onStartBarcodeScanClicked() {
        onStartBarcodeScanClickListener.onStartBarcodeScanClicked((int) itemView.getTag());
    }

    @OnClick(R.id.action_delete) public void onDeleteIngredientClicked() {
        onDeleteIngredientClickListener.onDeleteIngredientClicked((int) itemView.getTag());
    }

    public interface OnStartGrocerySearchClickListener {
        void onStartGrocerySearchClicked(int index);
    }

    public interface OnStartBarcodeScanClickListener {
        void onStartBarcodeScanClicked(int index);
    }

    public interface OnDeleteIngredientClickListener {
        void onDeleteIngredientClicked(int index);
    }

}
