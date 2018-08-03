package com.polling.polling_project.service;

import com.polling.polling_project.domain.User;
import com.polling.polling_project.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
