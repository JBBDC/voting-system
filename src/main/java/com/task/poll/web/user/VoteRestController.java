package com.task.poll.web.user;

import com.task.poll.model.Restaurant;
import com.task.poll.model.Vote;
import com.task.poll.repository.CrudUserRepository;
import com.task.poll.service.RestaurantService;
import com.task.poll.service.VoteService;
import com.task.poll.to.VoteTo;
import com.task.poll.util.SecurityUtil;
import com.task.poll.util.exception.TimeExpiredException;
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
    static final String REST_URL = "/api/v1/votes";

    private final VoteService voteService;
    private final RestaurantService restaurantService;

    @Autowired
    public VoteRestController(VoteService voteService, RestaurantService restaurantService) {
        this.voteService = voteService;
        this.restaurantService = restaurantService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public VoteTo getToday() {
        return makeTo(checkNotFound(voteService.getByDateAndUser(LocalDate.now(), SecurityUtil.authUserId()), "Not found Vote for today"));
    }

    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> vote(@RequestParam int restaurantId) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        Vote existed = getExisted();
        HttpStatus status = HttpStatus.CREATED;
        if (existed != null) {
            if (LocalTime.now().isAfter(EXPIRED)) {
                throw new TimeExpiredException("Vote change are not allowed after "+EXPIRED.toString());
            } else {
                return new ResponseEntity<>(makeTo(voteService.update(restaurant, existed.getId(), SecurityUtil.authUserId())), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(makeTo(voteService.create(restaurant, SecurityUtil.authUserId())), status);
    }

    private Vote getExisted() {
        return voteService.getByDateAndUser(LocalDate.now(), SecurityUtil.authUserId());
    }
}
