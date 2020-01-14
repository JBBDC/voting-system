package com.task.poll.repository;

import com.task.poll.model.Dish;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.task.poll.util.DateTimeUtil.getEndIfNull;
import static com.task.poll.util.DateTimeUtil.getStartIfNull;

@Repository
public class DishRepository {

    private CrudDishRepository repository;
    private CrudRestaurantRepository restaurantRepository;

    @Autowired
    public DishRepository(CrudDishRepository repository, CrudRestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Dish save(int restaurantId, Dish dish) {
        if (!dish.isNew()) {
            repository.getByIdAndRestaurant(restaurantId, dish.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Dish id=" + dish.getId() + " not consistent with restaurant id=" + restaurantId));
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return repository.save(dish);
    }

    public List<Dish> getAll(int restId) {
        return repository.getAllByRestaurantId(restId);
    }

    public List<Dish> getByRestaurantBetweenDates(int restaurantId, @Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return repository.getAllByRestaurantId(restaurantId);
        }
        return repository.getBetweenDates(restaurantId, getStartIfNull(startDate), getEndIfNull(endDate));
    }

    public Dish get(int restaurantId, int id) {
        return repository.getByIdAndRestaurant(restaurantId, id).orElseThrow(() -> new NotFoundException("not found dish with id = " + id + "and restaurantId = " + restaurantId));
    }

    public boolean delete(int restaurantId, int id) {
        return repository.delete(restaurantId, id) != 0;
    }
}
