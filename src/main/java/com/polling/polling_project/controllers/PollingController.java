package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.Item;
import com.polling.polling_project.domain.User;
import com.polling.polling_project.domain.Vote;
import com.polling.polling_project.repos.ItemRepo;
import com.polling.polling_project.repos.VotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping ("/polling")
public class PollingController {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private VotesRepo votesRepo;

    @PostMapping ("/save_votes")
    public ResponseEntity<?> saveVotes (@AuthenticationPrincipal User user,
                                        @RequestBody HashMap<?, ? extends List<? extends Integer>> list) {
        /*-- votes validation ? --*/

        votesRepo.deleteAll(votesRepo.findByAuthor(user));

        int i = 0;
        for (Item item : itemRepo.findAll ()) {
            try {
                votesRepo.save (
                    new Vote ()
                        .setAuthor (user)
                        .setItem (item)
                        .setCount (list.get("list").get (i++)));
            } catch (IndexOutOfBoundsException e) {
                votesRepo.save (
                    new Vote()
                        .setAuthor (user)
                        .setItem (item)
                        .setCount (0));
            }
        }

        System.out.println (list.get ("list"));
        return new ResponseEntity<> (HttpStatus.OK);
    }
}
