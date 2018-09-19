package com.polling.polling_project.service;

import com.polling.polling_project.repos.IFeaturesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureService {
    @Autowired
    private IFeaturesRepo featuresRepo;


}
