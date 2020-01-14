package com.task.poll.repository;

import com.task.poll.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id =:restaurantId")
    int delete(@Param("restaurantId") int restaurantId, @Param("id") int id);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.id =:id")
    Optional<Dish> getByIdAndRestaurant(@Param("restaurantId") int restaurantId, @Param("id") int id);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:id")
    List<Dish> getAllByRestaurantId(int id);

    @Query("SELECT DISTINCT d FROM Dish d WHERE d.created >= :startDate AND d.created <= :endDate AND d.restaurant.id =:restId")
    List<Dish> getBetweenDates(@Param("restId") int restId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
