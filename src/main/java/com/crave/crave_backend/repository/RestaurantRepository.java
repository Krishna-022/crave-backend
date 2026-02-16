package com.crave.crave_backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.crave.crave_backend.dto.out.RestaurantListViewOutDTO;
import com.crave.crave_backend.entity.Restaurant;

import jakarta.persistence.LockModeType;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT r FROM Restaurant r WHERE r.id = :restaurantId")
	Optional<Restaurant> findByIdForUpdate(@Param("restaurantId") Long restaurantId);
	
	@Query("""
	        SELECT new com.crave.crave_backend.dto.out.RestaurantListViewOutDTO(
	            i.id,
	            i.name,
	            i.cityName
	        )
	        FROM Restaurant i
	        WHERE i.userId = :userId
	        ORDER BY i.id ASC
	    """)
	List<RestaurantListViewOutDTO> getRestaurantsByUserId(@Param("userId") Long userId);
		
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