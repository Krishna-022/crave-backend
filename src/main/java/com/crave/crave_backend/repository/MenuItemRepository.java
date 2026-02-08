package com.crave.crave_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crave.crave_backend.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

}
