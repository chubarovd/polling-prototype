package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.Item;
import com.polling.polling_project.domain.Role;
import com.polling.polling_project.domain.User;
import com.polling.polling_project.domain.Vote;
import com.polling.polling_project.repos.ItemRepo;
import com.polling.polling_project.repos.UserRepo;
import com.polling.polling_project.repos.VotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private VotesRepo votesRepo;

    @GetMapping
    public String adminMainView(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "admin/main";
    }

    @PostMapping("/delete/{userId}")
    public String deleteItem(@PathVariable("userId") User user) {
        votesRepo.deleteAll(votesRepo.findByAuthor(user));
        userRepo.delete(user);
        return "redirect:/admin";
    }

    @GetMapping("/items")
    public String editItems(Model model) {
        List<Integer> summary = new ArrayList();
        for (Item item : itemRepo.findAll()) {
            int temp = 0;
            for (Vote vote : votesRepo.findByItem(item)) {
                temp += vote.getCount();
            }
            summary.add(temp);
        }

        model.addAttribute("items", itemRepo.findAll());
        model.addAttribute("summary", summary);
        return "admin/items_edit";
    }

    @PostMapping("/items/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Item item) {
        votesRepo.deleteAll(votesRepo.findByItem(item));
        itemRepo.delete(item);
        return "redirect:/admin/items";
    }

    @PostMapping("/items/add")
    public String addItem(Item item) {
        itemRepo.save(item);
        return "redirect:/admin/items";
    }

    @GetMapping("/edit/{user}")
    public String editUserInfo(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("votes", votesRepo.findByAuthor(user));
        return "admin/user_edit";
    }

    @PostMapping("/edit")
    public String setUserInfo(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam Role role,
                              /*@RequestParam Boolean active,
                              @RequestParam Boolean resetLastTime,*/
                              @RequestParam("userId") User user) {
        user.getRoles().clear();
        try {
            user.getRoles().add(role);
        } catch(IllegalArgumentException e) {
            return "redirect:/admin/edit/" + user.getId();
        }
        /*if (resetLastTime) user.setLastPollTime (Date.valueOf (LocalDate.now ().minusMonths (2l)));*/
        userRepo.save(
                user.setUsername(username)
                    .setPassword(password)
                    /*.setActive(active)*/);

        return "redirect:/admin";
    }

    @PostMapping("/edit/clear_votes")
    public String clearVotes(@RequestParam("id") User user) {
        user.setLastPollTime(Date.valueOf(LocalDate.now().minusMonths(2l)));
        userRepo.save(user);
        votesRepo.deleteAll(votesRepo.findByAuthor(user));
        return "redirect:/admin/edit/" + user.getId();
    }
}
