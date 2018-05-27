package de.karzek.diettracker.data.provider;

import de.karzek.diettracker.data.cache.interfaces.GroceryCache;
import de.karzek.diettracker.data.repository.datasource.interfaces.GroceryDataSource;
import de.karzek.diettracker.data.repository.datasource.local.GroceryLocalDataSourceImpl;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GroceryProvider {
    private final GroceryCache groceryCache;

    public GroceryProvider(GroceryCache groceryCache){
        this.groceryCache = groceryCache;
    }

    public GroceryDataSource create(){
        if(!groceryCache.isExpired() && groceryCache.isCached()){
            return new GroceryLocalDataSourceImpl(groceryCache);
        }else{
            return null;//new GroceryLocalDataSourceImpl(groceryCache);
        }
    }
}
