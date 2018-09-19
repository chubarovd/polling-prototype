package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.EUserRole;
import com.polling.polling_project.domain.User;
import com.polling.polling_project.repos.IFeaturesRepo;
import com.polling.polling_project.repos.IItemRepo;
import com.polling.polling_project.repos.IUserRepo;
import com.polling.polling_project.repos.IVoteRepo;
import com.polling.polling_project.service.FeatureService;
import com.polling.polling_project.service.OldVoteService;
import com.polling.polling_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController extends BaseController {
    public RegistrationController(IFeaturesRepo featuresRepo, IItemRepo itemRepo, IVoteRepo voteRepo, IUserRepo userRepo, FeatureService featureService, OldVoteService oldVoteService, UserService userService) {
        super(featuresRepo, itemRepo, voteRepo, userRepo, featureService, oldVoteService, userService);
    }

    @PostMapping("/add")
    public String addUser(User user, Model model, RedirectAttributes redirectAttrs) {
        if(userRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("message", "This user is already exist");
            return "common/registration";
        }

        user.setActive(true);
        user.setVotesLimit(10);
        user.setLastPollTime(null);
        user.setRoles(Collections.singleton(EUserRole.USER));
        userRepo.save(user);

        redirectAttrs.addFlashAttribute("user", user);
        return "redirect:/login";
    }
}
