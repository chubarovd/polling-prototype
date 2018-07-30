package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.Item;
import com.polling.polling_project.repos.ItemRepo;
import com.polling.polling_project.repos.UserRepo;
import com.polling.polling_project.repos.VotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/debug")
public class DebugController {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private VotesRepo votesRepo;

    @PostMapping("/cleardb")
    public String clearDb () {
        votesRepo.deleteAll ();
        itemRepo.deleteAll ();
        userRepo.deleteAll ();
        return "redirect:/login";
    }

    @PostMapping ("/add")
    public String addItem (Item item) {
        itemRepo.save (item);
        return "redirect:/polling";
    }

    @PostMapping("/print_votes")
    public ResponseEntity<?> printVotes () {
        System.out.println (votesRepo.findAll());
        return new ResponseEntity<> (HttpStatus.OK);
    }
}
