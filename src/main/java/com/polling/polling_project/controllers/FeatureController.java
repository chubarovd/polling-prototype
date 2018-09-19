package com.polling.polling_project.controllers;

import com.polling.polling_project.domain.Feature;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/feature")
public class FeatureController extends BaseController {
    public FeatureController(IFeaturesRepo featuresRepo, IItemRepo itemRepo, IVoteRepo voteRepo, IUserRepo userRepo, FeatureService featureService, OldVoteService oldVoteService, UserService userService) {
        super(featuresRepo, itemRepo, voteRepo, userRepo, featureService, oldVoteService, userService);
    }

    @GetMapping
    @ResponseBody
    public List<Feature> getFeatures() {
        return featuresRepo.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFeature(Feature feature) {
        featuresRepo.save(feature);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
