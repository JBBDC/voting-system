package com.task.poll.repository;

import com.task.poll.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer>, JpaSpecificationExecutor<Restaurant> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE function('FORMATDATETIME', m.created, 'yyyy-MM-dd') = CURRENT_DATE")
    List<Restaurant> getAllToday();

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu m")
    List<Restaurant> getAll();

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE r.id=:id ")
    Optional<Restaurant> get(@Param("id") int id);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE r.name=:name")
    Optional<Restaurant> getByName(@Param("name") String name);
}
