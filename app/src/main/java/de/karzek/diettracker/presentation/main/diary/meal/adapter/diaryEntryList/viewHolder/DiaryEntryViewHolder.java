package de.karzek.diettracker.presentation.main.diary.meal.adapter.diaryEntryList.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.data.cache.model.GroceryEntity;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.util.StringUtils;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class DiaryEntryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.grocery_summary) TextView grocerySummary;
    @BindView(R.id.swipe_layout) SwipeLayout swipeLayout;
    @BindView(R.id.swipe_option_move) ImageButton moveOption;

    private final OnDiaryEntryItemClickedListener onItemClickedListener;
    private final OnDeleteDiaryEntryItemListener onDeleteDiaryEntryItemListener;
    private final OnMoveDiaryEntryItemListener onMoveDiaryEntryItemListener;
    private final OnEditDiaryEntryItemListener onEditDiaryEntryItemListener;

    public DiaryEntryViewHolder(ViewGroup viewGroup,
                                OnDiaryEntryItemClickedListener onItemClickedListener,
                                OnDeleteDiaryEntryItemListener onDeleteDiaryEntryItemListener,
                                OnMoveDiaryEntryItemListener onMoveDiaryEntryItemListener,
                                OnEditDiaryEntryItemListener onEditDiaryEntryItemListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_diary_grocery, viewGroup, false));
        ButterKnife.bind(this, itemView);
        this.onItemClickedListener = onItemClickedListener;
        this.onDeleteDiaryEntryItemListener = onDeleteDiaryEntryItemListener;
        this.onMoveDiaryEntryItemListener = onMoveDiaryEntryItemListener;
        this.onEditDiaryEntryItemListener = onEditDiaryEntryItemListener;
    }

    public void bind(DiaryEntryDisplayModel diaryEntry) {
        grocerySummary.setText(formatGrocerySummary(diaryEntry));
        itemView.setTag(diaryEntry.getId());

        if(diaryEntry.getGrocery().getType() == GroceryEntity.GroceryEntityType.TYPE_DRINK)
            moveOption.setVisibility(View.GONE);
    }

    private String formatGrocerySummary(DiaryEntryDisplayModel diaryEntry){
        String amount = StringUtils.formatFloat(diaryEntry.getAmount());
        return "" + amount + diaryEntry.getUnit().getName() + " " + diaryEntry.getGrocery().getName();
    }

    @OnClick(R.id.grocery_summary) public void onItemClicked() {
        onItemClickedListener.onDiaryEntryClicked((int) itemView.getTag());
    }

    @OnClick(R.id.swipe_option_delete) public void onItemDeleteClicked(){
        swipeLayout.close(false);
        onDeleteDiaryEntryItemListener.onDiaryEntryDeleteClicked((int) itemView.getTag());
    }

    @OnClick(R.id.swipe_option_move) public void onItemMoveClicked(){
        swipeLayout.close();
        onMoveDiaryEntryItemListener.onDiaryEntryMoveClicked((int) itemView.getTag());
    }

    @OnClick(R.id.swipe_option_edit) public void onItemEditClicked(){
        onEditDiaryEntryItemListener.onDiaryEntryEditClicked((int) itemView.getTag());
    }

    public interface OnDiaryEntryItemClickedListener {
        void onDiaryEntryClicked(int id);
    }

    public interface OnDeleteDiaryEntryItemListener {
        void onDiaryEntryDeleteClicked(int id);
    }

    public interface OnMoveDiaryEntryItemListener {
        void onDiaryEntryMoveClicked(int id);
    }

    public interface OnEditDiaryEntryItemListener {
        void onDiaryEntryEditClicked(int id);
    }

}
