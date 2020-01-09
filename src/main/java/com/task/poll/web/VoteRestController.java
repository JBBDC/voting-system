package com.task.poll.web;

import com.task.poll.model.Restaurant;
import com.task.poll.model.User;
import com.task.poll.model.Vote;
import com.task.poll.repository.CrudRestaurantRepository;
import com.task.poll.repository.CrudUserRepository;
import com.task.poll.repository.VoteRepository;
import com.task.poll.to.VoteTo;
import com.task.poll.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.task.poll.util.VoteUtil.*;

@RestController
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController {
    public static LocalTime EXPIRED = LocalTime.of(11, 0, 0);
    static final String REST_URL = "/rest/votes";

    private final VoteRepository voteRepository;
    private final CrudRestaurantRepository restaurantRepository;
    private final CrudUserRepository userRepository;

    @Autowired
    public VoteRestController(VoteRepository voteRepository, CrudRestaurantRepository restaurantRepository, CrudUserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VoteTo> getAll() {
        return makeTos(voteRepository.getAll(SecurityUtil.authUserId()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public VoteTo get() {
        return makeTo(voteRepository.getByDateAndUser(LocalDate.now(), SecurityUtil.authUserId()));
    }

    @Transactional
    @PostMapping(value = "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> vote(@PathVariable int restaurantId) {
        Vote existed = getExisted();
        HttpStatus status = HttpStatus.CREATED;
        Vote vote = new Vote();
        if (existed != null) {
            if (existed.getRestaurant().getId() == restaurantId) {
                return new ResponseEntity<>(makeTo(existed), status);
            }
            if (LocalTime.now().isAfter(EXPIRED)) {
                return new ResponseEntity<>(makeTo(existed), HttpStatus.CONFLICT);
            } else {
                status = HttpStatus.OK;
                vote.setId(existed.getId());
            }
        }
        vote.setUser(userRepository.getOne(SecurityUtil.authUserId()));
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        return new ResponseEntity<>(makeTo(voteRepository.save(vote)), status);
    }

    private Vote getExisted() {
        return voteRepository.getByDateAndUser(LocalDate.now(), SecurityUtil.authUserId());
    }
}
