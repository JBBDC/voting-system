package com.task.poll.web.admin;

import com.task.poll.service.VoteService;
import com.task.poll.to.VoteTo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.task.poll.util.VoteUtil.makeTos;

@RestController
@RequestMapping(AdminVoteRestController.REST_URL)
public class AdminVoteRestController {

    static final String REST_URL = "/api/v1/admin/votes";

    private final VoteService voteService;

    public AdminVoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VoteTo> getBetween(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return makeTos(voteService.getBetweenDates(startDate, endDate));
    }
}
