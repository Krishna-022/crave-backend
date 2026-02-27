package com.crave.crave_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crave.crave_backend.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
