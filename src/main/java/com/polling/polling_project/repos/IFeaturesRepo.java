package com.polling.polling_project.repos;

import com.polling.polling_project.domain.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFeaturesRepo extends JpaRepository<Feature, Long> {
}
