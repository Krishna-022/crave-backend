package com.crave.crave_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crave.crave_backend.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	Optional<CartItem> findByCartIdAndMenuItemId(Long cartId, Long menuItemId);
	
	int deleteByCartIdAndMenuItemId(Long cartId, Long menuItemId);
}
