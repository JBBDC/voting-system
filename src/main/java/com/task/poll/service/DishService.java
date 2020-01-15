package com.task.poll.service;

import com.task.poll.model.Dish;
import com.task.poll.repository.CrudDishRepository;
import com.task.poll.repository.CrudRestaurantRepository;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.task.poll.util.DateTimeUtil.getEndIfNull;
import static com.task.poll.util.DateTimeUtil.getStartIfNull;
import static com.task.poll.util.ValidationUtil.checkId;

@Service
public class DishService {

    private CrudDishRepository repository;
    private CrudRestaurantRepository restaurantRepository;

    public DishService(CrudDishRepository repository, CrudRestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    @CacheEvict("restaurants")
    public Dish createOrUpdate(int restaurantId, Dish dish) {
        if (!dish.isNew()) {
            repository.getByIdAndRestaurant(restaurantId, dish.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Dish id=" + dish.getId() + " not consistent with restaurant id=" + restaurantId));
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return repository.save(dish);
    }

    public void update(int restaurantId, Dish dish, int id){
        checkId(dish, id);
        createOrUpdate(restaurantId, dish);
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

    @CacheEvict("restaurants")
    public void delete(int restaurantId, int id) {
        if (repository.delete(restaurantId, id) == 0) {
            throw new NotFoundException("not found dish with id = " + id + " for restaurant " + restaurantId);
        }
    }
}
