package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.Item;
import com.polling.polling_project.domain.User;
import com.polling.polling_project.domain.Vote;
import com.polling.polling_project.repos.ItemRepo;
import com.polling.polling_project.repos.UserRepo;
import com.polling.polling_project.repos.VotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Controller
@RequestMapping ("/polling")
public class PollingController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private VotesRepo votesRepo;

    @GetMapping("")
    public String viewPolling (@AuthenticationPrincipal User user, Model model) {
        try {
            if (ZonedDateTime.now().minusMinutes(1).compareTo(
                    ZonedDateTime.parse(user.getLastPollTime())) < 0) {
                model.addAttribute("access_denied", "You have already voted at this minute.");
            }
        } catch (DateTimeParseException e) {}
        model.addAttribute ("items", itemRepo.findAll ());
        return "polling";
    }

    @PostMapping ("/save_votes")
    public ResponseEntity<?> saveVotes (@AuthenticationPrincipal User user,
                                        @RequestBody HashMap<?, ? extends List<? extends Integer>> attributes) {
        int summary = 0;
        for (Integer count : attributes.get("list")) {
            if (count < 0) return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
            summary += count;
        }
        if (summary > user.getVotesLimit ()) return new ResponseEntity<> (HttpStatus.BAD_REQUEST);

        votesRepo.deleteAll(votesRepo.findByAuthor(user));

        int i = 0;
        for (Item item : itemRepo.findAll ()) {
            try {
                votesRepo.save (
                    new Vote ()
                        .setAuthor (user)
                        .setItem (item)
                        .setCount (attributes.get("list").get (i++)));
            } catch (IndexOutOfBoundsException e) {
                votesRepo.save (
                    new Vote()
                        .setAuthor (user)
                        .setItem (item)
                        .setCount (0));
            }
        }

        userRepo.updateTitle(user.getId(), ZonedDateTime.now ().toString ());
        return new ResponseEntity<> (HttpStatus.OK);
    }
}
