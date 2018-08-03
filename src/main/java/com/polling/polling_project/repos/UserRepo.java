package com.polling.polling_project.repos;

import com.polling.polling_project.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.lastPollTime=:date WHERE u.id=:id")
    void updateTitle (@Param("id") Long id, @Param("date") String date);
}
