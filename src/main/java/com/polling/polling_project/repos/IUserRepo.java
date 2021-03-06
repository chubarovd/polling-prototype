package com.polling.polling_project.repos;

import com.polling.polling_project.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
