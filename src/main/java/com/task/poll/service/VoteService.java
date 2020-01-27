package com.task.poll.service;

import com.task.poll.model.Vote;
import com.task.poll.repository.CrudVoteRepository;
import com.task.poll.util.SecurityUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.task.poll.util.DateTimeUtil.getEndIfNull;
import static com.task.poll.util.DateTimeUtil.getStartIfNull;

@Service
public class VoteService {
    private CrudVoteRepository repository;

    public VoteService(CrudVoteRepository repository) {
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

    public Vote getByDateAndUser(LocalDate date) {
        return repository.getByDateAndUserId(date, SecurityUtil.authUserId()).orElse(null);
    }

    public Vote save(Vote vote) {
        return repository.save(vote);
    }
}
