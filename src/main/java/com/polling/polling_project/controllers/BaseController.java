package com.polling.polling_project.controllers;

import com.polling.polling_project.repos.IFeaturesRepo;
import com.polling.polling_project.repos.IItemRepo;
import com.polling.polling_project.repos.IUserRepo;
import com.polling.polling_project.repos.IVoteRepo;
import com.polling.polling_project.service.FeatureService;
import com.polling.polling_project.service.OldVoteService;
import com.polling.polling_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
abstract class BaseController {
    // autowire repos
    final IFeaturesRepo featuresRepo;
    final IItemRepo itemRepo;
    final IVoteRepo voteRepo;
    final IUserRepo userRepo;

    // autowire services
    final FeatureService featureService;
    final OldVoteService oldVoteService;
    final UserService userService;

    @Autowired
    public BaseController(
            IFeaturesRepo featuresRepo,
            IItemRepo itemRepo,
            IVoteRepo voteRepo,
            IUserRepo userRepo,
            FeatureService featureService,
            OldVoteService oldVoteService,
            UserService userService) {
        this.featuresRepo = featuresRepo;
        this.itemRepo = itemRepo;
        this.voteRepo = voteRepo;
        this.userRepo = userRepo;
        this.featureService = featureService;
        this.oldVoteService = oldVoteService;
        this.userService = userService;
    }
}
