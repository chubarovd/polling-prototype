package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.Role;
import com.polling.polling_project.domain.User;
import com.polling.polling_project.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/add")
    public String addUser(User user, Model model, RedirectAttributes redirectAttrs) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("message", "This user is already exist");
            return "common/registration";
        }

        userRepo.save(
            user
                .setActive(true)
                .setVotesLimit(10)
                .setLastPollTime(Date.valueOf(LocalDate.now().minusMonths(2l)))
                .setRoles(Collections.singleton(Role.USER)));

        redirectAttrs.addFlashAttribute("user", user);
        return "redirect:/login";
    }
}
