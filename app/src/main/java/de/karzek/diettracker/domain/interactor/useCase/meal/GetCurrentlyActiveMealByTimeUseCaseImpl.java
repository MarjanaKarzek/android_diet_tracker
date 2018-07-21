package de.karzek.diettracker.domain.interactor.useCase.meal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.karzek.diettracker.data.model.MealDataModel;
import de.karzek.diettracker.data.repository.repositoryInterface.MealRepository;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.meal.GetCurrentlyActiveMealByTimeUseCase;
import de.karzek.diettracker.domain.mapper.MealDomainMapper;
import de.karzek.diettracker.domain.model.MealDomainModel;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetCurrentlyActiveMealByTimeUseCaseImpl implements GetCurrentlyActiveMealByTimeUseCase {

    private final MealRepository repository;
    private final MealDomainMapper mapper;

    private SimpleDateFormat databaseTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.GERMANY);

    public GetCurrentlyActiveMealByTimeUseCaseImpl(MealRepository repository, MealDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllMeals().map(new Function<List<MealDataModel>, Output>() {
            @Override
            public Output apply(List<MealDataModel> meals) {
                MealDataModel currentlyActiveMeal;

                if (meals.size() == 1)
                    currentlyActiveMeal = meals.get(0);
                else {
                    Calendar currentTime = Calendar.getInstance();

                    try {
                        String[] split = input.getCurrentTime().split(":");

                        currentTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(split[0]));
                        currentTime.set(Calendar.MINUTE, Integer.valueOf(split[1]));
                        currentTime.set(Calendar.SECOND, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        currentlyActiveMeal = meals.get(0);
                        return new Output(mapper.transform(currentlyActiveMeal), Output.SUCCESS);
                    }

                    HashMap<Integer, Long> ranking = new HashMap<>();

                    for (int i = 0; i < meals.size(); i++) {
                        Calendar startTime = Calendar.getInstance();
                        Calendar endTime = Calendar.getInstance();

                        try {
                            String[] split = meals.get(i).getStartTime().split(":");

                            startTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(split[0]));
                            startTime.set(Calendar.MINUTE, Integer.valueOf(split[1]));
                            startTime.set(Calendar.SECOND, 0);

                            split = meals.get(i).getEndTime().split(":");
                            endTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(split[0]));
                            endTime.set(Calendar.MINUTE, Integer.valueOf(split[1]));
                            endTime.set(Calendar.SECOND, 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                            currentlyActiveMeal = meals.get(0);
                            return new Output(mapper.transform(currentlyActiveMeal), Output.SUCCESS);
                        }

                        if(currentTime.after(startTime) && currentTime.before(endTime)) {
                            currentlyActiveMeal = meals.get(i);
                            return new Output(mapper.transform(currentlyActiveMeal), Output.SUCCESS);
                        } else {
                            long distanceStartTime = startTime.getTimeInMillis() - currentTime.getTimeInMillis();
                            if(distanceStartTime < 0)
                                distanceStartTime = distanceStartTime * -1;

                            long distanceEndTime = endTime.getTimeInMillis() - currentTime.getTimeInMillis();
                            if(distanceEndTime < 0)
                                distanceEndTime = distanceEndTime * -1;

                            if(distanceEndTime < distanceStartTime)
                                ranking.put(i, distanceEndTime);
                            else
                                ranking.put(i, distanceStartTime);
                        }
                    }

                    int smallestId = 0;
                    long smallestDistance = Long.MAX_VALUE;
                    for(Integer key: ranking.keySet()){
                        if(ranking.get(key) < smallestDistance) {
                            smallestId = key;
                            smallestDistance = ranking.get(key);
                        }
                    }

                    currentlyActiveMeal = meals.get(smallestId);

                }

                return new Output(mapper.transform(currentlyActiveMeal), Output.SUCCESS);
            }
        });
    }

}