package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.Item;
import com.polling.polling_project.domain.EUserRole;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController extends BaseController {
    public AdminController(IFeaturesRepo featuresRepo, IItemRepo itemRepo, IVoteRepo voteRepo, IUserRepo userRepo, FeatureService featureService, OldVoteService oldVoteService, UserService userService) {
        super(featuresRepo, itemRepo, voteRepo, userRepo, featureService, oldVoteService, userService);
    }

    @GetMapping
    public String adminMainView(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/main";
    }

    @PostMapping("/delete/{userId}")
    public String deleteItem(@PathVariable("userId") User user) {
        voteRepo.deleteAll(voteRepo.findByAuthor(user));
        userService.delete(user);
        return "redirect:/admin";
    }

    @GetMapping("/items")
    public String viewItemsEdit(Model model) {
        List<Integer> summary = new ArrayList<>();

        for(Item item : itemRepo.findAll()) {
            summary.add(
                    voteRepo.findByItem(item).parallelStream()
                            .mapToInt(Vote::getCount)
                            .sum()
            );
        }

        model.addAttribute("items", itemRepo.findAll());
        model.addAttribute("summary", summary);
        return "admin/items_edit";
    }

    @PostMapping("/items/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Item item) {
        voteRepo.deleteAll(voteRepo.findByItem(item));
        itemRepo.delete(item);
        return "redirect:/admin/items";
    }

    @PostMapping("/items/add")
    public String addItem(Item item) {
        itemRepo.save(item);
        return "redirect:/admin/items";
    }

    @GetMapping("/edit/{user}")
    public String viewUserEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", EUserRole.values());
        model.addAttribute("votes", voteRepo.findByAuthor(user));
        model.addAttribute("oldVotes", oldVoteService.findByAuthor(user));
        return "admin/user_edit";
    }

    @PostMapping("/edit")
    public String setUserInfo(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam EUserRole role,
                              @RequestParam("userId") User user,
                              RedirectAttributes redirectAttributes) {

        try {
            if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
                userService.saveUser(user, username, password, role);
            } else {
                redirectAttributes.addFlashAttribute("message", "Username and password should be not null");
                return "redirect:/admin/edit/" + user.getId();
            }
        } catch(IllegalArgumentException e) {
            return "redirect:/admin/edit/" + user.getId();
        }
        return "redirect:/admin";
    }

    @PostMapping("/edit/clear_votes")
    public String clearVotes(@RequestParam("id") User user) {
        user.setLastPollTime(null);
        userService.saveUser(user);
        Iterable<Vote> oldVotes = voteRepo.findByAuthor(user);
        oldVoteService.saveAll(oldVotes);
        voteRepo.deleteAll(oldVotes);
        return "redirect:/admin/edit/" + user.getId();
    }
}
