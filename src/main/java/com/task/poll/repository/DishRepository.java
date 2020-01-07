package com.task.poll.repository;

import com.task.poll.model.Dish;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DishRepository {

    private CrudDishRepository repository;

    @Autowired
    public DishRepository(CrudDishRepository repository) {
        this.repository = repository;
    }

    public Dish save(Dish dish) {

        return repository.save(dish);
    }

    public List<Dish> getAll(int restId, LocalDate created) {
        return repository.getAllByRestaurantIdAndCreated(restId, created);
    }

    public Dish get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("id = " + id));
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }
}
