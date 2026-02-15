package com.crave.crave_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crave.crave_backend.entity.MenuCategory;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

}
