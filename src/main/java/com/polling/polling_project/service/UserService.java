package com.polling.polling_project.service;

import com.polling.polling_project.domain.Role;
import com.polling.polling_project.domain.User;
import com.polling.polling_project.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername (String username) {
        User user = userRepo.findByUsername (username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException ("User not found");
        }
    }

    public void delete (User user) {
        userRepo.delete(user);
    }

    public Iterable<User> findAll () {
        return userRepo.findAll();
    }

    public void saveUser (User user) {
        userRepo.save(user);
    }

    public void saveUser (User user, String username, String password, Role role) throws IllegalArgumentException {
        user.getRoles().clear();
        user.getRoles().add(role);

        userRepo.save(
                user.setUsername(username)
                        .setPassword(password));
    }
}
