package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.Item;
import com.polling.polling_project.domain.Role;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping ("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private VotesRepo votesRepo;

    @GetMapping("/polling")
    public String viewPolling(@AuthenticationPrincipal User user, Model model) {
        if(user.getLastPollTime() != null && Timestamp.valueOf(LocalDateTime.now().minusMonths(1)).compareTo(
                user.getLastPollTime()) < 0) {
            model.addAttribute("access_denied", "You have already voted today.");
        }
        model.addAttribute("items", itemRepo.findAll());
        return "user/polling";
    }

    @PostMapping("/polling/save_votes")
    public ResponseEntity<?> saveVotes(@AuthenticationPrincipal User user,
                             @RequestBody HashMap<?, ? extends List<? extends Integer>> attributes) {
        int summary = 0;
        for(Integer count : attributes.get("list")) {
            if(count < 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            summary += count;
        }
        if(summary > user.getVotesLimit()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        votesRepo.deleteAll(votesRepo.findByAuthor(user));
        int i = 0;
        for(Item item : itemRepo.findAll()) {
            votesRepo.save(
                new Vote()
                    .setAuthor(user)
                    .setItem(item)
                    .setCount(attributes.get("list").get(i++)));
        }

        userRepo.save(user.setLastPollTime(Date.valueOf(LocalDate.now())));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("votes", votesRepo.findByAuthor(user));
        return "user/profile";
    }

    @PostMapping("/profile/save")
    public String editProfile(@AuthenticationPrincipal User user,
                              @RequestParam String password,
                              @RequestParam("new_password") String newPass,
                              @RequestParam("check_password") String checkPass,
                              RedirectAttributes redirectAttributes) {
        ArrayList<String> messages = new ArrayList<>();
        if(user.getPassword().equals(password))
            if(newPass.equals(checkPass))
                if (!newPass.equals(password)) user.setPassword(newPass);
                else messages.add("New password cannot match with old");
            else messages.add("Unmatched new and check passwords");
        else messages.add("Incorrect old password");

        userRepo.save(user);

        redirectAttributes.addFlashAttribute("messages", messages);
        return "redirect:/user/profile";
    }
}
