package com.polling.polling_project.controllers;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.polling.polling_project.domain.Item;
import com.polling.polling_project.domain.User;
import com.polling.polling_project.domain.Vote;
import com.polling.polling_project.repos.IFeaturesRepo;
import com.polling.polling_project.repos.IItemRepo;
import com.polling.polling_project.repos.IUserRepo;
import com.polling.polling_project.repos.IVoteRepo;
import com.polling.polling_project.service.FeatureService;
import com.polling.polling_project.service.OldVoteService;
import com.polling.polling_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController extends BaseController {
    public UserController(IFeaturesRepo featuresRepo, IItemRepo itemRepo, IVoteRepo voteRepo, IUserRepo userRepo, FeatureService featureService, OldVoteService oldVoteService, UserService userService) {
        super(featuresRepo, itemRepo, voteRepo, userRepo, featureService, oldVoteService, userService);
    }

    @GetMapping("/polling")
    public String viewPolling(@AuthenticationPrincipal User user, Model model) {
        if(user.isAbleToPoll()) {
            model.addAttribute("items", itemRepo.findAll());
        } else {
            model.addAttribute("access_denied", "You have already voted at this month.");
        }
        return "user/polling";
    }

    @PostMapping("/polling/save_votes")
    public ResponseEntity<?> saveVotes(@AuthenticationPrincipal User user,
                                       @RequestBody HashMap<?, ? extends List<? extends Integer>> attributes) {
        if(!user.isAbleToPoll()) {
            return new ResponseEntity<>("You have already poll!", HttpStatus.BAD_REQUEST);
        }
        if(!user.isVotesValid(attributes.get("votesList"))) {
            return new ResponseEntity<>("Invalid votes!", HttpStatus.BAD_REQUEST);
        }

        Iterable<Vote> oldVotes = voteRepo.findByAuthor(user);
        oldVoteService.saveAll(oldVotes);
        voteRepo.deleteAll(oldVotes);
        int i = 0;
        for(Item item : itemRepo.findAll()) {
            voteRepo.save(
                    Vote.builder()
                            .author(user)
                            .item(item)
                            .count(attributes.get("votesList").get(i++))
                        .build()
            );
        }
        user.setLastPollTime(Date.valueOf(LocalDate.now()));
        userRepo.save(user);
        return new ResponseEntity<>("Successfully saved!", HttpStatus.OK);
    }

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("votes", voteRepo.findByAuthor(user));
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
            if(newPass.equals(checkPass)) {
                if(!newPass.equals(password)) {
                    user.setPassword(newPass);
                    userRepo.save(user);
                } else {
                    messages.add("New password cannot match with old");
                }
            } else {
                messages.add("Unmatched new and check passwords");
            }
        else {
            messages.add("Incorrect old password");
        }

        redirectAttributes.addFlashAttribute("messages", messages);
        return "redirect:/user/profile";
    }
}
