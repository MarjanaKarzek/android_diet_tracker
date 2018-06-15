package de.karzek.diettracker.presentation.main.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseFragment;
import de.karzek.diettracker.presentation.main.settings.adapter.SettingsMealListAdapter;
import de.karzek.diettracker.presentation.main.settings.dialog.EditMealTimeDialog;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.model.MealDisplayModel;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import de.karzek.diettracker.presentation.util.StringUtils;

import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_BOTTLE_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_GLASS_VOLUME;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_CALORIES_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_CARBS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_FATS_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_LIQUID_DAILY;
import static de.karzek.diettracker.presentation.util.SharedPreferencesUtil.KEY_REQUIREMENT_PROTEINS_DAILY;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class SettingsFragment extends BaseFragment implements SettingsContract.View {

    @Inject SettingsContract.Presenter presenter;

    @BindView(R.id.expandable_diet_action) ImageButton expandableDietLayoutAction;
    @BindView(R.id.expandable_diet_details) ExpandableLayout expandableDietLayout;

    @BindView(R.id.edit_text_amount_calories) EditText amountCalories;
    @BindView(R.id.edit_text_amount_proteins) EditText amountProteins;
    @BindView(R.id.edit_text_amount_carbs) EditText amountCarbs;
    @BindView(R.id.edit_text_amount_fats) EditText amountFats;

    @BindView(R.id.expandable_liquids_action) ImageButton expandableLiquidLayoutAction;
    @BindView(R.id.expandable_liquid_details) ExpandableLayout expandableLiquidLayout;

    @BindView(R.id.edit_text_amount_liquids) EditText amountLiquids;
    @BindView(R.id.edit_text_volume_bottle) EditText volumeBottle;
    @BindView(R.id.edit_text_volume_glasses) EditText volumeGlass;

    @BindView(R.id.expandable_meals_action) ImageButton expandableMealsLayoutAction;
    @BindView(R.id.expandable_meal_details) ExpandableLayout expandableMealsLayout;

    @BindView(R.id.settings_meal_recyclerview) RecyclerView recyclerViewMeals;

    @BindView(R.id.expandable_allergies_action) ImageButton expandableAllergiesLayoutAction;
    @BindView(R.id.expandable_allergies_details) ExpandableLayout expandableAllergiesLayout;

    @BindView(R.id.text_allergies) TextView allergenText;

    @BindView(R.id.expandable_data_action) ImageButton expandableDataLayoutAction;
    @BindView(R.id.expandable_data_details) ExpandableLayout expandableDataLayout;

    @BindView(R.id.settings_data_macros) CheckBox dataDisplayMacro;

    @BindView(R.id.expandable_start_screen_action) ImageButton expandableStartScreenLayoutAction;
    @BindView(R.id.expandable_start_screen_details) ExpandableLayout expandableStartScreenLayout;

    @BindView(R.id.settings_start_screen_recipes) CheckBox startScreenRecipes;
    @BindView(R.id.settings_start_screen_liquids) CheckBox startScreenLiquids;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addDoneListenerToEditTextViews();
        presenter.setView(this);
        presenter.start();
    }

    private void addDoneListenerToEditTextViews() {
        amountCalories.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(amountCalories);
                    presenter.updateSharedPreferenceIntValue(KEY_REQUIREMENT_CALORIES_DAILY, Integer.valueOf(amountCalories.getText().toString()));
                }
                return true;
            }
        });
        amountCalories.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                presenter.updateSharedPreferenceIntValue(KEY_REQUIREMENT_CALORIES_DAILY, Integer.valueOf(amountCalories.getText().toString()));
            }
        });

        amountProteins.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(amountProteins);
                    presenter.updateSharedPreferenceIntValue(KEY_REQUIREMENT_PROTEINS_DAILY, Integer.valueOf(amountProteins.getText().toString()));
                }
                return true;
            }
        });
        amountProteins.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                presenter.updateSharedPreferenceIntValue(KEY_REQUIREMENT_PROTEINS_DAILY, Integer.valueOf(amountProteins.getText().toString()));
            }
        });

        amountCarbs.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(amountCarbs);
                    presenter.updateSharedPreferenceIntValue(KEY_REQUIREMENT_CARBS_DAILY, Integer.valueOf(amountCarbs.getText().toString()));
                }
                return true;
            }
        });
        amountCarbs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                presenter.updateSharedPreferenceIntValue(KEY_REQUIREMENT_CARBS_DAILY, Integer.valueOf(amountCarbs.getText().toString()));
            }
        });

        amountFats.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(amountFats);
                    presenter.updateSharedPreferenceIntValue(KEY_REQUIREMENT_FATS_DAILY, Integer.valueOf(amountFats.getText().toString()));
                }
                return true;
            }
        });
        amountFats.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                presenter.updateSharedPreferenceIntValue(KEY_REQUIREMENT_FATS_DAILY, Integer.valueOf(amountFats.getText().toString()));
            }
        });

        amountLiquids.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(amountLiquids);
                    presenter.updateSharedPreferenceFloatValue(KEY_REQUIREMENT_LIQUID_DAILY, Float.valueOf(amountLiquids.getText().toString()));
                }
                return true;
            }
        });
        amountLiquids.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                presenter.updateSharedPreferenceFloatValue(KEY_REQUIREMENT_LIQUID_DAILY, Float.valueOf(amountLiquids.getText().toString()));
            }
        });

        volumeBottle.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(volumeBottle);
                    presenter.updateSharedPreferenceFloatValue(KEY_BOTTLE_VOLUME, Float.valueOf(volumeBottle.getText().toString()));
                }
                return true;
            }
        });
        volumeBottle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                presenter.updateSharedPreferenceFloatValue(KEY_BOTTLE_VOLUME, Float.valueOf(volumeBottle.getText().toString()));
            }
        });

        volumeGlass.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    clearFocusOfView(volumeGlass);
                    presenter.updateSharedPreferenceFloatValue(KEY_GLASS_VOLUME, Float.valueOf(volumeGlass.getText().toString()));
                }
                return true;
            }
        });
        volumeGlass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                presenter.updateSharedPreferenceFloatValue(KEY_GLASS_VOLUME, Float.valueOf(volumeGlass.getText().toString()));
            }
        });

    }

    @Override
    public void clearFocusOfView(EditText view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    @Override
    public void setupMealRecyclerView(ArrayList<MealDisplayModel> meals) {
        SettingsMealListAdapter adapter = new SettingsMealListAdapter(presenter, presenter, presenter);
        adapter.setList(meals);
        recyclerViewMeals.setAdapter(adapter);
        recyclerViewMeals.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void setupAllergenTextView(ArrayList<AllergenDisplayModel> allergens){
        String allergenDescription = "";
        for(int i=0; i < allergens.size(); i++){
            allergenDescription += allergens.get(i).getName();
            if(i < allergens.size()-1)
                allergenDescription += ", ";
        }

        if (allergenDescription.equals(""))
            allergenDescription = getString(R.string.no_allergens_set_placeholder);

        allergenText.setText(allergenDescription);
    }

    @Override
    protected void setupFragmentComponent() {
        TrackerApplication.get(getContext()).createSettingsComponent().inject(this);
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(getContext()).releaseSettingsComponent();
    }

    @Override
    public void fillSettingsOptions(SharedPreferencesUtil sharedPreferencesUtil){
        amountCalories.setText(StringUtils.formatInt(sharedPreferencesUtil.getInt(KEY_REQUIREMENT_CALORIES_DAILY,SharedPreferencesUtil.VALUE_REQUIREMENT_CALORIES_DAILY)));
        amountProteins.setText(StringUtils.formatInt(sharedPreferencesUtil.getInt(SharedPreferencesUtil.KEY_REQUIREMENT_PROTEINS_DAILY,SharedPreferencesUtil.VALUE_REQUIREMENT_PROTEINS_DAILY)));
        amountCarbs.setText(StringUtils.formatInt(sharedPreferencesUtil.getInt(SharedPreferencesUtil.KEY_REQUIREMENT_CARBS_DAILY,SharedPreferencesUtil.VALUE_REQUIREMENT_CARBS_DAILY)));
        amountFats.setText(StringUtils.formatInt(sharedPreferencesUtil.getInt(SharedPreferencesUtil.KEY_REQUIREMENT_FATS_DAILY,SharedPreferencesUtil.VALUE_REQUIREMENT_FATS_DAILY)));

        amountLiquids.setText(StringUtils.formatFloat(sharedPreferencesUtil.getFloat(SharedPreferencesUtil.KEY_REQUIREMENT_LIQUID_DAILY,SharedPreferencesUtil.VALUE_REQUIREMENT_LIQUID_DAILY)));
        volumeBottle.setText(StringUtils.formatFloat(sharedPreferencesUtil.getFloat(SharedPreferencesUtil.KEY_BOTTLE_VOLUME,SharedPreferencesUtil.VALUE_BOTTLE_VOLUME)));
        volumeGlass.setText(StringUtils.formatFloat(sharedPreferencesUtil.getFloat(SharedPreferencesUtil.KEY_GLASS_VOLUME,SharedPreferencesUtil.VALUE_GLASS_VOLUME)));

        if(sharedPreferencesUtil.getString(SharedPreferencesUtil.KEY_SETTING_NUTRITION_DETAILS,SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS).equals(SharedPreferencesUtil.VALUE_SETTING_NUTRITION_DETAILS_CALORIES_AND_MACROS))
            dataDisplayMacro.setChecked(true);

        if(sharedPreferencesUtil.getBoolean(SharedPreferencesUtil.KEY_START_SCREEN_RECIPE,SharedPreferencesUtil.VALUE_TRUE))
            startScreenRecipes.setChecked(true);
        if(sharedPreferencesUtil.getBoolean(SharedPreferencesUtil.KEY_START_SCREEN_LIQUIDS,SharedPreferencesUtil.VALUE_TRUE))
            startScreenLiquids.setChecked(true);
    }

    @Override
    public void showMealEditTimeDialog(MealDisplayModel mealDisplayModel) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment previous = getFragmentManager().findFragmentByTag("dialog");
        if (previous != null)
            fragmentTransaction.remove(previous);
        fragmentTransaction.addToBackStack(null);

        AppCompatDialogFragment dialogFragment = new EditMealTimeDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("mealId", mealDisplayModel.getId());
        bundle.putString("startTime", mealDisplayModel.getStartTime());
        bundle.putString("endTime", mealDisplayModel.getEndTime());
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction,"dialog");
    }

    @Override
    public void updateRecyclerView() {
        recyclerViewMeals.getAdapter().notifyDataSetChanged();
    }

    @OnClick(R.id.expandable_diet_action) public void onExpandDietLayoutClicked(){
        if(expandableDietLayout.isExpanded()){
            collapseExpandableLayout(expandableDietLayout, expandableDietLayoutAction);
        } else {
            expandExpandableLayout(expandableDietLayout, expandableDietLayoutAction);
        }
    }

    @OnClick(R.id.expandable_liquids_action) public void onExpandLiquidsLayoutClicked(){
        if(expandableLiquidLayout.isExpanded()){
            collapseExpandableLayout(expandableLiquidLayout, expandableLiquidLayoutAction);
        } else {

            expandExpandableLayout(expandableLiquidLayout, expandableLiquidLayoutAction);
        }
    }

    @OnClick(R.id.expandable_meals_action) public void onExpandMealLayoutClicked(){
        if(expandableMealsLayout.isExpanded()){
            collapseExpandableLayout(expandableMealsLayout, expandableMealsLayoutAction);
        } else {

            expandExpandableLayout(expandableMealsLayout, expandableMealsLayoutAction);
        }
    }

    @OnClick(R.id.expandable_allergies_action) public void onExpandAllergiesLayoutClicked(){
        if(expandableAllergiesLayout.isExpanded()){
            collapseExpandableLayout(expandableAllergiesLayout, expandableAllergiesLayoutAction);
        } else {

            expandExpandableLayout(expandableAllergiesLayout, expandableAllergiesLayoutAction);
        }
    }

    @OnClick(R.id.expandable_data_action) public void onExpandDataLayoutClicked(){
        if(expandableDataLayout.isExpanded()){
            collapseExpandableLayout(expandableDataLayout, expandableDataLayoutAction);
        } else {

            expandExpandableLayout(expandableDataLayout, expandableDataLayoutAction);
        }
    }

    @OnClick(R.id.expandable_start_screen_action) public void onExpandStartScreenLayoutClicked(){
        if(expandableStartScreenLayout.isExpanded()){
            collapseExpandableLayout(expandableStartScreenLayout, expandableStartScreenLayoutAction);
        } else {

            expandExpandableLayout(expandableStartScreenLayout, expandableStartScreenLayoutAction);
        }
    }

    @OnClick(R.id.add_meal) public void onAddMealClicked(){
        if (recyclerViewMeals.getAdapter().getItemCount() >= 10)
            Toast.makeText(getContext(),R.string.error_message_only_ten_supported_meals, Toast.LENGTH_LONG).show();
        else
            ((SettingsMealListAdapter)recyclerViewMeals.getAdapter()).addItem(new MealDisplayModel(-1, "", "",""));
    }

    private void collapseExpandableLayout(ExpandableLayout layout, ImageButton action){
        action.setImageDrawable(getContext().getDrawable(R.drawable.ic_expand_more_primary_text));
        layout.collapse(true);
    }

    private void expandExpandableLayout(ExpandableLayout layout, ImageButton action){
        action.setImageDrawable(getContext().getDrawable(R.drawable.ic_expand_less_primary_text));
        layout.expand(true);
    }

    @Override
    public void saveMealTime(int id, String startTime, String endTime) {
        presenter.updateMealTime(id, startTime, endTime);
    }
}
