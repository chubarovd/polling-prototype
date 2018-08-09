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

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

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
        if (user.getLastPollTime() != null && Timestamp.valueOf(LocalDateTime.now().minusMonths(1)).compareTo(
                user.getLastPollTime()) < 0) {
            model.addAttribute("access_denied", "You have already voted today.");
        }
        model.addAttribute ("items", itemRepo.findAll ());
        return "user/polling";
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
            votesRepo.save (
                new Vote ()
                    .setAuthor (user)
                    .setItem (item)
                    .setCount (attributes.get("list").get (i++)));
        }

        userRepo.save(user.setLastPollTime(Date.valueOf(LocalDate.now())));
        return new ResponseEntity<> (HttpStatus.OK);
    }
}
