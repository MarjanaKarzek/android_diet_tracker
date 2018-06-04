package de.karzek.diettracker.presentation.main.diary.meal.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.model.DiaryEntryDisplayModel;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class DiaryEntryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.grocery_summary) TextView grocerySummary;

    private final OnDiaryEntryItemClickedListener onItemClickedListener;

    public DiaryEntryViewHolder(ViewGroup viewGroup, OnDiaryEntryItemClickedListener onItemClickedListener) {
        super(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_diary_grocery, viewGroup, false));
        ButterKnife.bind(this, itemView);
        this.onItemClickedListener = onItemClickedListener;
    }

    public void bind(DiaryEntryDisplayModel diaryEntry) {
        grocerySummary.setText(formatGrocerySummary(diaryEntry));
        itemView.setTag(diaryEntry.getId());
    }

    private String formatGrocerySummary(DiaryEntryDisplayModel diaryEntry){

        String amount = "";
        if (diaryEntry.getAmount() % 10 == 0)
            amount = "" +(int)diaryEntry.getAmount();
        else
            amount = "" + diaryEntry.getAmount();

        return "" + amount + diaryEntry.getUnit().getName() + " " + diaryEntry.getGrocery().getName();
    }

    @OnClick(R.id.diary_grocery_item) public void onItemClicked() {
        onItemClickedListener.onItemClicked((int) itemView.getTag());
    }

    public interface OnDiaryEntryItemClickedListener {
        void onItemClicked(int id);
    }

}
