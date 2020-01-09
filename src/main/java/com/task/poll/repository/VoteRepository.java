package com.task.poll.repository;

import com.task.poll.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Repository
public class VoteRepository {

    private CrudVoteRepository repository;

    @Autowired
    public VoteRepository(CrudVoteRepository repository) {
        this.repository = repository;
    }

    public Set<Vote> getByUserBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getBetween(userId, startDate, endDate);
    }

    public Set<Vote> getAll(int userId) {
        return repository.getAllByUserId(userId);
    }

    public Vote getByDateAndUser(LocalDate date, int userId) {
        return repository.getByDateAndUserId(date, userId).orElse(null);
    }

    public Vote save(Vote vote) {
        return repository.save(vote);
    }
}
