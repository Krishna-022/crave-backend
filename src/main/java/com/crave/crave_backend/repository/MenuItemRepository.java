package com.crave.crave_backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.crave.crave_backend.dto.out.MenuItemListViewOutDto;
import com.crave.crave_backend.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
	
	boolean existsByMenuCategoryIdAndNameIgnoreCase(Long menuCategoryId, String name);
	
	@Query("SELECT r.image FROM MenuItem r WHERE r.id = :menuItemId")
    byte[] findMenuItemImageById(@Param("menuItemId") Long menuItemId);
	
	@Query("""
		    SELECT new com.crave.crave_backend.dto.out.MenuItemListViewOutDto(
		        i.id,
		        i.name,
		        i.description,
		        i.price
		    )
		    FROM MenuItem i
		    WHERE i.id = :menuItemId
		""")
	Optional<MenuItemListViewOutDto> getItemById(@Param("menuItemId") Long menuItemId);

	@Query("""
			    SELECT new com.crave.crave_backend.dto.out.MenuItemListViewOutDto(
			        i.id,
			        i.name,
			        i.description,
			        i.price
			    )
			    FROM MenuItem i
			    WHERE i.menuCategoryId = :menuCategoryId
			    ORDER BY i.name ASC
			""")
	List<MenuItemListViewOutDto> findByCategory(@Param("menuCategoryId") Long menuCategoryId);
}
