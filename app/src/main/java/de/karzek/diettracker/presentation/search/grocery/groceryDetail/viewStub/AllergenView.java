package de.karzek.diettracker.presentation.search.grocery.groceryDetail.viewStub;

import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import lombok.Data;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
@Data
public class AllergenView {

    @BindView(R.id.allergen_warning)
    TextView allergenWarning;

    public AllergenView(View view) {
        ButterKnife.bind(this, view);
    }

}
