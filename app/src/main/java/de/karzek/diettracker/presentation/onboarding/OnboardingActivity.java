package de.karzek.diettracker.presentation.onboarding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;

import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_DISPLAY_SETTINGS;
import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_INGREDIENT_SEARCH;
import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_SLIDE_OPTIONS;
import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_SUPPORT_OPTIONS;
import static de.karzek.diettracker.presentation.util.Constants.ONBOARDING_WELCOME;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class OnboardingActivity extends BaseActivity implements OnboardingContract.View {

    @Inject
    OnboardingContract.Presenter presenter;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.close)
    Button closeButton;

    private int onboardingTag;

    public static Intent newIntent(Context context, int onboardingTag) {
        Intent intent = new Intent(context, OnboardingActivity.class);
        intent.putExtra("onboardingTag", onboardingTag);

        return intent;
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).createOnboardingComponent().inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);

        onboardingTag = getIntent().getIntExtra("onboardingTag", ONBOARDING_WELCOME);
        setupInformation();

        presenter.setView(this);
        presenter.start();
    }

    private void setupInformation() {
        switch (onboardingTag) {
            case ONBOARDING_INGREDIENT_SEARCH:
                image.setImageDrawable(getDrawable(R.drawable.ic_cooking_hut_accent_104dp));
                description.setText(getString(R.string.onboarding_text_ingredient_search));
                break;
            case ONBOARDING_DISPLAY_SETTINGS:
                image.setImageDrawable(getDrawable(R.drawable.ic_cooking_hut_accent_104dp));
                description.setText(getString(R.string.onboarding_text_display_settings));
                break;
            case ONBOARDING_SUPPORT_OPTIONS:
                image.setImageDrawable(getDrawable(R.drawable.ic_cooking_hut_accent_104dp));
                description.setText(getString(R.string.onboarding_text_support_options));
                break;
            case ONBOARDING_SLIDE_OPTIONS:
                image.setImageDrawable(getDrawable(R.drawable.ic_cooking_hut_accent_104dp));
                description.setText(getString(R.string.onboarding_text_slide_options));
                break;
            case ONBOARDING_WELCOME:
                image.setVisibility(View.GONE);
                description.setText(getString(R.string.onboarding_text_welcome));
                closeButton.setText(getString(R.string.lets_go_button));
                break;
            default:
                finishSelf();
        }
        presenter.setOnboardingScreenViewed(onboardingTag);
    }

    @OnClick(R.id.close)
    public void onCloseButtonClicked() {
        presenter.onCloseButtonClicked();
    }

    @OnClick(R.id.background_view)
    public void onBackgroundViewClicked() {
        presenter.onBackgroundViewClicked();
    }

    @Override
    public void finishSelf() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(this).releaseOnboardingComponent();
    }
}
