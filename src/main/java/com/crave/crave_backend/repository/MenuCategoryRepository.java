package com.crave.crave_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crave.crave_backend.dto.out.MenuOutDto;
import com.crave.crave_backend.entity.MenuCategory;

import jakarta.persistence.LockModeType;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT c FROM MenuCategory c WHERE c.id = :categoryId")
	Optional<MenuCategory> findByIdForUpdate(@Param("categoryId") Long categoryId);
	
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
