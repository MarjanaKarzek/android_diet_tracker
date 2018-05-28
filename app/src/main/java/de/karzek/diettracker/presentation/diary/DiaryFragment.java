package de.karzek.diettracker.presentation.diary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseFragment;
import de.karzek.diettracker.presentation.diary.adapter.DiaryViewPagerAdapter;
import de.karzek.diettracker.presentation.diary.drink.GenericDrinkFragment;
import de.karzek.diettracker.presentation.diary.meal.GenericMealFragment;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class DiaryFragment extends BaseFragment implements DiaryContract.View {

    @Inject
    DiaryContract.Presenter presenter;

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);

        presenter.setView(this);
        presenter.start();
    }

    @Override
    protected void setupFragmentComponent() {
        TrackerApplication.get(getContext()).createDiaryComponent().inject(this);
    }

    @Override
    public void setPresenter(DiaryContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(getContext()).releaseDiaryComponent();
    }

    private void setupViewPager() {
        DiaryViewPagerAdapter adapter = new DiaryViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new GenericMealFragment(), "Frühstück");
        adapter.addFragment(new GenericMealFragment(), "Mittagessen");
        adapter.addFragment(new GenericMealFragment(), "Abendessen");
        adapter.addFragment(new GenericMealFragment(), "Snacks");
        adapter.addFragment(new GenericDrinkFragment(), "Getränke");
        viewPager.setAdapter(adapter);
    }
}
