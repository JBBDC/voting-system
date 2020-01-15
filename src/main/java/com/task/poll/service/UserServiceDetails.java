package com.task.poll.service;

import com.task.poll.AuthorizedUser;
import com.task.poll.model.User;
import com.task.poll.repository.CrudUserRepository;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceDetails implements UserDetailsService {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository repository;

    public UserServiceDetails(CrudUserRepository repository) {
        this.repository = repository;
    }

    public User save(User user) {
        return repository.save(user);
    }

    public boolean delete(int id) {
        return repository.delete(id) != 0;
    }

    public User get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("not found user with id = " + id));
    }

    public User getByEmail(String email) {
        return repository.getByEmail(email);
    }

    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
