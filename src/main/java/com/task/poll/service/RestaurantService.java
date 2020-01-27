package com.task.poll.service;

import com.task.poll.model.Restaurant;
import com.task.poll.repository.CrudRestaurantRepository;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {

    final CrudRestaurantRepository repository;

    public RestaurantService(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @CacheEvict("restaurants")
    public Restaurant createOrUpdate(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    @CacheEvict("restaurants")
    public void delete(int id) throws NotFoundException {
        if (repository.delete(id) == 0) {
            throw new NotFoundException("Not found restaurant with id = " + id);
        }
    }

    public Restaurant get(int id) throws NotFoundException {
        return repository.get(id).orElseThrow(() -> new NotFoundException("Not found restaurant with id = " + id));
    }

    public Restaurant getByName(String name) throws NotFoundException {
        return repository.getByName(name).orElseThrow(() -> new NotFoundException("Not found restaurant with name = " + name));
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllToday() {
        return repository.getAllToday();
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }
}
