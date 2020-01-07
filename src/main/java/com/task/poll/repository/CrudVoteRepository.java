package com.task.poll.repository;

import com.task.poll.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    Vote getByDateTime(LocalDateTime dateTime);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId " +
            "AND v.dateTime >= :startDate AND v.dateTime < :endDate ORDER BY v.dateTime DESC")
    Set<Vote> getBetween(@Param("userId") int userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
