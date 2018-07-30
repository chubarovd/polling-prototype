package com.polling.polling_project.controllers;

import com.polling.polling_project.repos.ItemRepo;
import com.polling.polling_project.repos.UserRepo;
import com.polling.polling_project.repos.VotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @Autowired
    private ItemRepo itemRepo;

    @Value("${polling.items_url}")
    private String listURL;

    @GetMapping (value = { "/", "index", "/polling" })
    public String viewPolling (Model model) {
        model.addAttribute ("items", itemRepo.findAll ());
        return "polling";
    }

    @GetMapping ("/get_list")
    public String getList () {
        return null;
    }
}
