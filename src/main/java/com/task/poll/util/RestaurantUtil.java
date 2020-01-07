package com.task.poll.util;

import com.task.poll.model.Restaurant;
import com.task.poll.model.Vote;
import com.task.poll.repository.VoteRepository;
import com.task.poll.to.DishTo;
import com.task.poll.to.RestaurantTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RestaurantUtil {

    private static final LocalDateTime TODAY_START = LocalDate.now().atTime(LocalTime.MIN);
    private static final LocalDateTime TODAY_END = LocalDate.now().atTime(LocalTime.MAX);

    static VoteRepository voteRepository;

    @Autowired
    private VoteRepository wiredRepository;

    @PostConstruct
    private void init() {
        voteRepository = this.wiredRepository;
    }

    public static List<RestaurantTo> makeTos(List<Restaurant> restaurants) {
        return makeTos(restaurants, SecurityUtil.authUserId());
    }

    public static List<RestaurantTo> makeTos(Restaurant... restaurants) {
        return makeTos(List.of(restaurants));
    }

    public static List<RestaurantTo> makeTos(List<Restaurant> restaurants, int userId) {
        List<RestaurantTo> result = new ArrayList<>();
        if (restaurants != null) {
            Set<Vote> votes = voteRepository.getByUserBetweenDates(userId, TODAY_START, TODAY_END);
            for (Restaurant restaurant : restaurants) {
                int id = restaurant.getId();
                List<DishTo> menu = DishUtil.makeTos(restaurant);
                result.add(new RestaurantTo(id, restaurant.getName(), menu, isVotedToday(id, votes)));
            }
        }
        return result;
    }

    public static RestaurantTo makeTo(Restaurant restaurant) {
        return makeTo(restaurant, SecurityUtil.authUserId());
    }

    public static RestaurantTo makeTo(Restaurant restaurant, int userId) {
        if (restaurant != null) {
            Set<Vote> votes = voteRepository.getByUserBetweenDates(userId, TODAY_START, TODAY_END);
            int id = restaurant.getId();
            return new RestaurantTo(id, restaurant.getName(), DishUtil.makeTos(restaurant), isVotedToday(id, votes));
        }
        return null;
    }

    public static boolean isVotedToday(int restaurantId, Set<Vote> votes) {
        return votes != null && votes.size() > 0 && votes.stream().anyMatch(vote -> vote.getRestaurant().getId().equals(restaurantId));
    }
}
