package com.polling.polling_project.repos;

import com.polling.polling_project.domain.User;
import com.polling.polling_project.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotesRepo extends JpaRepository<Vote, Long> {
     List<Vote> findByAuthor(User user);
     //List<Vote> firdByItem (Long item_id);
}
