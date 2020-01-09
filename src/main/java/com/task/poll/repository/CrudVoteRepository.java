package com.task.poll.repository;

import com.task.poll.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> getByDateAndUserId(LocalDate date, int userId);

    @Query("SELECT DISTINCT v FROM Vote v WHERE v.user.id=:userId " +
            "AND v.date >= :startDate AND v.date < :endDate ORDER BY v.date DESC")
    Set<Vote> getBetween(@Param("userId") int userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT DISTINCT v FROM Vote v WHERE v.user.id=:userId")
    Set<Vote> getAllByUserId(@Param("userId")int userId);
}
