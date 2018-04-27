package de.karzek.diettracker.data.cache;

import de.karzek.diettracker.data.cache.interfaces.RandomQuoteCache;
import de.karzek.diettracker.domain.model.RandomQuoteData;
import io.reactivex.Single;

/**
 * Created by MarjanaKarzek on 25.04.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 25.04.2018
 */

public class RandomQuoteCacheImpl implements RandomQuoteCache {

    @Override
    public boolean isExpired() {
        /*Realm realm = Realm.getDefaultInstance();
        if (realm.where(TownshipEntity.class).count() != 0) {
            Date currentTime = new Date(System.currentTimeMillis());
            SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
            Date lastUpdated = null;
            try {
                lastUpdated = ISO8601DATEFORMAT.parse(realm.where(TownshipEntity.class).findFirst().getLastUpdated());
                boolean isExpired = currentTime.getTime() - lastUpdated.getTime() > EXPIRATION_TIME;
                if(isExpired){
                    realm.beginTransaction();
                    realm.delete(TownshipEntity.class);
                    realm.commitTransaction();
                    realm.close();
                }
                return isExpired;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return false;*/

        return true;
    }

    @Override
    public boolean isCached() {
        /*Realm realm = Realm.getDefaultInstance();
        return realm.where(TownshipEntity.class).findAll() != null && realm.where(TownshipEntity.class).findAll().size() > 0;*/

        return false;
    }

    @Override
    public Single<RandomQuoteData> get() {
        /*List<TownshipEntity> townshipEntities = Realm.getDefaultInstance().where(TownshipEntity.class).findAll();
        return Observable.just(townshipEntities);*/

        return null;
    }

    @Override
    public void put(RandomQuoteData randomQuoteData) {
        /*Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(townshipEntities);
        realm.commitTransaction();
        realm.close();*/
    }
}
