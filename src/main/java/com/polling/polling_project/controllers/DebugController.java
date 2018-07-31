package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.Item;
import com.polling.polling_project.domain.Vote;
import com.polling.polling_project.repos.ItemRepo;
import com.polling.polling_project.repos.UserRepo;
import com.polling.polling_project.repos.VotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.bind.SchemaOutputResolver;

@Controller
@RequestMapping("/debug")
public class DebugController {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private VotesRepo votesRepo;

    @PostMapping ("/cleardb")
    public String clearDb () {
        votesRepo.deleteAll ();
        itemRepo.deleteAll ();
        userRepo.deleteAll ();
        System.out.println ("~ DB cleared!");
        return "redirect:/login";
    }

    @PostMapping ("/add")
    public String addItem (Item item) {
        itemRepo.save (item);
        System.out.println ("~ Item: [ " + item.getContent() + " ] added");
        return "redirect:/polling";
    }

    @PostMapping ("/print_votes")
    public String printVotes () {
        System.out.println ("~ Votes: \n" + votesRepo.findAll());
        return "redirect:/polling";
    }

    @PostMapping ("/print_summary")
    public String printSummary () {
        System.out.println ("~ Summary :");
        for (Item item : itemRepo.findAll()) {
            int summary = 0;
            for (Vote vote : votesRepo.findByItem(item)) {
                summary += vote.getCount();
            }
            System.out.println("[ " + item + " ] votes: [ " + summary + " ]");
        }
        return "redirect:/polling";
    }
}
