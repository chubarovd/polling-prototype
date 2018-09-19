package com.polling.polling_project.service;

import com.polling.polling_project.domain.OldVote;
import com.polling.polling_project.domain.User;
import com.polling.polling_project.domain.Vote;
import com.polling.polling_project.repos.IOldVoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OldVoteService {
    @Autowired
    private IOldVoteRepo oldVoteRepo;

    public void saveAll(Iterable<Vote> old) {
        old.forEach(this::save);
    }

    public void save(Vote old) {
        oldVoteRepo.save(
                OldVote.builder()
                        .author(old.getAuthor())
                        .content(old.getItem().getContent())
                        .count(old.getCount())
                        .build());
    }

    public Iterable<OldVote> findByAuthor(User user) {
        return oldVoteRepo.findByAuthor(user);
    }
}
