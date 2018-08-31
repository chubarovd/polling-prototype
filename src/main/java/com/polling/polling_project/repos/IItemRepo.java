package com.polling.polling_project.repos;

import com.polling.polling_project.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepo extends JpaRepository<Item, Long> {

}
