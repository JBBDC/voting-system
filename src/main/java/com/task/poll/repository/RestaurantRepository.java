package com.task.poll.repository;

import com.task.poll.model.Restaurant;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RestaurantRepository {

    private CrudRestaurantRepository repository;

    @Autowired
    public RestaurantRepository(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (!restaurant.isNew()) {
            int id = restaurant.getId();
            Restaurant existed = repository.get(id).orElseThrow(() -> new NotFoundException("not found restaurant with id = " + id));
            if (restaurant.getMenu() == null) {
                restaurant.setMenu(existed.getMenu());
            }
            if (restaurant.getVotes() == null) {
                restaurant.setVotes(existed.getVotes());
            }
        }
        return repository.save(restaurant);
    }

    public boolean delete(int id) throws NotFoundException {
        return repository.delete(id) != 0;
    }

    public Restaurant get(int id) throws NotFoundException {
        return repository.get(id).orElseThrow(() -> new NotFoundException("not found restaurant with id = " + id));
    }

    public Restaurant getByName(String name) throws NotFoundException {
        return repository.getByName(name).orElseThrow(() -> new NotFoundException("not found restaurant with name = " + name));
    }

    public List<Restaurant> getAllToday() {
        return repository.getAllToday();
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

}
