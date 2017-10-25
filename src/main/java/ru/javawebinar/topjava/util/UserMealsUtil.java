package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        System.out.println("TODO return filtered list with correctly exceeded field");

        List<UserMealWithExceed> withExceedList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        Map<String, List<UserMeal>> map = new HashMap<>();
        Map<String, Integer> caloriesPerDayMap = new HashMap<>();

        for (UserMeal userMeal : mealList) {

            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {

                map.merge(
                        userMeal.getDateTime().format(formatter),
                        new ArrayList<UserMeal>() {{
                            add(userMeal);
                        }},
                        (userMeals, userMeals2) -> {
                            userMeals.addAll(userMeals2);
                            return userMeals;
                        }
                );

                caloriesPerDayMap.merge(userMeal.getDateTime().format(formatter),
                        userMeal.getCalories(), (integer, integer2) -> {
                            return integer + integer2;
                        });
            }
        }


        for (Map.Entry<String, Integer> entry : caloriesPerDayMap.entrySet()) {

            for (UserMeal userMeal : map.get(entry.getKey())) {
                withExceedList.add(new UserMealWithExceed(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        entry.getValue() > caloriesPerDay ? true : false
                ));
            }
        }


        /*for (Map.Entry<String, List<UserMeal>> entry : map.entrySet()) {

            System.out.print(entry.getKey() + " : ");

            for (UserMeal userMeal : entry.getValue()) {
                System.out.print(userMeal.getDescription() + ","
                        + userMeal.getCalories() + " ; ");
            }
            System.out.println();
        }

        for (Map.Entry<String, Integer> entry : caloriesPerDayMap.entrySet()) {

            // System.out.println(entry.getKey() + " : " + entry.getValue());

        }

        for (UserMealWithExceed meal : list) {
            System.out.println(meal);
        }

        */

        return withExceedList;
    }
}

