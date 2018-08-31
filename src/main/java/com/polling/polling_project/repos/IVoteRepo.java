package com.polling.polling_project.repos;

import com.polling.polling_project.domain.Item;
import com.polling.polling_project.domain.User;
import com.polling.polling_project.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface IVoteRepo extends JpaRepository<Vote, Long> {
    List<Vote> findByAuthor(User user);
    List<Vote> findByItem(Item item);
}
