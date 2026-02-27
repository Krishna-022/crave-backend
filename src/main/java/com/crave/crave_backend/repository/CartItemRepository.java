package com.crave.crave_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crave.crave_backend.dto.out.CartItemViewOutDto;
import com.crave.crave_backend.entity.CartItem;

import jakarta.persistence.LockModeType;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("""
			    SELECT new com.crave.crave_backend.dto.out.CartItemViewOutDto(
			        c.menuItemName,
			        c.quantity,
			        c.unitPrice
			    )
			    FROM CartItem c
			    WHERE c.cartId = :cartId
			""")
	List<CartItemViewOutDto> findCartItemsByCartId(@Param("cartId") Long cartId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("""
			SELECT c
			FROM CartItem c
			WHERE c.cartId = :cartId
			""")
	List<CartItem> findByCartIdForUpdate(@Param("cartId")Long cartId);

	Optional<CartItem> findByCartIdAndMenuItemId(Long cartId, Long menuItemId);

	int deleteByCartIdAndMenuItemId(Long cartId, Long menuItemId);
	
	int deleteByCartId(Long cartId);
}
