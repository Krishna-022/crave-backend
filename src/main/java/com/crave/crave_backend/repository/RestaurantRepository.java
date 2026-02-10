package com.crave.crave_backend.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.crave.crave_backend.dto.out.RestaurantListViewOutDTO;
import com.crave.crave_backend.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
		
	boolean existsByUserId(Long userId);
	
	List<Restaurant> findByContactNumberOrEmailOrName(String contactNumber, String email, String name);
	
	@Query("SELECT r.image FROM Restaurant r WHERE r.id = :id")
    byte[] findRestaurantImageById(@Param("id") Long id);
	
	 @Query("""
		        SELECT new com.crave.crave_backend.dto.out.RestaurantListViewOutDTO(
	 		    	i.id,
		            i.name,
		            i.cityName
		        )
		        FROM Restaurant i
		        WHERE i.id > :cursor
		        ORDER BY i.id ASC
		    """)
	List<RestaurantListViewOutDTO> getRestaurants(@Param("cursor") Long cursor, Pageable pageable);
}