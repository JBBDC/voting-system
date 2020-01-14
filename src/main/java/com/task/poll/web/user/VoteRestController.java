package com.task.poll.web.user;

import com.task.poll.model.Restaurant;
import com.task.poll.model.Vote;
import com.task.poll.repository.CrudUserRepository;
import com.task.poll.repository.RestaurantRepository;
import com.task.poll.repository.VoteRepository;
import com.task.poll.to.VoteTo;
import com.task.poll.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.task.poll.util.ValidationUtil.*;
import static com.task.poll.util.VoteUtil.*;

@RestController
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController {
    public static LocalTime EXPIRED = LocalTime.of(11, 0, 0);
    static final String REST_URL = "/api/v1/";

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final CrudUserRepository userRepository;

    @Autowired
    public VoteRestController(VoteRepository voteRepository, RestaurantRepository restaurantRepository, CrudUserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/votes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VoteTo> getBetween(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return makeTos(voteRepository.getByUserBetweenDates(SecurityUtil.authUserId(), startDate, endDate));
    }

    @GetMapping(value = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public VoteTo getByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return makeTo(checkNotFound(voteRepository.getByDateAndUser(date, SecurityUtil.authUserId()), "No vote for date " + date));
    }

    @Transactional
    @PostMapping(value = "vote/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> vote(@PathVariable int restaurantId) {
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        Vote vote = new Vote();
        Vote existed = getExisted();
        HttpStatus status = HttpStatus.CREATED;
        if (existed != null) {
            if (existed.getRestaurant().getId().equals(restaurant.getId()) || LocalTime.now().isAfter(EXPIRED)) {
                return new ResponseEntity<>(makeTo(existed), HttpStatus.CONFLICT);
            } else {
                status = HttpStatus.OK;
                vote.setId(existed.getId());
            }
        }
        vote.setRestaurant(restaurant);
        vote.setUser(userRepository.getOne(SecurityUtil.authUserId()));
        return new ResponseEntity<>(makeTo(voteRepository.save(vote)), status);
    }

    private Vote getExisted() {
        return voteRepository.getByDateAndUser(LocalDate.now(), SecurityUtil.authUserId());
    }
}