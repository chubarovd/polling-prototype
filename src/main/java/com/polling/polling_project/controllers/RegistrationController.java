package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.Role;
import com.polling.polling_project.domain.User;
import com.polling.polling_project.repos.IUserRepo;
import com.polling.polling_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private IUserRepo userRepo;

    @PostMapping("/add")
    public String addUser(User user, Model model, RedirectAttributes redirectAttrs) {
        if(userRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("message", "This user is already exist");
            return "common/registration";
        }

        user.setActive(true);
        user.setVotesLimit(10);
        user.setLastPollTime(null);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        redirectAttrs.addFlashAttribute("user", user);
        return "redirect:/login";
    }
}
