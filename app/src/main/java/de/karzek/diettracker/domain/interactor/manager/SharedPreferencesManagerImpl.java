package de.karzek.diettracker.domain.interactor.manager;

import java.util.ArrayList;

import dagger.Lazy;
import de.karzek.diettracker.data.repository.repositoryInterface.AllergenRepository;
import de.karzek.diettracker.domain.interactor.manager.managerInterface.SharedPreferencesManager;
import de.karzek.diettracker.domain.mapper.AllergenDomainMapper;
import de.karzek.diettracker.presentation.mapper.AllergenUIMapper;
import de.karzek.diettracker.presentation.model.AllergenDisplayModel;
import de.karzek.diettracker.presentation.util.Constants;
import de.karzek.diettracker.presentation.util.SharedPreferencesUtil;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class SharedPreferencesManagerImpl implements SharedPreferencesManager {

    private SharedPreferencesUtil sharedPreferencesUtil;

    public SharedPreferencesManagerImpl(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void initializeStandardValues() {
        sharedPreferencesUtil.initialiseStandardValues();
    }

    @Override
    public ArrayList<Integer> getAllergenIds() {
        String allAllergens = sharedPreferencesUtil.getString(SharedPreferencesUtil.KEY_ALLERGENS, "");

        ArrayList<Integer> allergens = new ArrayList<>();
        if(!allAllergens.equals("")) {
            String[] allergenStrings = allAllergens.split(Constants.SHARED_PREFERENCES_SPLIT_ARRAY_CHAR);
            for (String allergen : allergenStrings) {
                allergens.add(Integer.valueOf(allergen));
            }
        }

        return allergens;
    }

    @Override
    public void putAllergenIds(String allergenSelection) {
        sharedPreferencesUtil.setString(SharedPreferencesUtil.KEY_ALLERGENS, allergenSelection);
    }

}