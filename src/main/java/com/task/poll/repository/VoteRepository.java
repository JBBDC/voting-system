package com.task.poll.repository;

import com.task.poll.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public class VoteRepository {

    private CrudVoteRepository repository;

    @Autowired
    public VoteRepository(CrudVoteRepository repository) {
        this.repository = repository;
    }

    public Vote getByDateTime(LocalDateTime dateTime) {
        return repository.getByDateTime(dateTime);
    }

    public Set<Vote> getByUserBetweenDates(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return repository.getBetween(userId, startDateTime, endDateTime);
    }

    public Vote save(Vote vote) {
        return repository.save(vote);
    }
}
