package com.task.poll.repository;

import com.task.poll.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.task.poll.util.DateTimeUtil.*;

@Repository
public class VoteRepository {

    private CrudVoteRepository repository;

    @Autowired
    public VoteRepository(CrudVoteRepository repository) {
        this.repository = repository;
    }

    public List<Vote> getByUserBetweenDates(int userId, @Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return repository.getAllByUser(userId);
        }
        return repository.getByUserBetweenDates(userId, getStartIfNull(startDate), getEndIfNull(endDate));
    }

    public List<Vote> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return repository.findAll();
        }
        return repository.getBetweenDates(getStartIfNull(startDate), getEndIfNull(endDate));
    }

    public List<Vote> getAllByUser(int userId) {
        return repository.getAllByUser(userId);
    }

    public List<Vote> getAll() {
        return repository.findAll();
    }

    public Vote getByDateAndUser(LocalDate date, int userId) {
        return repository.getByDateAndUserId(date, userId).orElse(null);
    }

    public Vote save(Vote vote) {
        return repository.save(vote);
    }
}
