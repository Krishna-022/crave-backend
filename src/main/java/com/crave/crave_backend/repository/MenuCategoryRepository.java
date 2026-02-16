package com.crave.crave_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crave.crave_backend.dto.out.MenuOutDto;
import com.crave.crave_backend.entity.MenuCategory;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
	
	boolean existsByRestaurantIdAndName(Long restaurantId, String name);
	
	@Query("""
		    SELECT new com.crave.crave_backend.dto.out.MenuOutDto(
		        c.id,
		        c.name,
		        c.menuItemCount
		    )
		    FROM MenuCategory c
		    WHERE c.restaurantId = :restaurantId
		    ORDER BY c.name ASC
		""")
	List<MenuOutDto> getMenu(@Param("restaurantId") Long restaurantId);
}
