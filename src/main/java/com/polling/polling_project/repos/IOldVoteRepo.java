package com.polling.polling_project.repos;

import com.polling.polling_project.domain.OldVote;
import com.polling.polling_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOldVoteRepo extends JpaRepository<OldVote, Long> {
    Iterable<OldVote> findByAuthor(User user);
}
