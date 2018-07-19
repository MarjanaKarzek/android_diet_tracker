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

    private SimpleDateFormat databaseTimeFormat = new SimpleDateFormat("mm:hh:00", Locale.GERMANY);

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
                        currentTime.setTime(databaseTimeFormat.parse(input.getCurrentTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        currentlyActiveMeal = meals.get(0);
                        return new Output(mapper.transform(currentlyActiveMeal), Output.SUCCESS);
                    }

                    HashMap<Integer, Long> ranking = new HashMap<>();

                    for (int i = 0; i < meals.size(); i++) {
                        Calendar startTime = Calendar.getInstance();
                        Calendar endTime = Calendar.getInstance();

                        try {
                            startTime.setTime(databaseTimeFormat.parse(meals.get(i).getStartTime()));
                            endTime.setTime(databaseTimeFormat.parse(meals.get(i).getEndTime()));
                        } catch (ParseException e) {
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
                                distanceEndTime = distanceStartTime * -1;

                            if(distanceEndTime < distanceStartTime)
                                ranking.put(i, distanceEndTime);
                            else
                                ranking.put(i, distanceEndTime);
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