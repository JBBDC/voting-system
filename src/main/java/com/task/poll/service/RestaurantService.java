package com.task.poll.service;

import com.task.poll.model.Restaurant;
import com.task.poll.repository.CrudRestaurantRepository;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

import static com.task.poll.util.ValidationUtil.checkId;

@Service
public class RestaurantService {

    final CrudRestaurantRepository repository;

    public RestaurantService(CrudRestaurantRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @CachePut(value = "restaurants", key = "#restaurant.name")
    public Restaurant createOrUpdate(Restaurant restaurant) {
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

    public void update(Restaurant restaurant, int id) {
        checkId(restaurant, id);
        createOrUpdate(restaurant);
    }

    @CacheEvict("restaurants")
    public void delete(int id) throws NotFoundException {
        if (repository.delete(id) == 0) {
            throw new NotFoundException("not found restaurant with id = " + id);
        }
    }

    public Restaurant get(int id) throws NotFoundException {
        return repository.get(id).orElseThrow(() -> new NotFoundException("not found restaurant with id = " + id));
    }

    public Restaurant getByName(String name) throws NotFoundException {
        return repository.getByName(name).orElseThrow(() -> new NotFoundException("not found restaurant with name = " + name));
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllToday() {
        return repository.getAllToday();
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }


}
