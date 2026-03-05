package com.crave.crave_backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.crave.crave_backend.dto.out.CartListViewOutDto;
import com.crave.crave_backend.entity.Cart;
import jakarta.persistence.LockModeType;

public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("""
			    SELECT new com.crave.crave_backend.dto.out.CartListViewOutDto(
			        c.id,
			        c.itemCount,
			        r.name
			    )
			    FROM Cart c
			    JOIN Restaurant r ON c.restaurantId = r.id
			    WHERE c.userId = :userId
			""")
	List<CartListViewOutDto> findCartListByUserId(@Param("userId") Long userId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("""
			SELECT c
			FROM Cart c
			WHERE c.userId = :userId
			""")
	List<Cart> findAllByUserIdForUpdate(@Param("userId") Long userId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("""
			SELECT c
			FROM Cart c
			WHERE c.id = :cartId
			""")
	Optional<Cart> findAllByIdForUpdate(@Param("cartId") Long cartId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("""
			SELECT c
			FROM Cart c
			WHERE c.userId = :userId
			AND c.restaurantId = :restaurantId
			""")
	Optional<Cart> findByUserIdAndRestaurantIdForUpdate(@Param("userId") Long userId,
			@Param("restaurantId") Long restaurantId);

	Optional<Cart> findByUserIdAndRestaurantId(Long userId, Long restaurantId);

	Integer deleteByUserIdAndRestaurantId(Long userId, Long restaurantId);

	void deleteById(Long id);
}
